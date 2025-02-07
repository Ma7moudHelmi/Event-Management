package com.iti.eventmanagementbackend.DTO.request;

public class LoginRequest {
    private String Email;
    private String password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
