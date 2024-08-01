package com.uqblog.serverapp.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.request.LoginRequest;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.models.dtos.response.ApiResponseStatus;
import com.uqblog.serverapp.models.entities.Role;
import com.uqblog.serverapp.models.entities.User;
import com.uqblog.serverapp.repositories.UserRepository;
import com.uqblog.serverapp.services.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<ApiResponse<User>> registerUser(UserRequestDTO userRequestDTO)
            throws UserAlreadyExistsException {

        try {
            if (userRepository.findByEmail(userRequestDTO.getEmail()) != null) {
                throw new UserAlreadyExistsException(
                        "Registration Failed: User already exists with email " + userRequestDTO.getEmail());
            }
            if (userRepository.findByUsername(userRequestDTO.getUsername()) != null) {
                throw new UserAlreadyExistsException("Registration failed: user already with username");
            }

            User newUser = new User();
            newUser.setUsername(userRequestDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            newUser.setEmail(userRequestDTO.getEmail());

            // List<Role> roles = new ArrayList<>();
            // newUser.getRoles().forEach(role -> {
            // String roleName = role.name();
            // roles.add(roleName);
            // });

            // Set default role as USER
            newUser.setRoles(Collections.singletonList(Role.USER));

            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), newUser));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<User>> loginUser(LoginRequest loginRequest) throws UserServiceLogicException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            User getLogin = userRepository.findByUsername(loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), getLogin));
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()));

        return userRepository.findByUsername(input.getUsername());
    }

}
