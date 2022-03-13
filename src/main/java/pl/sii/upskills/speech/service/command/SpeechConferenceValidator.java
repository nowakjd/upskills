package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.speech.persistence.Speech;

public class SpeechConferenceValidator {

    void validate(Conference conference, Speech speech) {

        if (!speech.getConference().getId().equals(conference.getId())) {
            throw new SpeechRelationException();
        }
    }

}
