package com.github.lemongrab32.registrationtest.controllers;

import com.github.lemongrab32.registrationtest.repository.entities.User;
import com.github.lemongrab32.registrationtest.service.RoleService;
import com.github.lemongrab32.registrationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        User user = userService.findByLogin(principal.getName()).orElseThrow();
        return user.getLogin() + " - " + user.getRoles().stream()
                .map(it -> it.getName() + " ")
                .collect(Collectors.joining());
    }

}
