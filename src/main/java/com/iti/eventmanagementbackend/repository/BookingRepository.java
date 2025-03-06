package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countBookingByStatus(String status);
    Page<Booking> findAll(Pageable pageable);
}
