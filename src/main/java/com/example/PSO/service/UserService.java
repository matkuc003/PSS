package com.example.PSO.service;

import com.example.PSO.models.User;
import com.example.PSO.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<User> registerUser(User user) {
        if (userRepo.findUserByEmail(user.getEmail()).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User saveUser = userRepo.save(user);
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public ResponseEntity<User> changePassword(long userId, String newPassword) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        user.get().setPassword(newPassword);
        User saveUser = userRepo.save(user.get());
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> deleteUserById(long userId) {
        if (userRepo.findById(userId).isEmpty())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

        userRepo.deleteById(userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
