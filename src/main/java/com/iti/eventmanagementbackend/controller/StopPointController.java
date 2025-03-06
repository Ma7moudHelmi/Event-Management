package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.StopPointsDtoRequest;
import com.iti.eventmanagementbackend.DTO.response.StopPointsDtoResponse;
import com.iti.eventmanagementbackend.model.StopPoint;
import com.iti.eventmanagementbackend.service.StopPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stop-points")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
public class StopPointController {
    @Autowired
    private StopPointService stopPointService;

    @PostMapping
    public ResponseEntity<?> createStop(@RequestBody StopPointsDtoRequest stopPoint) {
        StopPointsDtoResponse savedStop = stopPointService.createStopPoint(stopPoint);
        return ResponseEntity.ok(savedStop);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<?> getStops(@PathVariable Long busId) {
        return ResponseEntity.ok(stopPointService.getStopsByBus(busId));
    }
}
