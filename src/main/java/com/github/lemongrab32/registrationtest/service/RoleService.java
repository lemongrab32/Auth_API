package com.github.lemongrab32.registrationtest.service;


import com.github.lemongrab32.registrationtest.repository.RoleRepository;
import com.github.lemongrab32.registrationtest.repository.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public Optional<Role> findByName(String roleName){
        return roleRepository.findByName(roleName);
    }

}