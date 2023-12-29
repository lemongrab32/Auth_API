package com.github.lemongrab32.registrationtest.repository.entities;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Table(name= "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "roles_x_users",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
