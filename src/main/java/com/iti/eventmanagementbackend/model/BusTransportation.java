package com.iti.eventmanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class BusTransportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String busNumber;

//    @ManyToMany
//    @JoinColumn(name = "eventId")
//    private Set<Event> event;

    @OneToMany(mappedBy = "transportation" , cascade = CascadeType.ALL)
    private List<StopPoint> stopPoints;
}