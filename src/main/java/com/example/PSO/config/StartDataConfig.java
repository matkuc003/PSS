package com.example.PSO.config;

import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.repositories.RoleRepo;
import com.example.PSO.repositories.UserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartDataConfig {
    public StartDataConfig(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        Role r1 = new Role("ROLE_ADMIN");
        r1 = roleRepo.save(r1);
        Role r2 = new Role("ROLE_USER");
        r2 = roleRepo.save(r2);

        User u1 = new User("UTP", "Bydgoszcz", "111222333", "Jan", "Kowalski",
                "jankow@wp.pl", passwordEncoder.encode("111222"));
        User u2 = new User("UKW", "Bydgoszcz", "555555555", "Andrzej", "Kowalski",
                "andkow@wp.pl", passwordEncoder.encode("222111"));

        u1.addRole(r1);
        u2.addRole(r2);
        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
    }
}
