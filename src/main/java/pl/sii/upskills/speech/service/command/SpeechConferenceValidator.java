package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speech.persistence.Speech;

public class SpeechConferenceValidator {

    boolean validate(Conference conference, Speech speech) {

        if (conference == null || !conference.getStatus().equals(ConferenceStatus.DRAFT)) {
            throw new ConferenceDraftNotFoundException(conference.getId());
        }

        if (speech == null) {
            throw new SpeechNotFoundException(speech.getId());
        }

        if (!conference.getListOfSpeeches().contains(speech)) {
            throw new SpeechRelationException();
        }

        return true;
    }
}
