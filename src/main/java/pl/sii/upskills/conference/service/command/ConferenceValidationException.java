package pl.sii.upskills.conference.service.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConferenceValidationException extends RuntimeException {
    private final List<String> errorsConference = new ArrayList<>();

    void addErrors(Collection<String> strings) {
        errorsConference.addAll(strings);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errorsConference);
    }

    public boolean hasErrors() {
        return !errorsConference.isEmpty();
    }
}
