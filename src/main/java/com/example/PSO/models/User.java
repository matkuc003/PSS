package com.example.PSO.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String companyName;
    @NotNull
    private String companyAddress;
    @NotNull
    private String companyNip;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Column
    private boolean status = true;
    @Column
    private LocalDate registrationDate = LocalDate.now();
    @ManyToMany
    private List<Role> role;
    @OneToMany
    private List<Delegation> delegations;

}
