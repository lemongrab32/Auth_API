package com.github.lemongrab32.registrationtest.repository.entities;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Table(name= "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    public Long getChatId() {
        return id;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles;


}


