package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.service.RegistrationService;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/webhook")
public class StripeWebhookController {

    @Autowired
    private RegistrationService registrationService;

    @Value("#{environment.getProperty('stripe.webhook.secret')}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            System.out.println("Payment Intent succeeded");

            EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
            Optional<StripeObject> optionalObject = deserializer.getObject();

            if (optionalObject.isPresent() && optionalObject.get() instanceof PaymentIntent) {
                PaymentIntent intent = (PaymentIntent) optionalObject.get();

                String eventId = intent.getMetadata().get("eventId");
                String userId = intent.getMetadata().get("email");

                if (eventId != null && userId != null) {
                    registrationService.markRegistrationAsPaid(intent);
                } else {
                    System.err.println("Missing metadata: eventId or userId");
                }
            } else {
                System.err.println("Unable to deserialize PaymentIntent object");
            }
        }

        return ResponseEntity.ok().build();
    }
}
