package com.springBoot.EmailApplication.ExceptionHandler;

import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(UserAlreadyDeleted exc){
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(false, "Operation Failed", "User details are already deleted with id : " + exc),null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(UserAlreadyRegistered exc){
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(false, "Operation Failed", "User is already registered"),null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(UserNotFound exc){
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(false, "Operation Failed", "No User Found"),null
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(Exception exc){
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(false, "Operation Failed", "Internal Server Error"),null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
