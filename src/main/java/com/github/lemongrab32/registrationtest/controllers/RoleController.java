package com.github.lemongrab32.registrationtest.controllers;
import com.github.lemongrab32.registrationtest.service.RoleService;
import com.github.lemongrab32.registrationtest.dtos.RoleData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.lemongrab32.registrationtest.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;
    private final RoleService roleService;
    @PostMapping("/admin/role/add") // Role add, endpoint - "/role"
    public ResponseEntity<?> addRole(@RequestBody RoleData roleData) {
        return userService.addRole(roleData);
    }
    @PostMapping("/admin/role/delete")
    public ResponseEntity<?> deleteRole(@RequestBody RoleData roleData){
        return userService.deleteRole(roleData);
    }
}
