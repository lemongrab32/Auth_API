package com.github.lemongrab32.registrationtest.service;
import com.github.lemongrab32.registrationtest.dtos.RoleData;
import com.github.lemongrab32.registrationtest.dtos.RegistrationUserDto;
import com.github.lemongrab32.registrationtest.exceptions.AppError;
import com.github.lemongrab32.registrationtest.repository.UserRepository;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Searching user data
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format("User '%s' not found", username)
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(role ->
                                new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    // Registration data saving
    public User save(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setLogin(registrationUserDto.getUsername());
        user.setMail(registrationUserDto.getEmail());
        user.setPassword(registrationUserDto.getPassword());
        roleService.addRole("ROLE_USER", user);
        return userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<?> addRole(@RequestBody RoleData roleData){
        try {
            User user = findByLogin(roleData.getLogin()).get();
            if (!user.getRoles().contains(roleService.findByName(roleData.getRoleName()).get())){
                roleService.addRole(roleData.getRoleName(), user);
                userRepository.save(user);
            }
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Wrong data"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<?> deleteRole(@RequestBody RoleData roleData){
        try{
            User user = findByLogin(roleData.getLogin()).get();
            if (!user.getRoles().contains(roleService.findByName(roleData.getRoleName()).get())){
                roleService.deleteRole(roleData.getRoleName(), user);
                userRepository.save(user);
            }
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "No such user!"), HttpStatus.BAD_REQUEST);
        }
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
