package com.example.MedicCenter.infrastructure.adapters.in.rest.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
}
