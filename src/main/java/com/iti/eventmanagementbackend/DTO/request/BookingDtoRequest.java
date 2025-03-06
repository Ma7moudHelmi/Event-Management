package com.iti.eventmanagementbackend.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingDtoRequest {
    private String contactNo;
    private String email;
    private long eventId;
    private Double totalAmount;
    private String userEmail;
    private Long busId;

}
