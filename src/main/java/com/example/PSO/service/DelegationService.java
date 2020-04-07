package com.example.PSO.service;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.User;
import com.example.PSO.repositories.DelegationRepo;
import com.example.PSO.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<Boolean> removeDelegation(long userId, long delegationId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        Optional<Delegation> delegation = delegationRepo.findById(delegationId);
        if (delegation.isEmpty())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        if (!delegation.get().getUser().getEmail().equals(user.get().getEmail()))
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);

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

    public List<Delegation> getAllDelegationByUser(long userId) {
        return delegationRepo.getAllByUser(userRepo.findById(userId).get());
    }

    public List<Delegation> getAllDelegationsOrderByDateStartDesc() {
        return this.getAllDelegation()
                .stream()
                .sorted(Comparator.comparing(Delegation::getDateTimeStart, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<Delegation> getAllDelByUserOrderByDateStartDesc(long userId) {
        return this.getAllDelegationByUser(userId)
                .stream()
                .sorted(Comparator.comparing(Delegation::getDateTimeStart, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
