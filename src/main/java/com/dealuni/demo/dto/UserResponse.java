package com.dealuni.demo.dto;

import com.dealuni.demo.models.Role;

import java.util.Set;

public class UserResponse {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String universityName;
    private Set<Role> roles;
    private Boolean isVerified = false;

    public UserResponse(Long id, String username, String firstName, String lastName, String universityName, Set<Role> roles, Boolean isVerified) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.universityName = universityName;
        this.roles = roles;
        this.isVerified = isVerified;
    }

    public UserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

}

