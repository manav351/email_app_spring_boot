package com.springBoot.EmailApplication.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseWithList {
    Status status;
    List<User> data;

    public GenericResponseWithList(Boolean status, String message, String error, List<User> userList){
        Status newStatus = new Status(status, message, error);
        this.status = newStatus;
        this.data = userList;
    }
}
