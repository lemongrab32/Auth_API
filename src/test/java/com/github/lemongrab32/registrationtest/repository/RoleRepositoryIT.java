package com.github.lemongrab32.registrationtest.repository;


import com.github.lemongrab32.registrationtest.repository.entities.Role;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryIT {
    @Autowired
    private RoleRepository roleRepository;

    @Sql(scripts = {"/sql/dropDataBase.sql", "/sql/roleTest.sql"})
    @Test
    public void ifRoleExists() {
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_ADMIN");
        Assertions.assertTrue(roleOptional.isPresent()); //If role exists
        List<User> users = roleOptional.get().getUsers();
        Assertions.assertEquals(2, users.size()); // If number of users equals
    }

    @Sql(scripts = {"/sql/dropDataBase.sql", "/sql/roleTest.sql"})
    @Test
    public void makeAnotherUser(){
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_ADMIN");
        Assertions.assertTrue(roleOptional.isPresent());
        List<User> users = roleOptional.get().getUsers();

        User user = new User();
        user.setLogin("Gavrik");
        user.setId(user.getId()+1);

        users.add(user);
        Assertions.assertEquals(3, users.size());
    }


}

