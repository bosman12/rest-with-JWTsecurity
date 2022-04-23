package com.example.rest_example.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
