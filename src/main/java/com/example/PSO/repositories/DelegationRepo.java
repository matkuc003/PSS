package com.example.PSO.repositories;

import com.example.PSO.models.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DelegationRepo extends JpaRepository<Delegation,Long> {

}
