package com.github.lemongrab32.registrationtest.dtos;

import lombok.Data;

@Data
public class RegistrationUserDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String ip;
}
