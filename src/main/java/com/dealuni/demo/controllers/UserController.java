package com.dealuni.demo.controllers;

import com.dealuni.demo.models.User;
import com.dealuni.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //update user by id
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User newUserDetails) {
            User updatedUser = userService.updateUserById(id, newUserDetails);
            return ResponseEntity.ok(updatedUser);
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
