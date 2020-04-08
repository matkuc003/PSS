package com.example.PSO.service;

import com.example.PSO.models.Role;
import com.example.PSO.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role getRoleByName(String name) {
        return roleRepo.findByRoleName(name).orElseThrow();
    }
}
