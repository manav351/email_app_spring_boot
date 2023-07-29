package com.springBoot.EmailApplication.Service;

import com.springBoot.EmailApplication.DAO.UserDAO;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
import com.springBoot.EmailApplication.Entity.Status;
import com.springBoot.EmailApplication.Entity.User;
import com.springBoot.EmailApplication.ExceptionHandler.MailAlreadySent;
import com.springBoot.EmailApplication.ExceptionHandler.UserNotFound;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    UserDAO userDAO;
    JavaMailSender javaMailSender;

    @Autowired
    public EmailService(UserDAO userDAO, JavaMailSender javaMailSender) {
        this.userDAO = userDAO;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public ResponseEntity<GenericResponse> sendEmailViaId(Integer id) {
        User userObj = userDAO.findUserById(id);
        userDAO.saveUser(sendEmailViaObject(userObj));
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(true, "Operation Successful",""), userObj
                ),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<GenericResponse> sendEmailViaEmailId(String emailId) {
        User userObj = userDAO.findUserbyEmailId(emailId);
        userDAO.saveUser(sendEmailViaObject(userObj));
        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(true, "Operation Successful",""), userObj
                ),
                HttpStatus.OK
        );
    }

    private User sendEmailViaObject(User user){
        if(user == null)
            throw new UserNotFound("");

        if(user.getSentStatus())
            throw new MailAlreadySent("");

        sendEmail(
                user.getEmailId(),
                "Hi " + user.getFirstName() + "!",
                "Thank you for registering" + user.getFirstName()
        );

        user.setTriggerTime(LocalDateTime.now().toString());
        user.setSentStatus(true);

        return user;
    }


    private User resetUserByObj(User user){
        if(user.getSentStatus()){
            user.setSentStatus(false);
            user.setTriggerTime("");
            userDAO.saveUser(user);
        }
        return user;
    }

    @Transactional
    public ResponseEntity<GenericResponseWithList> resetAllUsers(){
        List<User> allUsers = userDAO.getAllUsers();

        for(User user:allUsers){
            user = resetUserByObj(user);
        }

        return new ResponseEntity<>(
                new GenericResponseWithList(
                        new Status(true, "Operation Successful", ""), allUsers
                ),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<GenericResponse> resetUserById(Integer id){
        User user = userDAO.findUserById(id);
        if(user == null)
            throw new UserNotFound("");

        user = resetUserByObj(user);

        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(true, "Operation Successful",""), user
                ),
                HttpStatus.OK
        );
    }

    @Async
    public void sendEmail(String toEmail, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("manav121999@gmail.com");

        javaMailSender.send(mailMessage);
    }

    public ResponseEntity<GenericResponse> sendEmailViaBoth(Integer id, String emailId) {
        User userFromDB = userDAO.findUserById(id);

        if (userFromDB.getEmailId().equals(emailId))
            userDAO.saveUser(sendEmailViaObject(userFromDB));
        else
            return new ResponseEntity<>(
                    new GenericResponse(
                            new Status(false, "Operation Failed", "Email Id and User Id doesn't match"),
                            null
                    ), HttpStatus.BAD_REQUEST
            );

        return new ResponseEntity<>(
                new GenericResponse(
                        new Status(true, "Operation Successful",""), userFromDB
                ),
                HttpStatus.OK
        );
    }
}

// TODO: Convert Multiple Email to unique Emails
// TODO: Separate trigger tables as there can be multiple hit for single table
// TODO: Add Email Validator