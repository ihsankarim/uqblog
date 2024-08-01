package com.uqblog.serverapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserNotFoundException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.request.UserRequestDTO;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.models.dtos.response.ApiResponseStatus;
import com.uqblog.serverapp.models.entities.User;
import com.uqblog.serverapp.repositories.UserRepository;
import com.uqblog.serverapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> registerUser(UserRequestDTO userRequestDTO)
            throws UserAlreadyExistsException, UserServiceLogicException {
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
            newUser.setPassword(userRequestDTO.getPassword());
            newUser.setEmail(userRequestDTO.getEmail());
            // newUser.setRoles(Role.USER);

            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(),
                            "new user account has been successfully created"));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getAllUsers() throws UserServiceLogicException {
        try {
            List<User> users = userRepository.findAll();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), users));
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getById(Integer id) throws UserNotFoundException, UserServiceLogicException {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found by id " + id));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), user));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getByUsername(String username) throws UserNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), user));
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateUser(UserRequestDTO userRequestDTO, Integer id)
            throws UserNotFoundException, UserServiceLogicException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            user.setUsername(userRequestDTO.getUsername());
            user.setPassword(userRequestDTO.getPassword());
            user.setEmail(userRequestDTO.getEmail());

            userRepository.save(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), "Update user was successfully"));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteUser(Integer id)
            throws  UserNotFoundException, UserServiceLogicException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            userRepository.delete(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), user));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceLogicException();
        }
    }



}
