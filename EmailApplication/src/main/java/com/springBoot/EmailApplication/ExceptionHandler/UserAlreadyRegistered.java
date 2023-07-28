package com.springBoot.EmailApplication.ExceptionHandler;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }

    public UserAlreadyRegistered(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyRegistered(Throwable cause) {
        super(cause);
    }
}
