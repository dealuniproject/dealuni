package com.dealuni.demo.dto;

public class ChangePasswordResponse {
    private String message;

    public ChangePasswordResponse(String message) {
        this.message = message;
    }

    public ChangePasswordResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
