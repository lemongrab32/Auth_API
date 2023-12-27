package com.github.lemongrab32.registrationtest.repository;


import com.github.lemongrab32.registrationtest.repository.entities.Role;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
public class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    @Sql(scripts = {"/sql/dropDataBase.sql", "/sql/userTest.sql"})
    @Test
    public void ifUserExists(){
        Optional<User> optionalUser = userRepository.findByLogin("Pablo");
        Assertions.assertTrue(optionalUser.isPresent()); // Checking if user exists
        List<Role> roles = optionalUser.get().getRoles();
        Assertions.assertEquals(3, roles.size());
    }

    @Sql(scripts = {"/sql/dropDataBase.sql", "/sql/userTest.sql"})
    @Test
    public void makeAnotherRole(){
        Optional<User> optionalUser = userRepository.findByLogin("Pablo");
        Assertions.assertTrue(optionalUser.isPresent());
        List<Role> roles = optionalUser.get().getRoles();

        Role role = new Role();
        role.setName("ROLE_SUPERUSER");
        role.setId(optionalUser.get().getId()+1);
        roles.add(role);

        Assertions.assertEquals(4, roles.size()); // Checking if we got another role

    }

}
