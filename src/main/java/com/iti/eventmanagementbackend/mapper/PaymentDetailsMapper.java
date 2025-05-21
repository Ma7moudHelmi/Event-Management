package com.iti.eventmanagementbackend.mapper;


import com.iti.eventmanagementbackend.DTO.response.PaymentDetailsDtoResponse;
import com.iti.eventmanagementbackend.model.Payments;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsMapper {

    public PaymentDetailsDtoResponse toDto(Payments payment) {
        PaymentDetailsDtoResponse dto = new PaymentDetailsDtoResponse();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setFullName(payment.getUserId().getFirstName() + " " + payment.getUserId().getLastName());
        dto.setEmail(payment.getUserId().getEmail());
        dto.setBookingId(payment.getBookingId().getId());
        return dto;
    }
}
