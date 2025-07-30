package com.gateway.library.exception;

public class UserNotAuthenticateException extends RuntimeException {
    public UserNotAuthenticateException(String message) {
        super(message);
    }
}
