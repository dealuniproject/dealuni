package com.dealuni.demo.services;

import com.dealuni.demo.models.User;
import com.dealuni.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
