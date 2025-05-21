package com.iti.eventmanagementbackend.controller;


import com.iti.eventmanagementbackend.DTO.request.PaymentDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.PaymentDetailsDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.PaymentDtoResponse;
import com.iti.eventmanagementbackend.mapper.PaymentDetailsMapper;
import com.iti.eventmanagementbackend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    @Value("#{environment.getProperty('stripe.api.key')}")
    private String stripeApiKey;

    public PaymentController(PaymentRepository paymentRepository, PaymentDetailsMapper paymentDetailsMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentDtoRequest request) {
        Stripe.apiKey = stripeApiKey;
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.getAmount() *100L) // Amount in cents
                    .setCurrency("EGP")
                    .putMetadata("eventId", request.getEventId())
                    .putMetadata("email", request.getEmail())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return ResponseEntity.ok(new PaymentDtoResponse(intent.getId(), intent.getClientSecret(),intent.getAmount(),intent.getCurrency()));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/payment-details/{payment_intent}")
    public ResponseEntity<PaymentDetailsDtoResponse> getPaymentDetails(@PathVariable("payment_intent") String paymentIntent) {
        return ResponseEntity.ok(paymentDetailsMapper.toDto(paymentRepository.findByPaymentId(paymentIntent)));
    }
}

