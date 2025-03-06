package com.iti.eventmanagementbackend.DTO.request;

import com.iti.eventmanagementbackend.model.BusTransportation;
import com.iti.eventmanagementbackend.model.Moderators;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class EventDtoRequest {

    private Long categoryId;
    private String description;
    private Date endDate;
    private String location;
    private Double price;
    private Date startDate;
    private String title;
    private String status;
    private String userId;
    private String image;
    private Set<Long> speakers;
    private Set<Long> moderators;
    private Set<Long> transportations;


}
