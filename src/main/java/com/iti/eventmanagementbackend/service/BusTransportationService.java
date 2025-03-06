package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.DTO.request.BusTransportationDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.TransportationDtoResponse;
import com.iti.eventmanagementbackend.mapper.StopPointsMapper;
import com.iti.eventmanagementbackend.mapper.TransportationMapper;
import com.iti.eventmanagementbackend.model.BusTransportation;
import com.iti.eventmanagementbackend.model.Event;
import com.iti.eventmanagementbackend.repository.BusTransportationRepository;
import com.iti.eventmanagementbackend.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusTransportationService {
    @Autowired
    private BusTransportationRepository busTransportationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TransportationMapper transportationMapper;

    public BusTransportation createBusTransportation(BusTransportationDtoRequest bus) {
        BusTransportation busTransportation = new BusTransportation();
        busTransportation.setBusNumber(bus.getBusNumber());
        busTransportation.setStopPoints(bus.getStopPoints());
        return busTransportationRepository.save(busTransportation);

    }

    public TransportationDtoResponse getBusById(Long id) {
        BusTransportation busTransportation = busTransportationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        TransportationDtoResponse busTransportationDtoResponse;
        TransportationMapper transportationMapper = new TransportationMapper();
        busTransportationDtoResponse = transportationMapper.toDto(busTransportation);
        return busTransportationDtoResponse;
    }

    public List<TransportationDtoResponse> getAllBusTransportations() {
        return busTransportationRepository.findAll().stream().map(transportationMapper::toDto).toList();

    }

}

