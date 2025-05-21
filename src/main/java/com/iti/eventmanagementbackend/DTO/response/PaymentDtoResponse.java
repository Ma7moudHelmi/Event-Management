package com.iti.eventmanagementbackend.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDtoResponse {
    String paymentIntentId;
    String clientSecret;
    Long amount;
    String currency;

}
