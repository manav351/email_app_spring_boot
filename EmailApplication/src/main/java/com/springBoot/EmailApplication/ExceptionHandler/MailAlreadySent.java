package com.springBoot.EmailApplication.ExceptionHandler;

public class MailAlreadySent extends RuntimeException {
    public MailAlreadySent() {
    }

    public MailAlreadySent(String message) {
        super(message);
    }

    public MailAlreadySent(String message, Throwable cause) {
        super(message, cause);
    }

    public MailAlreadySent(Throwable cause) {
        super(cause);
    }
}
