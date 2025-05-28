package com.dealuni.demo.services;

import com.dealuni.demo.dto.UserRequest;
import com.dealuni.demo.dto.UserResponse;
import com.dealuni.demo.models.University;
import com.dealuni.demo.models.User;
import com.dealuni.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//Ne spune ca e un service class
@Service
public class UserService {

    //Constructor injection, we are injecting user repository inside the user service
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse registerNewUser(UserRequest userRequest) {
        User user = convertUserRequestToUserEntity(userRequest);
        User savedUser = userRepository.save(user);
        return convertUserEntityToUserResponse(savedUser);
    }

    //get all users
    public List<UserResponse> getAllExistingUsers() {
        List<User> allExistingUsers = userRepository.findAll();
        return convertUserEntityToUserResponse(allExistingUsers);
    }

    //get user by id
    public UserResponse getUserById(Long Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("User not found"));
        return convertUserEntityToUserResponse(user);
    }

    //update user by id
    public UserResponse updateUserById(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        //daca exista un firstname in request, ii dau userului existent un alt firstname
        if (userRequest.getFirstName() != null) {
            existingUser.setFirstName(userRequest.getFirstName());
        }

        //daca exista un lastname in request, ii dau userului un alt lastname
        if (userRequest.getLastName() != null) {
            existingUser.setLastName(userRequest.getLastName());
        }

        //daca exista o universitate in request, ii dau userului o alta universitate
        if (userRequest.getUniversityName() != null) {
            existingUser.setUniversityName(University.valueOf(userRequest.getUniversityName()));
        }

        //daca exista o parola in request, ii dau userului o alta parola
        if (userRequest.getPassword() != null) {
            existingUser.setPassword(userRequest.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        return convertUserEntityToUserResponse(updatedUser);
    }

    //delete user by id
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    //metod overload
    public List<UserResponse> convertUserEntityToUserResponse(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(convertUserEntityToUserResponse(user));
        }
        return userResponses;
    }

    //metoda pentru converti un obiect user intr-un user response
    public UserResponse convertUserEntityToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUniversityName(user.getUniversityName().name());
        userResponse.setVerified(user.getVerified());
        userResponse.setBlocked(user.getBlocked());
        return userResponse;
    }

    //metoda pentru converti un request intr-un obiect user
    public User convertUserRequestToUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUniversityName(University.valueOf(userRequest.getUniversityName()));
        user.setPassword(userRequest.getPassword());
        return user;
    }
}
