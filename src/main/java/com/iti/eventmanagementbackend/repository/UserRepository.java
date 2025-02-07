package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
