package pl.sii.upskills.speech.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeakerSetValidationException extends RuntimeException {

    private final List<String> speakerSetErrors = new ArrayList<>();

    void addError(String message) {
        speakerSetErrors.add(message);
    }

    void addAll(List<String> errorsList) {
        speakerSetErrors.addAll(errorsList);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(speakerSetErrors);
    }
}
