package com.github.lemongrab32.registrationtest.service;
import com.github.lemongrab32.registrationtest.dtos.JwtRequest;
import com.github.lemongrab32.registrationtest.dtos.JwtResponse;
import com.github.lemongrab32.registrationtest.dtos.RegistrationUserDto;
import com.github.lemongrab32.registrationtest.dtos.UserDto;
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


import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {


    Logger logger = LoggerFactory.getLogger("UserAuthenticationLog");
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Wrong login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        logger.info("User '{}' created a join token {}", authRequest.getUsername(), token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Password mismatch"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByLogin(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "A user with the same username is already exists"), HttpStatus.BAD_REQUEST);
        }
        registrationUserDto.setPassword(new BCryptPasswordEncoder()
                .encode(registrationUserDto.getPassword()));
        User user = userService.save(registrationUserDto);
        logger.info("User {} signed up on [{}/{}] [{}:{}:{}].", user.getLogin(), currentDateTime.getDayOfMonth(),
                currentDateTime.getMonth(), currentDateTime.getYear(), currentDateTime.getHour(), currentDateTime.getMinute(),
                currentDateTime.getSecond());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getMail()));
    }


}
