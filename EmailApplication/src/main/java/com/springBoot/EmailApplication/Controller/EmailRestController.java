package com.springBoot.EmailApplication.Controller;


import com.springBoot.EmailApplication.Entity.Email;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("email")
public class EmailRestController {

    EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/record")
    public ResponseEntity<GenericResponse> findEmailByEmailId(@RequestParam String emailId){
        return emailService.getEmailByEmailId(emailId);
    }

    @GetMapping("/record/all")
    public ResponseEntity<List<Email>> getAllEmails(){
        return emailService.getAllEmails();
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> addEmail(@RequestBody Email email){
        return emailService.addEmail(email);
    }

    @DeleteMapping("/record")
    public ResponseEntity<GenericResponse> removeEmail(@RequestParam Integer Id){
        return emailService.removeEmail(Id);
    }
}
