package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countBookingByStatus(String status);
    Page<Booking> findAll(Pageable pageable);

    @Query("SELECT b from Booking b where b.users.id=?1 and b.event.id=?2")
    Optional<Booking> findByUsers_IdAndEvent_Id(Long users_Id, Long events_Id);
}
