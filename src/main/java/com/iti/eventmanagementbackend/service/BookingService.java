package com.iti.eventmanagementbackend.service;


import com.iti.eventmanagementbackend.DTO.request.BookingDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.BookingDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.GetBookingDataDtoResponse;
import com.iti.eventmanagementbackend.mapper.BookingMapper;
import com.iti.eventmanagementbackend.model.Booking;
import com.iti.eventmanagementbackend.model.BusTransportation;
import com.iti.eventmanagementbackend.model.Event;
import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.repository.BookingRepository;
import com.iti.eventmanagementbackend.repository.BusTransportationRepository;
import com.iti.eventmanagementbackend.repository.EventRepository;
import com.iti.eventmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final BookingMapper bookingMapper;

    private final BusTransportationRepository busTransportationRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          EventRepository eventRepository, BookingMapper bookingMapper,
                          BusTransportationRepository busTransportationRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.bookingMapper = bookingMapper;
        this.busTransportationRepository = busTransportationRepository;
    }

    public Booking createBooking(BookingDtoRequest bookingDtoRequest, Booking booking) {
        Users users = userRepository.findByEmail(bookingDtoRequest.getUserEmail());
        if (users == null) throw new IllegalArgumentException("User not found");
        Event event = eventRepository.findById(bookingDtoRequest.getEventId()).orElseThrow(IllegalArgumentException::new);
        BusTransportation bus = busTransportationRepository.findById(bookingDtoRequest.getBusId()).orElseThrow(IllegalArgumentException::new);
        booking.setTransportation(bus);
        booking.setUsers(users);
        booking.setContactNo(bookingDtoRequest.getContactNo());
        booking.setStatus("PENDING");
        booking.setEmail(bookingDtoRequest.getEmail());
        booking.setTotalAmount(booking.getTotalAmount());
        booking.setEvent(event);
        return bookingRepository.save(booking);
    }

    public ResponseEntity<List<BookingDtoResponse>> getBookings() {
        List<BookingDtoResponse> lstBookings = bookingRepository.findAll().stream().map(bookingMapper::toDto).collect(Collectors.toList());

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

    public List<BookingDtoResponse> getUserBookingData(Pageable pageable, String email) {
//        return bookingRepository.findAll(pageable).getContent();
        return bookingRepository.findAll(pageable).getContent()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<BookingDtoResponse> userUpdateBookingStatus(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);

        booking.setStatus("canceled");
        bookingRepository.save(booking);
        BookingDtoResponse bookingDtoResponse = new BookingMapper().toDto(booking);
        return ResponseEntity.ok(bookingDtoResponse);
    }

    public ResponseEntity<BookingDtoResponse> updateBookingStatus(Long bookingId,String status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);

        booking.setStatus(status);
        bookingRepository.save(booking);
        BookingDtoResponse bookingDtoResponse = new BookingMapper().toDto(booking);
        return ResponseEntity.ok(bookingDtoResponse);
    }

    public ResponseEntity<BookingDtoResponse> deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
        bookingRepository.delete(booking);
        return ResponseEntity.ok().build();
    }
}
