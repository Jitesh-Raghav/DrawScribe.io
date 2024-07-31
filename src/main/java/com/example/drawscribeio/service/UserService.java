package com.example.drawscribeio.service;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.exception.UserException;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long userId) throws UserException;
    User createUser(User user);
    UserDto updateUser(Long id, UserDto userDetails);
    void deleteUser(Long userId);
}
