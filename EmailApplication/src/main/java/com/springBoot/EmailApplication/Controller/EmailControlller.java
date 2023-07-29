package com.springBoot.EmailApplication.Controller;

import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
import com.springBoot.EmailApplication.Service.EmailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailControlller {

    EmailService emailService;

    @Autowired
    public EmailControlller(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse> sendEmail(
            @RequestParam(required = false) String email_id,
            @RequestParam(required = false) Integer id
    ){
        if(id!=null && email_id==null)
            return emailService.sendEmailViaId(id);
        else if (id==null && email_id!=null)
            return emailService.sendEmailViaEmailId(email_id);
        else
            return emailService.sendEmailViaBoth(id, email_id);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<GenericResponse> sendEmail(@PathVariable Integer id){
//        return emailService.sendEmailViaId(id);
//    }

    @PostMapping("/reset/all")
    public ResponseEntity<GenericResponseWithList> resetAllEmails(){
        return emailService.resetAllUsers();
    }

    @PostMapping("/reset/{id}")
    public ResponseEntity<GenericResponse> resetEmailById(@PathVariable Integer id){
        return emailService.resetUserById(id);
    }
}
