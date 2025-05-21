package com.iti.eventmanagementbackend.service;


import com.iti.eventmanagementbackend.model.Booking;
import com.iti.eventmanagementbackend.model.Payments;
import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.repository.BookingRepository;
import com.iti.eventmanagementbackend.repository.PaymentRepository;
import com.iti.eventmanagementbackend.repository.UserRepository;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final BookingRepository bookingRepository;

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;

    public RegistrationService(BookingRepository bookingRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void markRegistrationAsPaid(PaymentIntent paymentIntent) {
        Users users = userRepository.findByEmail(paymentIntent.getMetadata().get("email"));
        String eventId = paymentIntent.getMetadata().get("eventId");

        Booking booking = bookingRepository
                .findByUsers_IdAndEvent_Id(users.getId(),Long.parseLong(eventId)).orElseThrow(IllegalArgumentException::new);
        booking.setPaid(true);
        System.out.println(booking.getId());
        bookingRepository.save(booking);

        Payments payments = new Payments();

        payments.setAmount(paymentIntent.getAmount());
        payments.setCurrency(paymentIntent.getCurrency());
        payments.setPaymentId(paymentIntent.getId());
        payments.setBookingId(booking);
        payments.setUserId(users);
        paymentRepository.save(payments);
    }
}
