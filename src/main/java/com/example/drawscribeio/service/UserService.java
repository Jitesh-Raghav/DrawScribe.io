package com.example.drawscribeio.service;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDetails);
    void deleteUser(Long userId);
}
