package com.iti.eventmanagementbackend.DTO.response;

public class RefreshTokenResponse {
    private String accessToken;

    public RefreshTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}