package com.github.lemongrab32.registrationtest.controllers;
import com.github.lemongrab32.registrationtest.dtos.RoleSettingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.lemongrab32.registrationtest.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/add_role")
    public ResponseEntity<?> addRole(@RequestBody RoleSettingDto roleSettingDto) {
        return userService.addRole(roleSettingDto);
    }

    @PostMapping("/delete_role")
    public ResponseEntity<?> deleteRole(@RequestBody RoleSettingDto roleSettingDto){
        return userService.deleteRole(roleSettingDto);
    }

}
