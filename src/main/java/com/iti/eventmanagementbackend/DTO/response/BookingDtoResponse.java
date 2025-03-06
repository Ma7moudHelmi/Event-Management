package com.iti.eventmanagementbackend.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDtoResponse {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String email;
    private String fullName;
    private String contactNo;
    private Long eventId;
    private String eventTitle;
    private String eventCategory;
    private String busPoint;
}
