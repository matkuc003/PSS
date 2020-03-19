package com.example.PSO.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
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
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName ="rid"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Delegation> delegations;

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public User(String companyName, String companyAddress, String companyNip, String name, String lastName, String email, String password) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyNip = companyNip;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
