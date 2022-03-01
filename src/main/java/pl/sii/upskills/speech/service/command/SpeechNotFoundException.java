package pl.sii.upskills.speech.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeechNotFoundException extends RuntimeException {
    private final List<String> errors = new ArrayList<>();

    public SpeechNotFoundException(Long speechId) {
        errors.add(String.format("Speaker with id = %s was not found", speechId));
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
