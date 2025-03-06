package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Moderators;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ModeratorsRepository extends JpaRepository<Moderators, Long> {
    long countByIdIn(Set<Long> ids);
}
