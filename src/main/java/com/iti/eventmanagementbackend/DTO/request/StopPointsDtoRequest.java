package com.iti.eventmanagementbackend.DTO.request;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StopPointsDtoRequest {
    private String location;
    private LocalDateTime arrivalTime;
    private String stopName;
    private Long transportationId;
}
