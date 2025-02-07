package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countBookingByStatus(String status);
}
