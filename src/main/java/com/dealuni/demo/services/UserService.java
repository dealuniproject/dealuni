package com.dealuni.demo.services;

import com.dealuni.demo.dto.UserRequest;
import com.dealuni.demo.dto.UserResponse;
import com.dealuni.demo.models.Role;
import com.dealuni.demo.models.University;
import com.dealuni.demo.models.User;
import com.dealuni.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void registerNewUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }

        String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);

        user.setVerified(false);

        userRepository.save(user);

        emailService.sendVerificationEmail(user.getUsername(), verificationCode);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Utilizatorul nu a fost găsit."));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public List<UserResponse> getAllExistingUsers() {
        List<User> allExistingUsers = userRepository.findAll();
        return convertUserEntityToUserResponse(allExistingUsers);
    }

    public UserResponse getUserById(Long Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("Utilizatorul nu a fost găsit."));
        return convertUserEntityToUserResponse(user);
    }

    public UserResponse updateUserById(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit."));

        if (userRequest.getFirstName() != null) {
            existingUser.setFirstName(userRequest.getFirstName());
        }

        if (userRequest.getLastName() != null) {
            existingUser.setLastName(userRequest.getLastName());
        }

        if (userRequest.getUniversityName() != null) {
            existingUser.setUniversityName(University.valueOf(userRequest.getUniversityName()));
        }

        if (userRequest.getPassword() != null) {
            existingUser.setPassword(userRequest.getPassword());
        }

        if (userRequest.getRoles() != null) {
            Set<Role> newRoles = userRequest.getRoles().stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
            existingUser.setRoles(newRoles);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertUserEntityToUserResponse(updatedUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Utilizatorul nu a fost găsit."));
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost găsit."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Parola curentă este incorectă.");
        }

        if (!newPassword.matches("^(?=.*[A-Z]).{8,}$")) {
            throw new IllegalArgumentException("Parola nouă trebuie să aibă cel puțin 8 caractere și să conțină cel puțin o literă mare.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<UserResponse> convertUserEntityToUserResponse(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(convertUserEntityToUserResponse(user));
        }
        return userResponses;
    }

    public UserResponse convertUserEntityToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUniversityName(user.getUniversityName().name());
        userResponse.setRoles(user.getRoles());
        userResponse.setVerified(user.getVerified());
        return userResponse;
    }

    public User convertUserRequestToUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUniversityName(University.valueOf(userRequest.getUniversityName()));
        user.setPassword(userRequest.getPassword());
        return user;
    }

    public void validateUser(UserRequest userRequest) {

        if (userRequest.getUsername() == null || userRequest.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username-ul nu poate fi null.");
        }

        if (userRequest.getUsername().length() > 60) {
            throw new IllegalArgumentException("Username-ul nu poate depăși 60 de caractere.");
        }

        String regex = "^[a-zA-Z0-9._-]+@(stud\\.upb\\.ro|student\\.utcb\\.ro|stud\\.usamv\\.ro|stud\\.unibuc\\.ro|stud\\.umfcd\\.ro|student\\.ase\\.ro|student\\.snspa\\.ro|stud\\.utcluj\\.ro|stud\\.usamvcluj\\.ro|stud\\.ubbcluj\\.ro|stud\\.umfcluj\\.ro|student\\.uaic\\.ro|student\\.umfiasi\\.ro|student\\.upt\\.ro|student\\.usvt\\.ro|e-uvt\\.ro|student\\.umft\\.ro|student\\.uoradea\\.ro|edu\\.ucv\\.ro|student\\.umfst\\.ro|uab\\.ro|uav\\.ro|ub\\.ro|unitbv\\.ro|univ-ovidius\\.ro|cmu-edu\\.eu|ugal\\.ro|upg-ploiesti\\.ro|ulbsibiu\\.ro|usv\\.ro|valahia\\.ro|utgjiu\\.ro|anmb\\.ro|afahc\\.ro|aft\\.ro)$";

        if (!userRequest.getUsername().matches(regex)) {
            throw new IllegalArgumentException("Email-ul trebuie să aparțină unui domeniu instituțional valid.");
        }

        if (userRequest.getFirstName() == null || userRequest.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("Prenumele nu poate fi null.");
        }

        if (userRequest.getFirstName().length() > 50) {
            throw new IllegalArgumentException("Prenumele nu poate depăși 50 de caractere.");
        }

        if (!userRequest.getFirstName().matches("^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$")) {
            throw new IllegalArgumentException("Prenumele trebuie să înceapă cu literă mare și să conțină doar litere, cratimă sau spațiu.");
        }

        if (userRequest.getLastName() == null || userRequest.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi null.");
        }

        if (userRequest.getLastName().length() > 50) {
            throw new IllegalArgumentException("Numele nu poate depăși 50 de caractere.");
        }

        if (!userRequest.getLastName().matches("^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$")) {
            throw new IllegalArgumentException("Numele trebuie să înceapă cu literă mare și să conțină doar litere, cratimă sau spațiu.");
        }

        if (userRequest.getUniversityName() == null) {
            throw new IllegalArgumentException("Numele universității nu poate fi null.");
        }

        if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Parola nu poate fi null.");
        }

        if (!userRequest.getPassword().matches("^(?=.*[A-Z]).{8,}$")) {
            throw new IllegalArgumentException("Parola trebuie să aibă cel puțin 8 caractere și să conțină cel puțin o literă mare.");
        }
    }
}
