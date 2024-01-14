package com.github.lemongrab32.registrationtest.repository;

import com.github.lemongrab32.registrationtest.repository.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    void deleteUserByLogin(String username);

}
