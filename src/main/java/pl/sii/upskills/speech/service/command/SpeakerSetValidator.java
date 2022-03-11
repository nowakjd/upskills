package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class SpeakerSetValidator {

    void validate(Set<Speaker> speakerSet, Speech speech) {

        SpeakerSetValidationException speakerSetValidationException = new SpeakerSetValidationException();

        speakerSet.stream()
                .map(s -> isAvailable(s, speech))
                .forEach(speakerSetValidationException::addAll);

        if (!speakerSetValidationException.getErrors().isEmpty()) {
            throw speakerSetValidationException;
        }
    }

    private List<String> isAvailable(Speaker speaker, Speech speech) {
        if (!speaker.isActive()) {
            return Collections.singletonList("Speaker with id " + speaker.getId() + " is inactive");
        }
        return getListOfSpeechesToCheck(speech)
                .stream()
                .filter(s -> s.hasSpeaker(speaker))
                .map(s -> "Speaker with id " + speaker.getId() + " is already assigned to speech with id "
                        + s.getId())
                .toList();
    }

    private List<Speech> getListOfSpeechesToCheck(Speech speech) {
        return (speech.getConference().getListOfSpeeches()).stream()
                .filter((s -> !s.equals(speech)))
                .filter(s -> !s.getTimeSlotVO().doesntCollide(speech.getTimeSlotVO()))
                .toList();
    }
}
