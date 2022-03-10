package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class SpeakerSetValidator {
    private static final String SPEAKER_WITH = "Speaker with id ";

    void validate(Set<Speaker> speakerSet, Speech speech, SpeechSpeakersInput input) {

        SpeakerSetValidationException speakerSetValidationException = new SpeakerSetValidationException();

        speakerSetValidationException.addAll(checkInput(speakerSet, input));
        speakerSet.stream()
                .map(s -> isAvailable(s, speech))
                .forEach(speakerSetValidationException::addAll);

        if (!speakerSetValidationException.getErrors().isEmpty()) {
            throw speakerSetValidationException;
        }
    }

    private List<String> isAvailable(Speaker speaker, Speech speech) {
        if (!speaker.isActive()) {
            return Collections.singletonList(SPEAKER_WITH + speaker.getId() + " is inactive");
        }
        return getListOfSpeechesToCheck(speech)
                    .stream()
                    .filter(s -> s.hasSpeaker(speaker))
                    .map(s -> SPEAKER_WITH + speaker.getId() + " is already assigned to speech with id "
                            + s.getId())
                    .toList();
    }

    private List<Speech> getListOfSpeechesToCheck(Speech speech) {
        return (speech.getConference().getListOfSpeeches()).stream()
                .filter((s -> !s.equals(speech)))
                .filter(s -> !s.getTimeSlotVO().doesntCollide(speech.getTimeSlotVO()))
                .toList();
    }

    private List<String> checkInput(Set<Speaker> speakerSet, SpeechSpeakersInput speechSpeakersInput) {

        if (speechSpeakersInput.getIds().isEmpty()) {
            return Collections.singletonList("Please select speakers to add them to speech");
        }

        List<Long> list = speakerSet.stream()
                .map(Speaker::getId)
                .toList();

        return (speechSpeakersInput.getIds().stream()
                .filter(s -> !list.contains(s))
                .map(s -> (SPEAKER_WITH + s + " doesn't exist"))
                .toList());
    }

}
