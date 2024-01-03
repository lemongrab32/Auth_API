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
    
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles;


}


