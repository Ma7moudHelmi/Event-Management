package com.iti.eventmanagementbackend.mapper;

import com.iti.eventmanagementbackend.DTO.response.EventDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.ModeratorDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.SpeakerDtoResponse;
import com.iti.eventmanagementbackend.DTO.response.TransportationDtoResponse;
import com.iti.eventmanagementbackend.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class EventMapper {

    @Autowired
    private SpeakerMapper speakerMapper;

    @Autowired
    private ModeratorMapper moderatorMapper;

    @Autowired
    private TransportationMapper transportationMapper;

    public EventDtoResponse toDto(Event event) {
        EventDtoResponse eventDtoResponse = new EventDtoResponse();

        try {
            if (!event.getSpeakers().isEmpty()) {
                Set<SpeakerDtoResponse> speakerDTOs = event.getSpeakers()
                        .stream()
                        .map(speakerMapper::toDto)
                        .collect(Collectors.toSet());
                eventDtoResponse.setSpeakers(speakerDTOs);
            } else {
                System.out.println("No speakers found");
                eventDtoResponse.setSpeakers(null);
            }

            if (!event.getModerators().isEmpty()) {
                Set<ModeratorDtoResponse> moderatorDTOs = event.getModerators()
                        .stream()
                        .map(moderatorMapper::toDto)
                        .collect(Collectors.toSet());
                eventDtoResponse.setModerators(moderatorDTOs);
            } else {
                System.out.println("No moderators found");
                eventDtoResponse.setModerators(null);
            }

            if (!event.getBusTransportations().isEmpty()) {
                Set<TransportationDtoResponse> transportationDTOs = event.getBusTransportations()
                        .stream()
                        .map(transportationMapper::toDto)
                        .collect(Collectors.toSet());
                eventDtoResponse.setTransportations(transportationDTOs);
            }else{
                System.out.println("No transportations found");
                eventDtoResponse.setTransportations(null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }

        eventDtoResponse.setId(event.getId());
        eventDtoResponse.setTitle(event.getTitle());
        eventDtoResponse.setDescription(event.getDescription());
        eventDtoResponse.setStartDate(event.getStartDate());
        eventDtoResponse.setEndDate(event.getEndDate());
        eventDtoResponse.setLocation(event.getLocation());
        eventDtoResponse.setImage(event.getImage());
        eventDtoResponse.setPrice(event.getPrice());
        eventDtoResponse.setCategory(event.getCategory().getName());
        eventDtoResponse.setStatus(event.getStatus());
        eventDtoResponse.setCreatedAt(event.getCreatedAt());
        eventDtoResponse.setUpdatedAt(event.getUpdatedAt());
        eventDtoResponse.setUserEmail(event.getUsers().getEmail());
        eventDtoResponse.setUserId(event.getUsers().getId());
        eventDtoResponse.setUserFullName(event.getUsers().getFirstName() + " " + event.getUsers().getLastName());


        return eventDtoResponse;
    }
}
