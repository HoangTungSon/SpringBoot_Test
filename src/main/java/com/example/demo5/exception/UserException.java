package com.example.demo5.exception;

public class UserException extends RuntimeException {
    public UserException(Long id) {
        super("Could not found user: " + id);
    }
}
