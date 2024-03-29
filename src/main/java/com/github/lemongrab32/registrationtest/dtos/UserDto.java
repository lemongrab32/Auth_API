package com.github.lemongrab32.registrationtest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String ip;

}
