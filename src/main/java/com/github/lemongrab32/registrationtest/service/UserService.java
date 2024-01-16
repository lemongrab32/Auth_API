package com.github.lemongrab32.registrationtest.service;
import com.github.lemongrab32.registrationtest.dtos.DeletionUserDto;
import com.github.lemongrab32.registrationtest.dtos.RoleSettingDto;
import com.github.lemongrab32.registrationtest.dtos.RegistrationUserDto;
import com.github.lemongrab32.registrationtest.exceptions.AppError;
import com.github.lemongrab32.registrationtest.repository.UserRepository;
import com.github.lemongrab32.registrationtest.repository.entities.Role;
import com.github.lemongrab32.registrationtest.repository.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MailService mailService;

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

    public User save(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setLogin(registrationUserDto.getUsername());
        user.setMail(registrationUserDto.getEmail());
        user.setPassword(registrationUserDto.getPassword());
        user.addRole(roleService.findByName("ROLE_UNCONFIRMED").get());
        mailService.sendMail(user.getMail(),
                "Confirmation of given email address",
                "Follow this link to confirm your email address:\n" +
                        "\thttp://localhost:8080/api/confirmed/" + user.getLogin());
        logger.info("Confirmation of given email address sent to {}", user.getMail());
        return userRepository.save(user);
    }
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<?> deleteUser(@RequestBody DeletionUserDto deletionUserDto){
        try {
            User user = findByLogin(deletionUserDto.getUsername()).get();
            user.deleteAllRoles();
            userRepository.deleteUserByLogin(deletionUserDto.getUsername());

        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "User " + deletionUserDto.getUsername() + " doesn't exists"), HttpStatus.NOT_FOUND);
        }
        logger.info("User {} deleted.", deletionUserDto.getUsername());
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    public void addRole(String roleName, String username) {
        Role role = roleService.findByName(roleName).get();
        User user = findByLogin(username).get();
        user.addRole(role);
        userRepository.save(user);
        logger.info("User '{}' now has role '{}'", user.getLogin(), roleName);
    }
    public void deleteRole(String roleName, String username){
        Role role = roleService.findByName(roleName).get();
        User user = findByLogin(username).get();
        user.deleteRole(role);
        userRepository.save(user);
        logger.info("User {} no longer has role '{}'.", user.getLogin(), roleName);
    }

    @Transactional
    public ResponseEntity<?> addRole(@RequestBody RoleSettingDto roleSettingDto){
        try {
            User user = findByLogin(roleSettingDto.getLogin()).get();
            if (!user.getRoles().contains(roleService.findByName(roleSettingDto.getRoleName()).get())){
                addRole(roleSettingDto.getRoleName(), user.getLogin());
            }
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Wrong data"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<?> deleteRole(@RequestBody RoleSettingDto roleSettingDto){
        try{
            User user = findByLogin(roleSettingDto.getLogin()).get();
            if (user.getRoles().contains(roleService.findByName(roleSettingDto.getRoleName()).get())){
                deleteRole(roleSettingDto.getRoleName(), user.getLogin());
            }
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "No such user!"), HttpStatus.BAD_REQUEST);
        }
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
