package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.DTO.request.EventDtoRequest;
import com.iti.eventmanagementbackend.model.Category;
import com.iti.eventmanagementbackend.model.Event;
import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.repository.CategoryRepository;
import com.iti.eventmanagementbackend.repository.EventRepository;
import com.iti.eventmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Event> createEvent(EventDtoRequest eventDtoRequest, Event event) {
        Users user = userRepository.findByEmail(eventDtoRequest.getUserId());
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
    public List<Event> getEvents(int page, int limit, String status) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        if (status != null) {
            return eventRepository.findByStatus(status, pageable);
        }
        return eventRepository.findAll(pageable).getContent();
    }
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public ResponseEntity<Event> getEventById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setLocation(eventDetails.getLocation());
        event.setPrice(eventDetails.getPrice());
        event.setImage(eventDetails.getImage());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

