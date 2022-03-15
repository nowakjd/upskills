package pl.sii.upskills.speaker.service.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SpeakerStatusInput {

    private final String status;

    @JsonCreator
    public SpeakerStatusInput(String status) {

        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
