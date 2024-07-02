package com.uqblog.serverapp.models.dtos.exceptions;

public class UserServiceLogicException extends Exception{
    public UserServiceLogicException() {
        super("Something went wrong. Please try again later!");
    }
}