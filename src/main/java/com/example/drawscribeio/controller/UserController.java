package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.UserDto;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.exception.UserException;
import com.example.drawscribeio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService= userService;
    }

    @GetMapping
    public List<User> GetAllUsers(){
       return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
   public ResponseEntity<User> getUserById(@PathVariable Long userId) throws UserException {
        User user= userService.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        UserDto updatedUser= userService.updateUser(userId, userDto);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        String message= "User Deleted successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
