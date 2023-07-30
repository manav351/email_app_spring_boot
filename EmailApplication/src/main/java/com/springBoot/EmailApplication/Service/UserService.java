package com.springBoot.EmailApplication.Service;

import com.springBoot.EmailApplication.DAO.UserDAO;
import com.springBoot.EmailApplication.Entity.User;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
import com.springBoot.EmailApplication.Entity.Status;
import com.springBoot.EmailApplication.ExceptionHandler.UserAlreadyDeleted;
import com.springBoot.EmailApplication.ExceptionHandler.UserAlreadyRegistered;
import com.springBoot.EmailApplication.ExceptionHandler.UserIdCannotBeNegative;
import com.springBoot.EmailApplication.ExceptionHandler.UserNotFound;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private UserDAO userDao;
    private EmailService emailService;

    @Autowired
    public UserService(UserDAO userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }

    public ResponseEntity<GenericResponseWithList> getUserByEmailId(String emailId) {
        User userFromDB;

        try{
            userFromDB = userDao.findUserbyEmailId(emailId);
        }catch(EmptyResultDataAccessException e){
            userFromDB = null;
        }

        if(userFromDB == null)
            throw new UserNotFound();

        return new ResponseEntity<>(
                new GenericResponseWithList(true, "Operation Successful : Search By Id", "",Collections.singletonList(userFromDB)),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<GenericResponse> addUserAndSendWelcomeMail(User userFromAPI) {
        User newUser = addUserToDB(userFromAPI);
        GenericResponse response = new GenericResponse();

        // Sending the mail to new user
        try{
            emailService.sendEmailViaId(newUser.getId());
            response.updateFields(true, "Operation Successful! User details stored + mail sent successfull", "", newUser);
        }catch(Exception e){
            response.updateFields(false,"Operation Failed", "User Details stored but mail not sent", newUser);
        }

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    private User addUserToDB(User userFromAPI){
        User userFromDB;

        try{
            userFromDB = userDao.findUserbyEmailId(userFromAPI.getEmailId());
        }catch (EmptyResultDataAccessException e){
            userFromDB = null;
        }

        if(userFromDB != null)
            throw new UserAlreadyRegistered("");

        User newUser = new User(
                0,
                userFromAPI.getFirstName(),
                userFromAPI.getLastName(),
                userFromAPI.getEmailId(),
                LocalDateTime.now().toString(),
                false,
                "00-00-00T00:00:00"
        );

        // Add user to DB
        newUser = userDao.saveUser(newUser);

        return newUser;
    }

    @Transactional
    public ResponseEntity<GenericResponse> removeUser(Integer id) {
        User userObj = userDao.findUserById(id);

        if(userObj == null)
            throw new UserAlreadyDeleted(id.toString());

        userDao.deleteUser(userObj);

        return new ResponseEntity<>(
                new GenericResponse(true, "User Details Successfully Deleted", "", userObj),
                HttpStatus.OK
        );
    }

    public ResponseEntity<GenericResponseWithList> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();

        return new ResponseEntity<>(
                new GenericResponseWithList(true, "Operation Successful : All Users", "",allUsers),
                HttpStatus.OK
        );
    }

    public ResponseEntity<GenericResponseWithList> getUserById(Integer id) {
        if(id < 0)
            throw new UserIdCannotBeNegative();

        User userObj = userDao.findUserById(id);

        if(userObj == null)
            throw new UserAlreadyDeleted(id.toString());

        return new ResponseEntity<>(
                new GenericResponseWithList(true, "Operation Successful", "",List.of(userObj)),
                HttpStatus.OK
        );
    }
}
