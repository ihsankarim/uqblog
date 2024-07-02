package com.uqblog.serverapp.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.uqblog.serverapp.models.dtos.exceptions.UserAlreadyExistsException;
import com.uqblog.serverapp.models.dtos.exceptions.UserNotFoundException;
import com.uqblog.serverapp.models.dtos.exceptions.UserServiceLogicException;
import com.uqblog.serverapp.models.dtos.response.ApiResponse;
import com.uqblog.serverapp.models.dtos.response.ApiResponseStatus;

@RestControllerAdvice
public class UserServiceExceptionHandler {
 @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserServiceLogicException.class)
    public ResponseEntity<ApiResponse<?>> UserServiceLogicExceptionHandler(UserServiceLogicException exception) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponseStatus.FAIL.name(), errorMessage.toString()));
    }

}
