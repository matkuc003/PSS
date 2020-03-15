package com.example.PSO.service;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.User;
import com.example.PSO.repositories.DelegationRepo;
import com.example.PSO.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelegationService {
    private DelegationRepo delegationRepo;
    private UserRepo userRepo;

    @Autowired
    public DelegationService(DelegationRepo delegationRepo, UserRepo userRepo) {
        this.delegationRepo = delegationRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<Delegation> addDelegation(long userId, Delegation delegation) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        delegation.setUser(user.get());
        delegation = delegationRepo.save(delegation);
        return new ResponseEntity<>(delegation, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> removeDelegation(long delegationId) {
        if (delegationRepo.findById(delegationId).isEmpty())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

        delegationRepo.deleteById(delegationId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public ResponseEntity<Delegation> changeDelegation(long delegationId, Delegation delegation) {
        Optional<Delegation> getDelegation = delegationRepo.findById(delegationId);
        if (getDelegation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        delegation.setId(delegationId);
        delegation.setUser(getDelegation.get().getUser());
        delegation = delegationRepo.save(delegation);
        return new ResponseEntity<>(delegation, HttpStatus.OK);
    }

    public List<Delegation> getAllDelegation() {
        return delegationRepo.findAll();
    }
}
