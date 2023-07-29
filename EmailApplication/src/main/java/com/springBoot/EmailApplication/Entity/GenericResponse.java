package com.springBoot.EmailApplication.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    Status status;
    User data;

    public GenericResponse(Boolean status, String message, String error, User user){
        Status newStatus = new Status(status, message, error);
        this.status = newStatus;
        this.data = user;
    }
}
