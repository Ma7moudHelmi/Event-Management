package com.iti.eventmanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;

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
    // Getters and Setters
//    Users user  = userRepository.findByEmail(eventDtoRequest.getUserId());
    //        event.setPrice(eventDtoRequest.getPrice());
//        event.setLocation(eventDtoRequest.getLocation());
//        event.setId(user.getId());
//        event.setTitle(eventDtoRequest.getTitle());
//        event.setStartDate(eventDtoRequest.getStartDate());
//        event.setEndDate(eventDtoRequest.getEndDate());
//        event.setDescription(eventDtoRequest.getDescription());
//        event.setStatus("upcoming");
//
//        Category eventCategory = categoryRepository.findById(eventDtoRequest.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        event.setCategory(eventCategory);
//    Event createEvent = eventRepository.save(event);
//        return  ResponseEntity.status(HttpStatus.CREATED).body(createEvent);
}
