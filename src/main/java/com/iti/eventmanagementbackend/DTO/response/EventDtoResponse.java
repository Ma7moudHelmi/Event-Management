package com.iti.eventmanagementbackend.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class EventDtoResponse {

    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private Double price;
    private String image;
    private String status;
    private String category;
    private Long userId;
    private String userFullName;
    private String userEmail;
    private Set<SpeakerDtoResponse> speakers = new HashSet<>();
    private Set<ModeratorDtoResponse> moderators = new HashSet<>();
    private Set<TransportationDtoResponse> transportations = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
