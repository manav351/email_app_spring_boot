package com.springBoot.EmailApplication.Controller;


import com.springBoot.EmailApplication.Entity.Email;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
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
    public ResponseEntity<GenericResponseWithList> findEmailByEmailId(@RequestParam(required = false) String emailId, @RequestParam(required = false) Integer id){
        if (id != null)
            return emailService.getEmailById(id);
        else if(emailId != null)
            return emailService.getEmailByEmailId(emailId);
        else
            emailService.getAllEmails();
        return null;
    }


    @GetMapping("/record/all")
    public ResponseEntity<GenericResponseWithList> getAllEmails(){
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
