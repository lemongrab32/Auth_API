package com.github.lemongrab32.registrationtest.repository;

import com.github.lemongrab32.registrationtest.repository.entities.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from roles_x_users where user_id = :user_id",
            nativeQuery = true)
    void deleteAllUserRoles(@Param("user_id") Long u_id);

}
