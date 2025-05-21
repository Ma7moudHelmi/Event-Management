package com.iti.eventmanagementbackend.repository;

import com.iti.eventmanagementbackend.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payments, Long> {
    Payments findByPaymentId(String paymentId);
}
