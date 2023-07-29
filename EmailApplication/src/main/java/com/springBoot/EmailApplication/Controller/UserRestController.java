package com.springBoot.EmailApplication.Controller;


import com.springBoot.EmailApplication.Entity.User;
import com.springBoot.EmailApplication.Entity.GenericResponse;
import com.springBoot.EmailApplication.Entity.GenericResponseWithList;
import com.springBoot.EmailApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("user")
public class UserRestController {

    UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/records")
    public ResponseEntity<GenericResponseWithList> findUsers(
            @RequestParam(required = false) String emailId,
            @RequestParam(required = false) Integer id

    ){
        if (id != null)
            return userService.getUserById(id);
        else if(emailId != null)
            return userService.getUserByEmailId(emailId);
        else
            return userService.getAllUsers();
    }


    @GetMapping("/records/all")
    public ResponseEntity<GenericResponseWithList> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/record")
    public ResponseEntity<GenericResponse> addUserDetails(@RequestBody User userDetails){
        return userService.addUserAndSendWelcomeMail(userDetails);
    }

    @DeleteMapping("/record")
    public ResponseEntity<GenericResponse> removeUserDetails(@RequestParam Integer Id){
        return userService.removeUser(Id);
    }
}
