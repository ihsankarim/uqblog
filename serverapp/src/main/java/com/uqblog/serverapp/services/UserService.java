package com.uqblog.serverapp.services;

import org.springframework.http.ResponseEntity;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserNotFoundException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;

public interface UserService {
        ResponseEntity<ApiResponse<?>> registerUser(UserRequestDTO userRequestDTO)
                        throws UserAlreadyExistsException, UserServiceLogicException;

        ResponseEntity<ApiResponse<?>> getAllUsers()
                        throws UserServiceLogicException;

        ResponseEntity<ApiResponse<?>> getById(Integer id)
                        throws UserNotFoundException, UserServiceLogicException;

        ResponseEntity<ApiResponse<?>> getByUsername(String username)
                        throws UserNotFoundException;

        ResponseEntity<ApiResponse<?>> updateUser(UserRequestDTO userRequestDTO, Integer id)
                        throws UserNotFoundException, UserServiceLogicException;

        ResponseEntity<ApiResponse<?>> deleteUser(Integer id)
                        throws UserServiceLogicException, UserNotFoundException;
}
