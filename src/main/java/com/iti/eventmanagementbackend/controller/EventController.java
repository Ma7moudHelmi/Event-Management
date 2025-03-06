package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.EventDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.EventDtoResponse;
import com.iti.eventmanagementbackend.model.Event;
import com.iti.eventmanagementbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
    public ResponseEntity<Event> createEvent(@RequestBody EventDtoRequest eventDtoRequest,Event event) {
        return eventService.createEvent(eventDtoRequest,event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDtoResponse> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
    @GetMapping
    public List<EventDtoResponse> getEvents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String status) {
        return eventService.getEvents(page, limit, status);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM','USER')")
    public ResponseEntity<EventDtoResponse> updateEvent(@PathVariable Long id, @RequestBody EventDtoRequest eventDtoRequest) {
        return ResponseEntity.ok(eventService.patchEvent(id, eventDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}

