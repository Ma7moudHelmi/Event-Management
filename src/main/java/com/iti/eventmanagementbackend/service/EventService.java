package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.DTO.request.EventDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.EventDtoResponse;
import com.iti.eventmanagementbackend.mapper.EventMapper;
import com.iti.eventmanagementbackend.model.*;
import com.iti.eventmanagementbackend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final SpeakersRepository speakersRepository;

    private final CategoryRepository categoryRepository;

    private final EventMapper eventMapper;

    private final BusTransportationRepository busTransportationRepository;

    private final ModeratorsRepository moderatorsRepository;

    private final EntityManager entityManager;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository,
                        SpeakersRepository speakersRepository, CategoryRepository categoryRepository, EventMapper eventMapper,
                        BusTransportationRepository busTransportationRepository
            , ModeratorsRepository moderatorsRepository
            , EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.speakersRepository = speakersRepository;
        this.categoryRepository = categoryRepository;
        this.eventMapper = eventMapper;
        this.busTransportationRepository = busTransportationRepository;
        this.moderatorsRepository = moderatorsRepository;
        this.entityManager = entityManager;

    }

    public ResponseEntity<Event> createEvent(EventDtoRequest eventDtoRequest, Event event) {

        // validate user, Speakers, moderators and buses is exist
        Users user = userRepository.findByEmail(eventDtoRequest.getUserId());

        Set<Long> speakerIds = eventDtoRequest.getSpeakers();
        if (speakerIds != null && !speakerIds.isEmpty()) {
            long existingSpeakerCount = speakersRepository.countByIdIn(speakerIds);
            if (existingSpeakerCount != speakerIds.size()) {
                throw new IllegalArgumentException("Some speakers do not exist in the database");
            }
            List<Speakers> speakers = speakersRepository.findAllById(speakerIds);
            event.setSpeakers(new HashSet<>(speakers));
        }

        Set<Long> moderatorIds = eventDtoRequest.getModerators();
        if (moderatorIds != null && !moderatorIds.isEmpty()) {
            long existingModeratorCount = moderatorsRepository.countByIdIn(moderatorIds);
            if (existingModeratorCount != moderatorIds.size()) {
                throw new IllegalArgumentException("Some moderators do not exist in the database");
            }
            List<Moderators> moderators = moderatorsRepository.findAllById(moderatorIds);
            event.setModerators(new HashSet<>(moderators));
        }

        Set<Long> busTransportationsIds = eventDtoRequest.getTransportations();
        if (busTransportationsIds != null && !busTransportationsIds.isEmpty()) {
            long existingBusTransportations = busTransportationRepository.countByIdIn(busTransportationsIds);
            if (existingBusTransportations != busTransportationsIds.size()) {
                throw new IllegalArgumentException("Some bus transportations do not exist in the database");
            }
            List<BusTransportation> busTransportations = busTransportationRepository.findAllById(busTransportationsIds);
            event.setBusTransportations(new HashSet<>(busTransportations));
        }

        event.setPrice(eventDtoRequest.getPrice());
        event.setLocation(eventDtoRequest.getLocation());
        event.setUsers(user);
        event.setImage(eventDtoRequest.getImage());
        event.setTitle(eventDtoRequest.getTitle());
        event.setStartDate(eventDtoRequest.getStartDate());
        event.setEndDate(eventDtoRequest.getEndDate());
        event.setDescription(eventDtoRequest.getDescription());
        event.setStatus("upcoming");
        Category eventCategory = categoryRepository.findById(eventDtoRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        event.setCategory(eventCategory);
        Event createEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createEvent);
    }

    @Transactional
    public List<EventDtoResponse> getEvents(int page, int limit, String status) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        if (status != null) {
            return eventRepository.findByStatus(status, pageable).stream().map(eventMapper::toDto).toList();
        }
        return eventRepository.findAll(pageable).stream().map(eventMapper::toDto).toList();
    }


    public ResponseEntity<EventDtoResponse> getEventById(Long id) {
        EventDtoResponse eventDtoResponse = new EventDtoResponse();
        Event event = eventRepository.findById(id).orElse(null);
        eventDtoResponse = eventMapper.toDto(event);
        return ResponseEntity.status(HttpStatus.OK).body(eventDtoResponse);
    }

    public EventDtoResponse patchEvent(Long eventId, EventDtoRequest dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        updateIfPresent(dto.getTitle(), event::setTitle);
        updateIfPresent(dto.getDescription(), event::setDescription);
        updateIfPresent(dto.getEndDate(), event::setEndDate);
        updateIfPresent(dto.getLocation(), event::setLocation);
        updateIfPresent(dto.getPrice(), event::setPrice);
        updateIfPresent(dto.getStartDate(), event::setStartDate);
        updateIfPresent(dto.getStatus(), event::setStatus);
        updateIfPresent(dto.getImage(), event::setImage);

        if (dto.getCategoryId() != null) {
            event.setCategory(categoryRepository.existsById(dto.getCategoryId())
                    ? entityManager.getReference(Category.class, dto.getCategoryId())
                    : null);
        }

        if (dto.getSpeakers() != null) {
            Set<Long> speakerIds = dto.getSpeakers();
            if (speakersRepository.countByIdIn(speakerIds) != speakerIds.size()) {
                throw new IllegalArgumentException("Some speakers not found");
            }
            event.setSpeakers(speakerIds.stream()
                    .map(id -> entityManager.getReference(Speakers.class, id))
                    .collect(Collectors.toSet()));
        }

        if (dto.getModerators() != null) {
            Set<Long> moderatorIds = dto.getModerators();
            if (moderatorsRepository.countByIdIn(moderatorIds) != moderatorIds.size()) {
                throw new IllegalArgumentException("Some moderators not found");
            }
            event.setModerators(moderatorIds.stream()
                    .map(id -> entityManager.getReference(Moderators.class, id))
                    .collect(Collectors.toSet()));
        }
        eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // update if value exsit
    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

}

