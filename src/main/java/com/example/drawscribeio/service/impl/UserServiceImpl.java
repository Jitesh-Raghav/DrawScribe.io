package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.exception.UserException;
import com.example.drawscribeio.repository.UserRepository;
import com.example.drawscribeio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserDto convertToDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDTO = new UserDto();

        if (user.getUserId() != null) {
            userDTO.setUserId(user.getUserId());
        }

        if (user.getUsername() != null) {
            userDTO.setUsername(user.getUsername());
        }

        if (user.getEmail() != null) {
            userDTO.setEmail(user.getEmail());
        }

        if (user.getAvatarUrl() != null) {
            userDTO.setAvatarUrl(user.getAvatarUrl());
        }

        return userDTO;
    }

    private User convertToEntity(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        if (userDTO.getUserId() != null) {
            user.setUserId(userDTO.getUserId());
        }

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getAvatarUrl() != null) {
            user.setAvatarUrl(userDTO.getAvatarUrl());
        }

        return user;
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users=userRepository.findAll();
        return new ArrayList<>(users);
    }

    @Override
    public User getUserById(Long userId) throws UserException {
        User user= userRepository.findById(userId)
                .orElseThrow(()->new UserException("user not found with id " + userId));
        return user;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (userDetails.getUsername() != null) {
                user.setUsername(userDetails.getUsername());
            }

            if (userDetails.getEmail() != null) {
                user.setEmail(userDetails.getEmail());
            }

            if (userDetails.getAvatarUrl() != null) {
                user.setAvatarUrl(userDetails.getAvatarUrl());
            }

            // Update other fields as necessary
            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        }
        return null;
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
