package com.example.PSO.service;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.repositories.DelegationRepo;
import com.example.PSO.repositories.RoleRepo;
import com.example.PSO.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class InitService {
    private UserRepo userRepo;
    private DelegationRepo delegationRepo;
    private RoleRepo roleRepo;

    @Autowired
    public InitService(UserRepo userRepo, DelegationRepo delegationRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.delegationRepo = delegationRepo;
        this.roleRepo = roleRepo;
    }

    @PostConstruct
    public void init() {
        Role r1 = new Role("ROLE_USER");
        Role r2 = new Role("USER");

        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);

        User u1 = new User("UTP", "Bydgoszcz", "111222333", "Jan", "Kowalski", "jankow@wp.pl", "111222");
        User u2 = new User("UKW", "Bydgoszcz", "555555555", "Andrzej", "Kowalski", "andkow@wp.pl", "222333");
        User u3 = new User("UMK", "Torun", "666677771", "Anna", "Nowak", "nowanna@wp.pl", "334455");

        u1.addRole(r1);
        u1.addRole(r2);
        u2.addRole(r1);
        u3.addRole(r1);

        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
        u3 = userRepo.save(u3);

        Delegation d1 = new Delegation(LocalDate.now().minusDays(3), LocalDate.now().plusDays(2), u1);
        Delegation d2 = new Delegation(LocalDate.now().minusDays(1), LocalDate.now(), u2);
        Delegation d3 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(2), u2);
        Delegation d4 = new Delegation(LocalDate.now().minusDays(5), LocalDate.now().plusDays(3), u3);

        delegationRepo.saveAll(Arrays.asList(d1, d2, d3, d4));
    }
}
