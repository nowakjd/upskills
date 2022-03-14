package pl.sii.upskills.speech.service.command;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.util.Collections;
import java.util.List;

class SpeakerSetValidator {
    private final Speech speech;
    private final SpeechSpeakersInput input;

    private static final String SPEAKER_WITH = "Speaker with id ";

    SpeakerSetValidator(Speech speech, SpeechSpeakersInput input) {
        this.speech = speech;
        this.input = input;
    }

    List<String> validate() {

        if (input.getIds().isEmpty()) {
            return Collections.singletonList("Please select speakers to add them to speech");
        }

        List<Long> list = speech.getSpeakerSet().stream()
                .map(Speaker::getId)
                .toList();

        return (input.getIds().stream()
                .filter(s -> !list.contains(s))
                .map(s -> (SPEAKER_WITH + s + " doesn't exist"))
                .toList());
    }

}
