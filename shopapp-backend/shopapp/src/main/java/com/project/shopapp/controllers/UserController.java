package com.project.shopapp.controllers;

import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.reponses.LoginResponse;
import com.project.shopapp.reponses.RegisterResponse;
import com.project.shopapp.reponses.UserResponse;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.utils.LocalizationUtils;
import com.project.shopapp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final LocalizationUtils localizationUtils;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
            ){
        RegisterResponse registerResponse = new RegisterResponse();

            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                registerResponse.setMessage(errorMessages.toString());
                return ResponseEntity.badRequest().body(registerResponse);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
                return ResponseEntity.badRequest().body(registerResponse);
            }
        try {
            User user = userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        }catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO){
        //Kiểm tra thông tin đăng nhập và sinh token

        try {
            String token = userService.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId())
            ;

            return ResponseEntity.ok(LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAIL,e.getMessage()))
                    .build());
        }

    }

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(@RequestHeader("Authorization") String token ) {
        try {
            String extractedToken = token.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<UserResponse> updateUserDetails(
            @PathVariable Long userId,
            @RequestBody UpdateUserDTO updatedUserDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            if (user.getId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User updatedUser = userService.updateUser(userId, updatedUserDTO);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
