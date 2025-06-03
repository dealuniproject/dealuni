package com.dealuni.demo.dto;

import com.dealuni.demo.models.Role;

import java.util.Set;

public class RegisterResponse {

    private Long id;
    private String message;
    private String username;
    private String firstName;
    private String lastName;
    private String universityName;
    private Set<Role> roles;

    public RegisterResponse(Long id, String message, String username, String firstName, String lastName, String universityName, Set<Role> roles) {
        this.id = id;
        this.message = message;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.universityName = universityName;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
