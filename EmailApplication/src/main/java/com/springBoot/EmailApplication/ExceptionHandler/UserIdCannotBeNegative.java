package com.springBoot.EmailApplication.ExceptionHandler;

public class UserIdCannotBeNegative extends RuntimeException{
    public UserIdCannotBeNegative() {
    }

    public UserIdCannotBeNegative(String message) {
        super(message);
    }

    public UserIdCannotBeNegative(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdCannotBeNegative(Throwable cause) {
        super(cause);
    }
}
