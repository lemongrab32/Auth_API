package com.github.lemongrab32.registrationtest.service;

import com.github.lemongrab32.registrationtest.repository.RoleRepository;
import com.github.lemongrab32.registrationtest.repository.entities.Role;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").isPresent() ? roleRepository.findByName("ROLE_USER").get() : null;
    }

    public void addRole(String name, User user) {
        Role role = roleRepository.findByName(name).get();
        role.addUser(user);
        roleRepository.save(role);
    }

}
