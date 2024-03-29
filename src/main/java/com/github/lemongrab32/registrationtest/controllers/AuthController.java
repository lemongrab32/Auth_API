package com.github.lemongrab32.registrationtest.controllers;

import com.github.lemongrab32.registrationtest.dtos.JwtRequest;
import com.github.lemongrab32.registrationtest.dtos.PasswordRecoveryDto;
import com.github.lemongrab32.registrationtest.dtos.RefreshTokenRequest;
import com.github.lemongrab32.registrationtest.dtos.RegistrationUserDto;
import com.github.lemongrab32.registrationtest.service.AuthService;
import com.github.lemongrab32.registrationtest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest, HttpServletRequest request) {
        return authService.createAuthToken(authRequest, request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshRequest) {
        return authService.refreshToken(refreshRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto, HttpServletRequest request) {
        return authService.createNewUser(registrationUserDto, request);
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
