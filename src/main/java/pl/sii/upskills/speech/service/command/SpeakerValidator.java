package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.util.List;
import java.util.Set;

class SpeakerValidator {

    void validateSpeakers(Speech speech, SpeechSpeakersInput input) {

        SpeakerSetValidationException speakerSetValidationException = new SpeakerSetValidationException();
        Set<Speaker> speakerSet = speech.getSpeakerSet();

        SpeakerSetValidator speakerSetValidator = new SpeakerSetValidator(speech, input);

        speakerSetValidationException.addAll(speakerSetValidator.validate());
        speakerSet.stream()
                .map(s -> new SingleSpeakerValidator(s, speech))
                .map(SingleSpeakerValidator::validate)
                .forEach(speakerSetValidationException::addAll);

        if (!speakerSetValidationException.getErrors().isEmpty()) {
            throw speakerSetValidationException;
        }
    }

    void validateSpeaker(Speaker speaker, Speech speech) {
        List<String> violations = new SingleSpeakerValidator(speaker, speech).validate();

        if (!violations.isEmpty()) {
            SpeakerSetValidationException speakerSetValidationException = new SpeakerSetValidationException();
            speakerSetValidationException.addAll(violations);
            throw speakerSetValidationException;
        }
    }
}
