package com.github.lemongrab32.registrationtest.dtos;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}
