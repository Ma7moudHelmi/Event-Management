package com.iti.eventmanagementbackend.mapper;

import com.iti.eventmanagementbackend.DTO.response.SpeakerDtoResponse;
import com.iti.eventmanagementbackend.model.Speakers;
import org.springframework.stereotype.Component;

@Component
public class SpeakerMapper {
    public SpeakerDtoResponse toDto(Speakers speakers) {
        SpeakerDtoResponse speakerDtoResponse = new SpeakerDtoResponse();
        speakerDtoResponse.setId(speakers.getId());
        speakerDtoResponse.setName(speakers.getName());
        speakerDtoResponse.setEmail(speakers.getEmail());
        speakerDtoResponse.setBio(speakers.getBio());
        speakerDtoResponse.setImage(speakers.getImage());
        speakerDtoResponse.setExpertise(speakers.getExpertise());
        return speakerDtoResponse;
    }
}
