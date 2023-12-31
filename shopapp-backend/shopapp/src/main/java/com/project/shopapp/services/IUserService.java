package com.project.shopapp.services;

import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber , String password , Long roleID) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;
    User updateUser (Long userId , UpdateUserDTO updateUserDTO) throws Exception;
}
