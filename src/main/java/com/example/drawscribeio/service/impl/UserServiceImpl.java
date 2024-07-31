package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.UserRepository;
import com.example.drawscribeio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserDto convertToDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        return userDTO;
    }

    private User convertToEntity(UserDto userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::convertToDTO).orElse(null);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setAvatarUrl(userDetails.getAvatarUrl());
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
