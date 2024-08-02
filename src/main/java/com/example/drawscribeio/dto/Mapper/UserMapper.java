package com.example.drawscribeio.dto.Mapper;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;

public class UserMapper {
    public static UserDto toUserDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setScore(user.getScore());
        // Add other fields as necessary
        return userDTO;
    }
}
