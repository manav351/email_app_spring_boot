package com.springBoot.EmailApplication.Service;

import com.springBoot.EmailApplication.DAO.UserDAO;
import com.springBoot.EmailApplication.Entity.User;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
import com.springBoot.EmailApplication.Entity.Status;
import com.springBoot.EmailApplication.ExceptionHandler.UserAlreadyDeleted;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private UserDAO userDao;

    @Autowired
    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity<GenericResponseWithList> getUserByEmailId(String emailId) {
        List<User> user = userDao.findUserbyEmailId(emailId);

        return new ResponseEntity<>(
                new GenericResponseWithList(
                        new Status(true, "Operation Successful : Search By Id", ""), user
                ),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<GenericResponse> addUser(User userFromAPI) {
        userFromAPI.setSentStatus(false);
        userFromAPI.setRegisterTime(LocalDateTime.now().toString());
        userFromAPI.setId(0);
        userFromAPI.setTriggerTime("00:00:00");

        userFromAPI = userDao.saveUser(userFromAPI);

        GenericResponse response = new GenericResponse(
                new Status(true, "User Details stored successfully", ""),
                userFromAPI
        );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GenericResponse> removeUser(Integer id) {
        User userObj = userDao.findUserById(id);

        if(userObj == null)
            throw new UserAlreadyDeleted(id.toString());

        userDao.deleteUser(userObj);

        GenericResponse response = new GenericResponse(
                new Status(true, "User Details Successfully Deleted", ""),
                userObj
        );

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<GenericResponseWithList> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        return new ResponseEntity<>(
                new GenericResponseWithList(
                        new Status(true, "Operation Successful : All Users", ""), allUsers
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<GenericResponseWithList> getUserById(Integer id) {
        User userObj = userDao.findUserById(id);

        if(userObj == null)
            throw new UserAlreadyDeleted(id.toString());

        return new ResponseEntity<>(
                new GenericResponseWithList(
                        new Status(true, "Operation Successful", ""),
                        List.of(userObj)
                ),
                HttpStatus.OK
        );
    }
}
