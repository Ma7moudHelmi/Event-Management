package com.iti.eventmanagementbackend.controller;

import com.iti.eventmanagementbackend.DTO.request.BusTransportationDtoRequest;
import com.iti.eventmanagementbackend.model.BusTransportation;
import com.iti.eventmanagementbackend.service.BusTransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bus-transportations")
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIM')")
public class BusTransportationController {
    @Autowired
    private BusTransportationService busTransportationService;

    @PostMapping
    public ResponseEntity<?> createBus(@RequestBody BusTransportationDtoRequest bus) {
        BusTransportation savedBus = busTransportationService.createBusTransportation(bus);
        return ResponseEntity.ok(savedBus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(busTransportationService.getBusById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllBus() {
        return ResponseEntity.ok(busTransportationService.getAllBusTransportations());
    }

}
