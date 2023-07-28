package com.springBoot.EmailApplication.ExceptionHandler;


public class UserAlreadyDeleted extends RuntimeException {
    public UserAlreadyDeleted(String message) {
        super(message);
    }

    public UserAlreadyDeleted(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyDeleted(Throwable cause) {
        super(cause);
    }
}
