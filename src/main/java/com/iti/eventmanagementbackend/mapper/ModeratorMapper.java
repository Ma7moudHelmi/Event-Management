package com.iti.eventmanagementbackend.mapper;

import com.iti.eventmanagementbackend.DTO.response.ModeratorDtoResponse;
import com.iti.eventmanagementbackend.model.Moderators;
import org.springframework.stereotype.Component;


@Component
public class ModeratorMapper {
    public ModeratorDtoResponse toDto(Moderators moderators) {
        ModeratorDtoResponse moderatorDtoResponse = new ModeratorDtoResponse();
        moderatorDtoResponse.setId(moderators.getId());
        moderatorDtoResponse.setName(moderators.getName());
        moderatorDtoResponse.setEmail(moderators.getEmail());
        moderatorDtoResponse.setImage(moderators.getImage());
        return moderatorDtoResponse;
    }
}
