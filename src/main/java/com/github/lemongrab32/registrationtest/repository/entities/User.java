package com.github.lemongrab32.registrationtest.repository.entities;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(name= "users")
@Data
@SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @Column(name = "ip")
    private String ip;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_x_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public void addRole(Role role) {
        if (isNull(roles))
            roles = new ArrayList<>();
        roles.add(role);
    }

    public void deleteRole(Role role) {
        roles.remove(role);
    }

    public void deleteAllRoles() {
        roles.clear();
    }

}


