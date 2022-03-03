package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;
import pl.sii.upskills.speech.service.model.SpeakersForSpeechInput;

import java.util.Optional;
import java.util.Set;

@Component
public class SpeakerSetValidator {

    private SpeakerQueryService speakerQueryService;


    boolean validate(SpeakersForSpeechInput speakersForSpeechInput, Conference conference) {

        SpeakerSetValidationException speakerSetValidationException = new SpeakerSetValidationException();
        Set<Speaker> speakerSet = speakerQueryService.getSpeakersIds(speakersForSpeechInput.getIds());

        speakerSet.forEach(s -> showInactives(s).ifPresent(speakerSetValidationException::addError));

        // TODO - is available

        if (!speakerSetValidationException.getErrors().isEmpty()) {
            throw speakerSetValidationException;
        }
        return true;
    }

    private Optional<String> showInactives(Speaker speaker) {
        if (!speaker.isActive()) {
            return Optional.of("Speaker with id " + speaker.getId() + " is not active");
        }
        return Optional.empty();
    }
}
