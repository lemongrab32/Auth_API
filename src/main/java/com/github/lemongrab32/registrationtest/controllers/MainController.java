package com.github.lemongrab32.registrationtest.controllers;


import com.github.lemongrab32.registrationtest.repository.entities.User;
import com.github.lemongrab32.registrationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final UserService userService;

    @GetMapping("/user_info")
    public String userData(Principal principal) {
        User user = userService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Such user doesn't exist"));
        return user.getLogin() + " - " + user.getRoles().stream()
                .map(it -> it.getName() + " ")
                .collect(Collectors.joining());
    }


}
