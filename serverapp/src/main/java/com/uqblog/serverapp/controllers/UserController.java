package com.uqblog.serverapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserNotFoundException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUser() throws UserServiceLogicException {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable Integer id)
            throws UserNotFoundException, UserServiceLogicException {
        return userServiceImpl.getById(id);
    }

    @GetMapping("/q")
    public ResponseEntity<ApiResponse<?>> getUserByUsername(@RequestParam String username)
            throws UserNotFoundException, UserServiceLogicException {
        return userServiceImpl.getByUsername(username);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO)
            throws UserAlreadyExistsException, UserServiceLogicException {

        return userServiceImpl.registerUser(userRequestDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody UserRequestDTO userRequestDTO,
            @PathVariable Integer id)
            throws UserNotFoundException, UserServiceLogicException {
        return userServiceImpl.updateUser(userRequestDTO, id);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUserById(@PathVariable Integer id)
            throws UserNotFoundException, UserServiceLogicException {
        return userServiceImpl.deleteUser(id);
    }
}
