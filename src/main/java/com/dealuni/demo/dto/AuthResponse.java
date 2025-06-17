package com.dealuni.demo.dto;

import com.dealuni.demo.models.Role;

import java.util.Set;

public class AuthResponse {

    private String jwtToken;
    private Long id;
    private String username;
    private Set<Role> roles;

    public AuthResponse(String jwtToken, Long id, String username, Set<Role> roles) {
        this.jwtToken = jwtToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
