package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.BusTransportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BusTransportationRepository extends JpaRepository<BusTransportation, Long> {
    long countByIdIn(Set<Long> busIds);
}
