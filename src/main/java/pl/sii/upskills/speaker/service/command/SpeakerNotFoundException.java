package pl.sii.upskills.speaker.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeakerNotFoundException extends RuntimeException {
    private final List<String> errors = new ArrayList<>();

    public SpeakerNotFoundException(Long speakerId) {
        errors.add(String.format("Speaker with id = %s was not found", speakerId));
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
