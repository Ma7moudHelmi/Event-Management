package com.iti.eventmanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String email;
    private String contactNo;
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "userEmail")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate(){
        this.createdAt= LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

}
