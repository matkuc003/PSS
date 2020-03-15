package com.example.PSO.controller;

import com.example.PSO.models.Delegation;
import com.example.PSO.service.DelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delegation")
public class DelegationController {
    private DelegationService delegationService;

    @Autowired
    public DelegationController(DelegationService delegationService) {
        this.delegationService = delegationService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Delegation> addDelegation(@PathVariable long userId, @RequestBody Delegation delegation) {
        return delegationService.addDelegation(userId, delegation);
    }

    @DeleteMapping("/{delegationId}")
    public ResponseEntity<Boolean> removeDelegation(@PathVariable long delegationId) {
        return delegationService.removeDelegation(delegationId);
    }

    @PutMapping("/{delegationId}")
    public ResponseEntity<Delegation> changeDelegation(@PathVariable long delegationId, @RequestBody Delegation delegation) {
        return delegationService.changeDelegation(delegationId, delegation);
    }

    @GetMapping("/all")
    public List<Delegation> getAllDelegation() {
        return delegationService.getAllDelegation();
    }
}
