package com.springBoot.EmailApplication.ExceptionHandler;


public class EmailAlreadyDeleted extends RuntimeException {
    public EmailAlreadyDeleted(String message) {
        super(message);
    }

    public EmailAlreadyDeleted(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyDeleted(Throwable cause) {
        super(cause);
    }
}
