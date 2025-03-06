package com.iti.eventmanagementbackend.DTO.request;


import com.iti.eventmanagementbackend.model.StopPoint;
import lombok.Data;

import java.util.List;

@Data
public class BusTransportationDtoRequest {
    private String busNumber;
    private Long eventId;
    private List<StopPoint> stopPoints;

}
