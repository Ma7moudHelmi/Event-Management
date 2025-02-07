package com.iti.eventmanagementbackend.DTO.response;

import lombok.Data;

@Data
public class GetBookingDataDtoResponse {
    private long totalBookings;
    private long confirmedBookings;
    private long pendingBookings;
    private long canceledBookings;
}
