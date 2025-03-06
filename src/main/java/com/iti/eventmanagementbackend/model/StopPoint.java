package com.iti.eventmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class StopPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private LocalDateTime arrivalTime;
    private String stopName;

    @ManyToOne
    @JsonIgnoreProperties("stopPoints")
    private BusTransportation transportation;
}
