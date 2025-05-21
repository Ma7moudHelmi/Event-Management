package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.BookingDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.BookingDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.GetBookingDataDtoResponse;
import com.iti.eventmanagementbackend.model.Booking;
import com.iti.eventmanagementbackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
    @GetMapping
    public ResponseEntity<List<BookingDtoResponse>> getBookings (){
        return bookingService.getBookings();
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDtoResponse> updateBookingStatus(@PathVariable Long bookingId,@RequestBody String status) {
        return bookingService.updateBookingStatus(bookingId,status);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
    @PostMapping("get-data")
    public GetBookingDataDtoResponse getBookingData(){
        return bookingService.getBookingData();
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDtoRequest bookingDtoRequest , Booking booking) {
        Booking savedBooking = bookingService.createBooking(bookingDtoRequest,booking);
        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping("/user")
    public List<BookingDtoResponse> getUserBookingData(@PageableDefault(page = 1,size = 10) Pageable pageable,@RequestParam(required = false) String email) {

        int page = pageable.getPageNumber() - 1;
        if (page<0) page =0;
        int size = pageable.getPageSize();
        Sort sort = pageable.getSort();
        Pageable adjustedPageable = PageRequest.of(page, size, sort);

        return bookingService.getUserBookingData(adjustedPageable,email);
    }

    @PatchMapping("/user/{bookingId}")
    public ResponseEntity<BookingDtoResponse> userUpdateBookingStatus(@PathVariable Long bookingId) {
        return bookingService.userUpdateBookingStatus(bookingId);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingDtoResponse> deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBooking(bookingId);
    }
}
