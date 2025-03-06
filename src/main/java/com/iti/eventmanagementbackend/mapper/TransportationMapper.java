package com.iti.eventmanagementbackend.mapper;


import com.iti.eventmanagementbackend.DTO.response.StopPointsDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.TransportationDtoResponse;
import com.iti.eventmanagementbackend.model.BusTransportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
public class TransportationMapper {

    @Autowired
    private StopPointsMapper stopPointsMapper;

    public TransportationDtoResponse toDto(BusTransportation transportation) {
        TransportationDtoResponse transportationDtoResponse = new TransportationDtoResponse();
        transportationDtoResponse.setTransportationId(transportation.getId());
        transportationDtoResponse.setBusNumber(transportation.getBusNumber());
        transportationDtoResponse.setStopPoints(transportation.getStopPoints().stream().map(stopPointsMapper::toDto).collect(Collectors.toList()));
        return transportationDtoResponse;
    }

}
