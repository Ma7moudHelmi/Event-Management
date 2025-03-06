package com.iti.eventmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Data
public class Speakers {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String expertise;
    private String image;

//    @ManyToMany(mappedBy = "speakers")
//    private List<Event> events;
}
