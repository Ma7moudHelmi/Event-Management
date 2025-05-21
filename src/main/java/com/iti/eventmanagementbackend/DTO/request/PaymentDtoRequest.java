package com.iti.eventmanagementbackend.DTO.request;

import lombok.Data;

@Data
public class PaymentDtoRequest {
    Long amount;
    String eventId;
    String email;
}
