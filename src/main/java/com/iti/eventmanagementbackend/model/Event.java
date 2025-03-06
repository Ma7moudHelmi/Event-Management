package com.iti.eventmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private Double price;
    private String image;
    private String status;



    @ManyToOne
    @JoinColumn(name = "userId")
        private Users users;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "event_speakers",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "speakers_id")
    )
    private Set<Speakers> speakers;

    @ManyToMany
    @JoinTable(
            name="event_moderators",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "moderators_id")
    )
    private Set<Moderators> moderators;


    @ManyToMany
    @JoinTable(
            name="event_bus_transportation",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private Set<BusTransportation> busTransportations;


    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "event")
//    private List<Booking> bookings;
//
//    @OneToMany(mappedBy = "event")
//    private List<Review> reviews;

    @PrePersist
    private void onCreate(){
        this.createdAt= LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}
