package com.springBoot.EmailApplication.Service;

import com.springBoot.EmailApplication.DAO.EmailDAO;
import com.springBoot.EmailApplication.Entity.Email;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private EmailDAO emailDao;

    @Autowired
    public EmailService(EmailDAO emailDao) {
        this.emailDao = emailDao;
    }

    public ResponseEntity<GenericResponse> getEmail(String emailId) {
        Email emailDetails = emailDao.getEmailbyEmailId(emailId);
        GenericResponse response = new GenericResponse();

        response.setData(emailDetails);
        response.setStatus(
                new Status(true, "Operation Successful", "")
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
