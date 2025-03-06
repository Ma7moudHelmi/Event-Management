package com.iti.eventmanagementbackend.DTO.response;


import lombok.Data;

import java.util.List;

@Data
public class TransportationDtoResponse {
    private Long transportationId;
    private String busNumber;
    private List<StopPointsDtoResponse> stopPoints;
}
