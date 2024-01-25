package com.github.lemongrab32.registrationtest.service;

import com.github.lemongrab32.registrationtest.dtos.*;
import com.github.lemongrab32.registrationtest.exceptions.AppError;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import com.github.lemongrab32.registrationtest.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger("UserAuthenticationLog");
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.error("User {} with current data doesn't exists.", authRequest.getUsername());
            AppError unauthorized = new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Wrong login or password");
            return new ResponseEntity<>(unauthorized, HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        logger.info("User {} signed in.", userDetails.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), userDetails);
        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUsername(
                refreshTokenRequest.getToken()
        ));

        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), userDetails)) {
            var jwt = jwtUtils.generateToken(userService.loadUserByUsername(userDetails.getUsername()));

            return ResponseEntity.ok(new JwtResponse(jwt, refreshTokenRequest.getToken()));
        }
        return null;
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Password mismatch"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByLogin(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "A user with the same username is already exists"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "An account with given email is already exists"), HttpStatus.BAD_REQUEST);
        }
        registrationUserDto.setPassword(new BCryptPasswordEncoder()
                .encode(registrationUserDto.getPassword()));
        User user = userService.save(registrationUserDto);
        logger.info("User {} signed up.", user.getLogin());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getMail()));
    }

    public ResponseEntity<?> recoveryPassword(@RequestBody PasswordRecoveryDto pass, String username) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!pass.getPassword().equals(pass.getConfirmPassword())) {
            logger.error("User {} tried to recover the password", username);
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Password mismatch"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByLogin(username).get();
        if (encoder.matches(pass.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "New password must be different from the old one"), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(pass.getPassword()));
        userService.save(user);
        logger.info("User {} recovered his password.", user.getLogin());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getMail()));
    }
}
