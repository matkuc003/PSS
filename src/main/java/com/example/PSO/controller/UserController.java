package com.example.PSO.controller;

import com.example.PSO.models.User;
import com.example.PSO.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<User> changePassword(@PathVariable long userId, @RequestBody String newPassword) {
        return userService.changePassword(userId, newPassword);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable long userId) {
        return userService.deleteUserById(userId);
    }
}
