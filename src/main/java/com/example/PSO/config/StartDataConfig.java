package com.example.PSO.config;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.repositories.DelegationRepo;
import com.example.PSO.repositories.RoleRepo;
import com.example.PSO.repositories.UserRepo;
import org.apache.tomcat.jni.Local;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class StartDataConfig {
    public StartDataConfig(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, DelegationRepo delegationRepo) {
        Role r1 = new Role("ROLE_ADMIN");
        r1 = roleRepo.save(r1);
        Role r2 = new Role("ROLE_USER");
        r2 = roleRepo.save(r2);

        User u1 = new User("UTP", "Bydgoszcz", "111222333", "Jan", "Kowalski",
                "jankow@wp.pl", passwordEncoder.encode("111222"));
        User u2 = new User("UKW", "Bydgoszcz", "555555555", "Andrzej", "Kowalski",
                "andkow@wp.pl", passwordEncoder.encode("222111"));
        Delegation d1 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(5),u1);
        Delegation d2 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(7),u1);
        Delegation d3 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(8),u2);
        Delegation d4 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(9),u2);
        u1.addRole(r1);
        u2.addRole(r2);
        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
        d1 = delegationRepo.save(d1);
        d2 = delegationRepo.save(d2);
        d3 = delegationRepo.save(d3);
        d4 = delegationRepo.save(d4);
    }
}
