package com.uqblog.serverapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.request.LoginRequest;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.models.dtos.response.LoginResponse;
import com.uqblog.serverapp.models.entities.User;
import com.uqblog.serverapp.services.JwtService;
import com.uqblog.serverapp.services.impl.AuthServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO)
            throws UserAlreadyExistsException {
        return authServiceImpl.registerUser(userRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authServiceImpl.authenticate(loginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
