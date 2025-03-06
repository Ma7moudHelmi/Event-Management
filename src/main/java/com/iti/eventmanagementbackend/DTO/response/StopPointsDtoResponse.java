package com.iti.eventmanagementbackend.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopPointsDtoResponse {
    private Long id;
    private String location;
    private LocalDateTime arrivalTime;
    private Long busStopId;

}
