package com.example.PSO;

import com.example.PSO.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PSO.repositories.RoleRepo;

import javax.annotation.PostConstruct;

@Service
public class testDAO {
    @Autowired
    RoleRepo roleRepo;
    @PostConstruct
    public void init() {
        save(new Role(0l,"ROLE_USER",null));
    }
public boolean save (Role role){
    roleRepo.save(role);
    return false;
}
}
