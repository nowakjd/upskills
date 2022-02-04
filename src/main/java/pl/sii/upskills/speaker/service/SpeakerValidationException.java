package pl.sii.upskills.speaker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeakerValidationException extends RuntimeException {
    private final List<String> errors = new ArrayList<>();

    void addError(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
