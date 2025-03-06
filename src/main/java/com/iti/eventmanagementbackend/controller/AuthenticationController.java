package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.LoginRequest;
import com.iti.eventmanagementbackend.DTO.response.LoginResponse;
import com.iti.eventmanagementbackend.DTO.response.RefreshTokenResponse;
import com.iti.eventmanagementbackend.service.AppUserDetailsServices;
import com.iti.eventmanagementbackend.service.AuthService;
import com.iti.eventmanagementbackend.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) {
        try {

            System.out.println("Authentication Successful");

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetails userDetails = context.getBean(AppUserDetailsServices.class).loadUserByUsername(loginRequest.getEmail());
            final String accessToken = jwtService.generateAccessToken(userDetails);
            final String refreshToken = jwtService.generateRefreshToken(userDetails);
            authService.saveRefreshTokenInHttpOnlyCookie(response, refreshToken);
            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } catch (AuthenticationException ex) {
            throw new AuthenticationException("") {
            };
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request) {
        if (WebUtils.getCookie(request, "refreshToken") != null) {
            RefreshTokenResponse refreshTokenResponse = authService.refreshAccessToken(WebUtils.getCookie(request, "refreshToken").getValue());
            return ResponseEntity.ok(refreshTokenResponse);
        }
         throw new JwtException("Refresh token not found or invalid");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jwtService.logout(request, response);
    }
}
