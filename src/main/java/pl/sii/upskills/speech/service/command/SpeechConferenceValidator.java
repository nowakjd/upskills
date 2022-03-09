package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speech.persistence.Speech;

public class SpeechConferenceValidator {

    void validate(Conference conference, Speech speech) {

        if (conference == null) {
            throw new ConferenceDraftNotFoundException();
        }

        if (!conference.getStatus().equals(ConferenceStatus.DRAFT)) {
            throw new ConferenceDraftNotFoundException(conference.getId());
        }

        if (speech == null) {
            throw new SpeechNotFoundException();
        }

        if (!speech.getConference().getId().equals(conference.getId())) {
            throw new SpeechRelationException();
        }
    }

}
