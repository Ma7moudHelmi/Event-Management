package com.iti.eventmanagementbackend.service;


import com.iti.eventmanagementbackend.DTO.request.BookingDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.GetBookingDataDtoResponse;
import com.iti.eventmanagementbackend.model.Booking;
import com.iti.eventmanagementbackend.model.Event;
import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.repository.BookingRepository;
import com.iti.eventmanagementbackend.repository.EventRepository;
import com.iti.eventmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Booking createBooking(BookingDtoRequest bookingDtoRequest, Booking booking) {
        Users users = userRepository.findByEmail(bookingDtoRequest.getUserEmail());
        Event event = eventRepository.findById(bookingDtoRequest.getEventId()).orElse(null);
        booking.setUsers(users);
        booking.setContactNo(bookingDtoRequest.getContactNo());
        booking.setStatus("PENDING");
        booking.setEmail(bookingDtoRequest.getEmail());
        booking.setTotalAmount(booking.getTotalAmount());
        booking.setEvent(event);
        return bookingRepository.save(booking);
    }

    public ResponseEntity<List<Booking>> getBookings() {
        List<Booking> lstBookings = bookingRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(lstBookings);
    }

    public GetBookingDataDtoResponse getBookingData() {
        GetBookingDataDtoResponse getBookingDataDtoResponse = new GetBookingDataDtoResponse();
        getBookingDataDtoResponse.setConfirmedBookings(bookingRepository.countBookingByStatus("CONFIRMED"));
        getBookingDataDtoResponse.setPendingBookings(bookingRepository.countBookingByStatus("PENDING"));
        getBookingDataDtoResponse.setCanceledBookings(bookingRepository.countBookingByStatus("CANCELED"));
        getBookingDataDtoResponse.setTotalBookings(bookingRepository.count());
        return getBookingDataDtoResponse;
    }
}
