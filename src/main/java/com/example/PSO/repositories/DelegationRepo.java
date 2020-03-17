package com.example.PSO.repositories;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DelegationRepo extends JpaRepository<Delegation,Long> {
    List<Delegation> getAllByUser(User user);
}
