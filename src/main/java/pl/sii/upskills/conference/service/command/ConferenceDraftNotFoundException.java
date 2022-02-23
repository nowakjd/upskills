package pl.sii.upskills.conference.service.command;

import java.util.UUID;

public class ConferenceDraftNotFoundException extends RuntimeException {
    public ConferenceDraftNotFoundException(UUID id) {
        super("Conference draft with id= " + id + " not found.");
    }
}
