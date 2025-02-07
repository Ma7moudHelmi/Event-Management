package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.BookingDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.GetBookingDataDtoResponse;
import com.iti.eventmanagementbackend.model.Booking;
import com.iti.eventmanagementbackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;


    @GetMapping
    public ResponseEntity<List<Booking>> getBookings (){
        return bookingService.getBookings();
    }

    @PostMapping("get-data")
    public GetBookingDataDtoResponse getBookingData(){
        System.out.println("getBookingData");
        return bookingService.getBookingData();
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDtoRequest bookingDtoRequest , Booking booking) {
        Booking savedBooking = bookingService.createBooking(bookingDtoRequest,booking);
        return ResponseEntity.ok(savedBooking);
    }
}
