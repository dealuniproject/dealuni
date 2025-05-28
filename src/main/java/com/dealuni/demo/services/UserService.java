package com.dealuni.demo.services;

import com.dealuni.demo.models.User;
import com.dealuni.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Ne spune ca e un service class
@Service
public class UserService {

    //Constructor injection, we are injecting user repository inside the user service
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUser(User user) {
        return userRepository.save(user);
    }

    //get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //get user by id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    //update user by id
    public User updateUserById(Long id, User newUserDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        //daca exista un firstname in request, ii dau userului existent un alt firstname
        if (newUserDetails.getFirstName() != null) {
            existingUser.setFirstName(newUserDetails.getFirstName());
        }

        //daca exista un lastname in request, ii dau userului un alt lastname
        if (newUserDetails.getLastName() != null) {
            existingUser.setLastName(newUserDetails.getLastName());
        }

        //daca exista o universitate in request, ii dau userului o alta universitate
        if (newUserDetails.getUniversity() != null) {
            existingUser.setUniversity(newUserDetails.getUniversity());
        }

        //daca exista o parola in request, ii dau userului o alta parola
        //if (newUserDetails.getPassword() != null){
        //    existingUser.setPassword(newUserDetails.getPassword());
        //}
        return userRepository.save(existingUser);
    }

    //delete user by id
    public void deleteUserById(Long id) {
        User existingUser = getUserById(id);
        userRepository.deleteById(id);
    }

}
