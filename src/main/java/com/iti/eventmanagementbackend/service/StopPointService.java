package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.DTO.request.StopPointsDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.StopPointsDtoResponse;
import com.iti.eventmanagementbackend.mapper.StopPointsMapper;
import com.iti.eventmanagementbackend.model.BusTransportation;
import com.iti.eventmanagementbackend.model.StopPoint;
import com.iti.eventmanagementbackend.repository.BusTransportationRepository;
import com.iti.eventmanagementbackend.repository.StopPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StopPointService {
    @Autowired
    private StopPointRepository stopPointRepository;

    @Autowired
    private BusTransportationRepository busTransportationRepository;

    @Autowired
    private StopPointsMapper stopPointsMapper;

    public StopPointsDtoResponse createStopPoint(StopPointsDtoRequest stopPointsDtoRequest) {
        if (stopPointsDtoRequest.getLocation() == null || stopPointsDtoRequest.getLocation().isEmpty()) throw new IllegalArgumentException("Location cannot be empty");
        BusTransportation busTransportation = busTransportationRepository.findById(stopPointsDtoRequest.getTransportationId()).orElseThrow(IllegalArgumentException::new);
        StopPoint stopPoint = new StopPoint();
        stopPoint.setStopName(stopPointsDtoRequest.getStopName());
        stopPoint.setLocation(stopPointsDtoRequest.getLocation());
        stopPoint.setTransportation(busTransportation);
        stopPoint.setArrivalTime(stopPointsDtoRequest.getArrivalTime());
        stopPointRepository.save(stopPoint);

        return new StopPointsDtoResponse(stopPoint.getId(),stopPoint.getLocation(),stopPoint.getArrivalTime(),stopPointsDtoRequest.getTransportationId());
    }

    public List<StopPointsDtoResponse> getStopsByBus(Long busId) {
        busTransportationRepository.findById(busId).orElseThrow(IllegalArgumentException::new);
        return stopPointRepository.findAll().stream().map(stopPointsMapper::toDto).filter(s -> s.getBusStopId().equals(busId)).collect(Collectors.toList());
//        return stopPointRepository.findAll().stream().filter(s -> s.getTransportation().getId().equals(busId)).collect(Collectors.toList());
    }
}
