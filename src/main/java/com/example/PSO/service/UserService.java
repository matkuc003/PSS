package com.example.PSO.service;

import com.example.PSO.models.User;
import com.example.PSO.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepo userRepo;
    private DelegationService delegationService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, DelegationService delegationService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.delegationService = delegationService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<User> registerUser(User user) {
        if (userRepo.findUserByEmail(user.getEmail()).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }
    public Optional<User> getUserById(Long id) {
        System.out.println("maselko");return userRepo.findById(id);
    }
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public ResponseEntity<User> changePassword(long userId, String newPassword) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        user.get().setPassword(passwordEncoder.encode(newPassword));
        User saveUser = userRepo.save(user.get());
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> deleteUserById(long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

        if (user.get().getDelegations() != null)
            user.get().getDelegations().stream().forEach(d -> delegationService.removeDelegation(userId, d.getId()));
        if (user.get().getRoles() != null)
            user.get().getRoles().stream().forEach(r -> user.get().removeRole(r));
        userRepo.deleteById(userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public List<User> getAllUsersByRoleName(String roleName) {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleName)))
                .collect(Collectors.toList());
    }
}
