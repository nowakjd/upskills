package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;

import java.util.Collections;
import java.util.List;

class SingleSpeakerValidator {

    private static final String SPEAKER_WITH = "Speaker with id ";

    private final Speaker speaker;
    private final Speech speech;

    SingleSpeakerValidator(Speaker speaker, Speech speech) {
        this.speaker = speaker;
        this.speech = speech;
    }

    List<String> validate() {
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

}
