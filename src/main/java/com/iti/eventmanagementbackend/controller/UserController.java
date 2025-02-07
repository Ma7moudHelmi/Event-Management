package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfile(HttpServletRequest request) {
        Users user = userService.getUserProfile(request);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Users> getUserById(@PathVariable String email) {
        return userService.getUserById(email);
    }

//    @PatchMapping("/profile")
//    public ResponseEntity<Users> updateUserProfile(@RequestBody Users userDto) {
//        Users    updatedUser = userService.updateUserProfile(userDto);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<Users> updateUserById(@PathVariable Long id, @RequestBody Users userDto) {
        Users updatedUser = userService.updateUserById(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
