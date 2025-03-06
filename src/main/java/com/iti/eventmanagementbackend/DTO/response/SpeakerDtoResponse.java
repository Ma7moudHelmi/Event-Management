package com.iti.eventmanagementbackend.DTO.response;


import lombok.Data;

@Data
public class SpeakerDtoResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String expertise;
    private String image;
}
