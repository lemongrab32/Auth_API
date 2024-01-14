package com.github.lemongrab32.registrationtest.controllers;

import com.github.lemongrab32.registrationtest.dtos.JwtRequest;
import com.github.lemongrab32.registrationtest.dtos.PasswordRecoveryDto;
import com.github.lemongrab32.registrationtest.dtos.RegistrationUserDto;
import com.github.lemongrab32.registrationtest.service.AuthService;
import com.github.lemongrab32.registrationtest.service.RoleService;
import com.github.lemongrab32.registrationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/password_recovery/{username}")
    public ResponseEntity<?> recoveryPassword(@RequestBody PasswordRecoveryDto pass, @PathVariable String username) {
        return authService.recoveryPassword(pass, username);
    }

    @GetMapping("/confirmed/{username}")
    public String emailConfirmed(@PathVariable String username) {
        userService.addRole("ROLE_CONFIRMED", username);
        userService.deleteRole("ROLE_UNCONFIRMED", username);
        return "Your email has been confirmed successfully";
    }

}
