package com.arcaneemporium.backend.utils;

import com.arcaneemporium.backend.user.RegisterUserDTO;
import com.arcaneemporium.backend.user.Role;
import com.arcaneemporium.backend.user.User;
import com.arcaneemporium.backend.user.UserDTO;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user){
        return new UserDTO(user.getUsername(), user.getEmail());
    }

    public static User mapToUser(UserDTO userDTO, String encodedPassword, Role role){
        return new User(userDTO.username(), encodedPassword, userDTO.email(), role);
    }

    public static User mapToUser(RegisterUserDTO registerUserDTO, String encodedPassword, Role role){
        return new User(registerUserDTO.username(), encodedPassword, registerUserDTO.email(), role);
    }
}
