package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public Users getUserProfile(HttpServletRequest request) {
        String token = WebUtils.getCookie(request,"accessToken").getValue();
        String email = jwtService.extractUserName(token);
        return userRepository.findByEmail(email);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<Users> getUserById(String email) {
        Users user = userRepository.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

//    public Users updateUserProfile(Users user) {
//        Long currentUserId = getCurrentUserId();
//        Optional<Users> userOptional = userRepository.findById(currentUserId);
//        Users existingUser = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
//        updateUserEntity(existingUser, user);
//        userRepository.save(existingUser);
//        return existingUser;
//    }

    public Users updateUserById(Long id, Users user) {
        Optional<Users> userOptional = userRepository.findById(id);
        Users existingUser = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        updateUserEntity(existingUser, user);
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private void updateUserEntity(Users existingUser, Users user) {
        // Update Users entity with new values
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setContactNo(user.getContactNo());
        existingUser.setGender(user.getGender());
    }

//    private Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getDetails() instanceof JwtService) {
//            JwtService jwtTokenDetails = (JwtService) authentication.getDetails();
//            return jwtTokenDetails.
//        } else {
//            throw new RuntimeException("User not authenticated");
//        }
//    }
}

