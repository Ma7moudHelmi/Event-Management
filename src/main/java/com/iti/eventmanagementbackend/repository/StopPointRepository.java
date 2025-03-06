package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.StopPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopPointRepository extends JpaRepository<StopPoint, Long> {}
