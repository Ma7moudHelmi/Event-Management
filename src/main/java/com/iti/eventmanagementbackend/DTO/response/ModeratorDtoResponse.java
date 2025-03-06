package com.iti.eventmanagementbackend.DTO.response;


import lombok.Data;

@Data
public class ModeratorDtoResponse {
    private Long id;
    private String name;
    private String email;
    private String image;
}
