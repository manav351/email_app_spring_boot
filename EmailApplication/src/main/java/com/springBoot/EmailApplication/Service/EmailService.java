package com.springBoot.EmailApplication.Service;

import com.springBoot.EmailApplication.DAO.EmailDAO;
import com.springBoot.EmailApplication.Entity.Email;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.Status;
import com.springBoot.EmailApplication.ExceptionHandler.EmailAlreadyDeleted;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    private EmailDAO emailDao;

    @Autowired
    public EmailService(EmailDAO emailDao) {
        this.emailDao = emailDao;
    }

    public ResponseEntity<GenericResponse> getEmailByEmailId(String emailId) {
        Email emailDetails = emailDao.findEmailbyEmailId(emailId);
        GenericResponse response = new GenericResponse();

        response.setData(emailDetails);
        response.setStatus(
                new Status(true, "Operation Successful", "")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<GenericResponse> addEmail(Email email) {
        email.setSentStatus(false);
        email.setSubmitTime(LocalDateTime.now().toString());
        email.setId(0);
        email = emailDao.saveEmail(email);

        GenericResponse response = new GenericResponse(
                new Status(true, "Email Successfully Registered", ""),
                email
        );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GenericResponse> removeEmail(Integer id) {
        Email emailObj = emailDao.findEmailById(id);

        if(emailObj == null)
            throw new EmailAlreadyDeleted(id.toString());

        emailDao.deleteEmail(emailObj);

        GenericResponse response = new GenericResponse(
                new Status(true, "Email Successfully Deleted", ""),
                emailObj
        );

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<List<Email>> getAllEmails() {
        List<Email> allEmails = emailDao.getAllEmails();
        return new ResponseEntity<>(allEmails, HttpStatus.OK);
    }
}
