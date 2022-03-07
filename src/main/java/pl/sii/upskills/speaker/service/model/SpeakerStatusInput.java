package pl.sii.upskills.speaker.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;

public class SpeakerStatusInput {

    private final SpeakerStatus status;

    @JsonCreator
    public SpeakerStatusInput(SpeakerStatus status) {
        this.status = status;
    }

    public SpeakerStatus getStatus() {
        return status;
    }
}
