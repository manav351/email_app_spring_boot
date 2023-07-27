package com.springBoot.EmailApplication.Controller;


import com.springBoot.EmailApplication.Entity.Email;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("email")
public class EmailRestController {

    EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/records")
    public ResponseEntity<GenericResponse> getSentRecords(@RequestParam String emailId){
        return emailService.getEmail(emailId);
    }
}
