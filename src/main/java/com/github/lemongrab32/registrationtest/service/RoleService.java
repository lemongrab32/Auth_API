package com.github.lemongrab32.registrationtest.service;

import com.github.lemongrab32.registrationtest.repository.RoleRepository;
import com.github.lemongrab32.registrationtest.repository.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

}
