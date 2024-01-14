package com.github.lemongrab32.registrationtest.repository.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name= "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "roles_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;

}
