package pl.sii.upskills.conference.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConferenceValidationException extends RuntimeException{
    private final List<String> errorsConference = new ArrayList<>();

    void addError(String message) {
        errorsConference.add(message);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errorsConference);
    }
}
