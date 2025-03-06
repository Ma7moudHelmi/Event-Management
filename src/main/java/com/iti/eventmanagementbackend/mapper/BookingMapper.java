package com.iti.eventmanagementbackend.mapper;

import com.iti.eventmanagementbackend.DTO.response.BookingDtoResponse;
import com.iti.eventmanagementbackend.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDtoResponse toDto(Booking booking) {
        BookingDtoResponse dto = new BookingDtoResponse();
        dto.setId(booking.getId());
        dto.setEmail(booking.getUsers().getEmail());
        dto.setFullName(booking.getUsers().getFirstName()+" "+booking.getUsers().getLastName());
        dto.setEventId(booking.getEvent().getId());
        dto.setUpdatedAt(booking.getUpdatedAt());
        dto.setEventTitle(booking.getEvent().getTitle());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setStatus(booking.getStatus());
        dto.setContactNo(booking.getContactNo());
        dto.setEventCategory(booking.getEvent().getCategory().getName());
        dto.setBusPoint(booking.getTransportation().getBusNumber());
        return dto;
    }
}
