package com.uqblog.serverapp.models.dtos.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}
