package com.example.PSO;

import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.repositories.RoleRepo;
import com.example.PSO.repositories.UserRepo;
import com.example.PSO.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ComponentScan("com.example.PSO.service")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    private List<User> users = new ArrayList<>();

    @Before
    public void setUp() {
        Role r1 = new Role("ROLE_USER");
        Role r2 = new Role("USER");

        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        User u1 = new User("UTP", "Bydgoszcz", "111222333", "Jan", "Kowalski", "jankow@wp.pl", "111222");
        User u2 = new User("UKW", "Bydgoszcz", "555555555", "Andrzej", "Kowalski", "andkow@wp.pl", "222333");
        User u3 = new User("UMK", "Torun", "666677771", "Anna", "Nowak", "nowanna@wp.pl", "334455");
        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
        u3 = userRepo.save(u3);

        u1.addRole(r1);
        u1.addRole(r2);
        u2.addRole(r2);
        u3.addRole(r1);
        users.addAll(Arrays.asList(u1, u2, u3));
    }

    @Test
    public void getAllUserTest() {
        List<User> found = userService.getAllUser();
        Assertions.assertEquals(users, found);
    }

    @Test
    public void registerUserTest() {
        User given = new User("UTP", "Kaliskiego", "111222", "Jan", "Kowalski", "jk@wp.pl", "111222");
        ResponseEntity<User> response = userService.registerUser(given);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            given = response.getBody();
        }

        Optional<User> found = userRepo.findUserByEmail(given.getEmail());
        Assertions.assertEquals(given, found.get());
    }

    @Test
    public void changePasswordTest() {
        User given = users.get(0);
        ResponseEntity<User> response = userService.changePassword(given.getUid(), "n3wPassw0rd321");
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            given = response.getBody();
        }

        Optional<User> found = userRepo.findById(given.getUid());
        Assertions.assertEquals(given.getPassword(), found.get().getPassword());
    }

    @Test
    public void deleteUserTest() {
        User given = new User("UTP", "Kaliskiego", "111222", "Jan", "Kowalski", "jk@wp.pl", "111222");
        given = userRepo.save(given);
        ResponseEntity<Boolean> response = userService.deleteUserById(given.getUid());
        Assertions.assertTrue(response.getBody());
    }

    @Test
    public void getAllUsersByRoleTest() {
        List<User> usersWithRole = new ArrayList<>(Arrays.asList(users.get(0), users.get(1)));
        List<User> found = userService.getAllUsersByRoleName("USER");
        Assertions.assertEquals(usersWithRole, found);
    }
}
