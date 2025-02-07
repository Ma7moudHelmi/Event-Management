package com.iti.eventmanagementbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String role;
    private String gender;
    private String profileImg;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

//    @OneToMany(mappedBy = "users")
//    private List<Event> events;

//    @OneToMany(mappedBy = "users")
//    private List<Booking> bookings;

//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "user")
//    private List<Feedback> feedbacks;
//
//    @OneToMany(mappedBy = "user")
//    private List<BlogPost> blogPosts;
//
//    @OneToMany(mappedBy = "user")
//    private List<FAQ> faqs;
//
//    @OneToMany(mappedBy = "user")
//    private List<Page> pages;
}
