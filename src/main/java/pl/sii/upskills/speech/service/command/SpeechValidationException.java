package pl.sii.upskills.speech.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeechValidationException extends RuntimeException {
    private final List<String> speechErrors = new ArrayList<>();

    void addError(String message) {
        speechErrors.add(message);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(speechErrors);
    }

}
