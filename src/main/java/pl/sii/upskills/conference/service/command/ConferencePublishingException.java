package pl.sii.upskills.conference.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConferencePublishingException extends RuntimeException {
    private final List<String> errors = new ArrayList<>();

    public ConferencePublishingException(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
