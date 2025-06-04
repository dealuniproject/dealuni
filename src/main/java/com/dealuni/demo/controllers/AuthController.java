package com.dealuni.demo.controllers;

import com.dealuni.demo.dto.*;
import com.dealuni.demo.models.Role;
import com.dealuni.demo.models.University;
import com.dealuni.demo.models.User;
import com.dealuni.demo.repositories.UserRepository;
import com.dealuni.demo.services.UserService;
import com.dealuni.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

        //verificam daca username-ul exista
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        //mapam AuthRequest la User Entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUniversityName(University.valueOf(registerRequest.getUniversityName()));

        //dam roluri
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        } else {
            user.setRoles(registerRequest.getRoles());
        }

        //inregistram user-ul folosind UserService
        userService.registerNewUser(user);

        //cream un obiect response
        RegisterResponse response = new RegisterResponse(
                user.getId(),
                "User-ul s-a înregistrat cu succes.",
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getUniversityName().name(),
                user.getRoles()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest response) {

        try {

            //autetinficam user-ul
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            //facem autentificarea securizata
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //obtinem detalii despre User
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //generam JWT token

            String jwt = jwtUtil.generateToken(userDetails);
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true) //nu permite la JavaScript sa ia cookie
                    .secure(false) //IMPORTANT SA SCHIMBAM LA PRODUCTION IN TRUE
                    .path("/") //cookies sunt disponibili in toata aplicatia
                    .maxAge(10 * 60 * 60) // valida pentru 10h
                    .sameSite("Strict") //Lax & none
                    .build();

            //cream obiect response
            AuthResponse authResponse = new AuthResponse(
                    "Autentificare cu succes.",
                    userDetails.getUsername(),
                    userService.findUserByUsername(userDetails.getUsername()).getRoles()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authResponse);

            //returnam response cu cookie-header si body
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Username sau parolă incorectă.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //CHANGE TO TRUE WHEN PUSHING TO PRODUCTION
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Te-ai deconectat.");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()  || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nu este autentificat!");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(
                "Autentificat",
                user.getUsername(),
                user.getRoles()
        ));
    }
}
