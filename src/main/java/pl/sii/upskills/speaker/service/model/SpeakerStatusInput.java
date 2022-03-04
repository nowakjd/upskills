package pl.sii.upskills.speaker.service.model;

import pl.sii.upskills.speaker.persistence.SpeakerStatus;

public class SpeakerStatusInput {

    private final SpeakerStatus status;

    public SpeakerStatusInput(SpeakerStatus status) {
        this.status = status;
    }

    public SpeakerStatus getStatus() {
        return status;
    }
}
