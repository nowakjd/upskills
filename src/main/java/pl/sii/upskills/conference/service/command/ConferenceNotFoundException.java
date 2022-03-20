package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.conference.persistence.ConferenceStatus;

import java.util.UUID;

public class ConferenceNotFoundException extends RuntimeException {

    public ConferenceNotFoundException(UUID id) {
        super("Conference with id= " + id + " not found.");
    }

    public ConferenceNotFoundException(UUID id, ConferenceStatus status) {
        super("Conference " + status + " with id= " + id + " not found.");
    }
}
