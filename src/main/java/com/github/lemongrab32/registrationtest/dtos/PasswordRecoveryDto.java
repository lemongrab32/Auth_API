package com.github.lemongrab32.registrationtest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordRecoveryDto {

    String password;
    String confirmPassword;

}
