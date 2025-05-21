package com.iti.eventmanagementbackend.DTO.response;

import com.iti.eventmanagementbackend.model.Users;
import lombok.Data;

@Data
public class PaymentDetailsDtoResponse {
    private String paymentId;
    private String email;
    private String currency;
    private Long amount;
    private String fullName;
    private Long bookingId;
}
