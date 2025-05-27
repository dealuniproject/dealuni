package com.dealuni.demo.controllers;

import com.dealuni.demo.models.User;
import com.dealuni.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Ne spune ca e un controller
@RestController
//Mapping all the requests
@RequestMapping("/api/users")
public class UserController {

    //Constructor injection, we are injecting user service inside the user controler
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        User newUser = userService.registerNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
