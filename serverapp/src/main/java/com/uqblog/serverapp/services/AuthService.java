package com.uqblog.serverapp.services;

import org.springframework.http.ResponseEntity;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.request.LoginRequest;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.models.entities.User;

public interface AuthService {
    ResponseEntity<ApiResponse<User>> registerUser(UserRequestDTO userRequestDTO)
            throws UserAlreadyExistsException;

    ResponseEntity<ApiResponse<User>> loginUser(LoginRequest loginRequest) throws UserServiceLogicException;
}
