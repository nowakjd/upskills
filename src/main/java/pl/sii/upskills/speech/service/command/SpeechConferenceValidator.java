package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.conference.persistence.Conference;

import java.util.UUID;

public class SpeechConferenceValidator {

    void validate(Conference conference, UUID conferenceId) {

        if (!conferenceId.equals(conference.getId())) {
            throw new SpeechRelationException();
        }
    }

}
