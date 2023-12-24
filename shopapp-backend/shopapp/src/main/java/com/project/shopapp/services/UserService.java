package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.PermissionDenyException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phong number already exists");
        }

        Role role = roleRepository.findById(userDTO.getRoleID())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        if (role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("You cannot register a admin account");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountID())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        newUser.setRole(role);
        if (userDTO.getFacebookAccountID() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser) ;
    }

    @Override
    public String login(String phoneNumber, String password , Long roleId) throws Exception{

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phonenumber / password");
        }
        //return optionalUser.get();
        User existingUser = optionalUser.get();
        //Check password
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0){
            if (!passwordEncoder.matches(password,existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number / password");
            }
        }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                    existingUser.getAuthorities()
        );
        //Authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtil.extractPhongNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return  user.get();
        }else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the phone number is being changed and if it already exists for another user
        String newPhoneNumber = updateUserDTO.getPhoneNumber();
        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
                userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Update user information based on the DTO
        if (updateUserDTO.getFullName() != null) {
            existingUser.setFullName(updateUserDTO.getFullName());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (updateUserDTO.getAddress() != null) {
            existingUser.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updateUserDTO.getDateOfBirth());
        }
        if (updateUserDTO.getFacebookAccountId() > 0) {
            existingUser.setFacebookAccountId(updateUserDTO.getFacebookAccountId());
        }
        if (updateUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountId(updateUserDTO.getGoogleAccountId());
        }

        // Update the password if it is provided in the DTO
        if (updateUserDTO.getPassword() != null
                && !updateUserDTO.getPassword().isEmpty()) {
            if(!updateUserDTO.getPassword().equals(updateUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updateUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        //existingUser.setRole(updatedRole);
        // Save the updated user
        return userRepository.save(existingUser);
    }
}
