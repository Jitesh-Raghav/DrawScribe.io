package com.example.drawscribeio.service;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.exception.UserException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    User getUserById(Long userId) throws UserException;
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDetails);
    void deleteUser(Long userId);
}
