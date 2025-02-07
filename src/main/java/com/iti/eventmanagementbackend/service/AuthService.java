package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.DTO.response.RefreshTokenResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        try {
            String userName = jwtService.extractUserName(refreshToken);
            UserDetails userDetails = context.getBean(AppUserDetailsServices.class).loadUserByUsername(userName);

            if (jwtService.validateToken(refreshToken, userDetails)) {
                String newAccessToken = jwtService.generateAccessToken(userDetails);

                return new RefreshTokenResponse(newAccessToken);
            } else {
                throw new IllegalArgumentException("Invalid Refresh Token");
            }
        } catch (JwtException message) {
            return null;
        }

    }

    public void saveRefreshTokenInHttpOnlyCookie(HttpServletResponse response, String refreshToken) {
        // Create the cookie with the refresh token
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        // Set the cookie attributes
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        // Add the cookie to the response
        response.addCookie(refreshTokenCookie);
    }

}
