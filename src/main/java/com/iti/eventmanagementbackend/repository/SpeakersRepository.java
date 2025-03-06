package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Speakers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SpeakersRepository extends JpaRepository<Speakers, Long> {
    long countByIdIn(Set<Long> ids);
}
