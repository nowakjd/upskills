package pl.sii.upskills.speech.service.model;

import java.util.Collections;
import java.util.Set;

public class SpeechSpeakersInput {

    private final Set<Long> speakersIds;

    public SpeechSpeakersInput(Set<Long> speakersIds) {
        this.speakersIds = speakersIds;
    }

    public Set<Long> getIds() {
        return Collections.unmodifiableSet(speakersIds);
    }
}
