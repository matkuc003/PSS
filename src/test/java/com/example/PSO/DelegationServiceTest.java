package com.example.PSO;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.repositories.DelegationRepo;
import com.example.PSO.repositories.RoleRepo;
import com.example.PSO.repositories.UserRepo;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ComponentScan("com.example.PSO.service")
public class DelegationServiceTest {
    @Autowired
    private DelegationService delegationService;

    @Autowired
    private DelegationRepo delegationRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    int i=0;
    private List<Delegation> delegations = new ArrayList<>();
    @Before
    public void setUp() {
        Role r1 = new Role("ROLE_USER");
        Role r2 = new Role("USER");

        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        User u1 = new User("UTP", "Bydgoszcz", "111222333", "Jan", "Kowalski", "jankow@wp.pl", "111222");
        User u2 = new User("UKW", "Bydgoszcz", "555555555", "Andrzej", "Kowalski", "andkow@wp.pl", "222333");
        User u3 = new User("UMK", "Torun", "666677771", "Anna", "Nowak", "nowanna@wp.pl", "334455");
        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
        u3 = userRepo.save(u3);
        if(i==0) {
            Delegation d1 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(5), userRepo.getOne(1l));
            Delegation d2 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(2).plusDays(6), userRepo.getOne(2l));
            Delegation d3 = new Delegation(LocalDate.now(), LocalDate.now().plusDays(3).plusDays(7), userRepo.getOne(2l));
            d1 = delegationRepo.save(d1);
            d2 = delegationRepo.save(d2);
            d3 = delegationRepo.save(d3);
            delegations.addAll(Arrays.asList(d1, d2, d3));
            i++;
        }

        u1.addRole(r1);
        u1.addRole(r2);
        u2.addRole(r2);
        u3.addRole(r1);
    }
    @Test
    public void getAllDelegationTest() {
        List<Delegation> found = delegationService.getAllDelegation();
        Assertions.assertEquals(delegations, found);
    }

    @Test
    public void addDelegationTest() {
        Delegation given = new Delegation(LocalDate.now(),LocalDate.now().plusDays(5),userRepo.getOne(1l));
        ResponseEntity<Delegation> response = delegationService.addDelegation(1,given);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            given = response.getBody();
        }

        Optional<Delegation> found = delegationRepo.findById(given.getId());
        Assertions.assertEquals(given, found.get());
    }

    @Test
    public void getAlDelegationsOrderByDateStartDescTest() {
        List<Delegation> allDelegations = new ArrayList<>(Arrays.asList(delegations.get(0), delegations.get(1), delegations.get(2)));
        List<Delegation> found = delegationService.getAllDelegationsOrderByDateStartDesc();
        Assertions.assertEquals(allDelegations, found);
    }
    @Test
    public void getAllDelByUserOrderByDateStartDescTest() {
        List<Delegation> allDelegations = new ArrayList<>(Arrays.asList(delegations.get(1), delegations.get(2)));
        List<Delegation> found = delegationService.getAllDelByUserOrderByDateStartDesc(2);
        Assertions.assertEquals(allDelegations, found);
    }
    @Test
    public void changeDelegationTest() {
        Delegation given = delegations.get(0);
        Delegation changed = given;
        changed.setDinnerNumber(5);
        changed.setDescription("test");
        changed.setTicketPrice(5.0);
        changed.setKm(50);
        changed.setAccommodationPrice(50.0);
        changed.setOtherTicketsPrice(20.0);
        changed.setOtherOutlayDesc("test");
        changed.setOtherOutlayPrice(20.0);
        ResponseEntity<Delegation> response = delegationService.changeDelegation(given.getId(),changed);
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            changed = response.getBody();
        }

        Optional<Delegation> found = delegationRepo.findById(given.getId());
        Assertions.assertEquals(changed, found.get());
    }
    @Test
    public void deleteDelegationTest() {
        Delegation given = new Delegation(LocalDate.now(),LocalDate.now().plusDays(5),userRepo.getOne(1l));
        given = delegationRepo.save(given);
        ResponseEntity<Boolean> response = delegationService.removeDelegation(1l,given.getId());
        Assertions.assertTrue(response.getBody());
    }
}
