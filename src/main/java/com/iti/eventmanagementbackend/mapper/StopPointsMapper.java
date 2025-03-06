package com.iti.eventmanagementbackend.mapper;

import com.iti.eventmanagementbackend.DTO.response.StopPointsDtoResponse;
import com.iti.eventmanagementbackend.model.StopPoint;
import org.springframework.stereotype.Component;

@Component
public class StopPointsMapper {

    public StopPointsDtoResponse toDto (StopPoint stopPoint) {
        StopPointsDtoResponse stopPointsDtoResponse = new StopPointsDtoResponse();
        stopPointsDtoResponse.setId(stopPoint.getId());
        stopPointsDtoResponse.setLocation(stopPoint.getLocation());
        stopPointsDtoResponse.setArrivalTime(stopPoint.getArrivalTime());
        stopPointsDtoResponse.setBusStopId(stopPoint.getTransportation().getId());
        return stopPointsDtoResponse;
    }
}
