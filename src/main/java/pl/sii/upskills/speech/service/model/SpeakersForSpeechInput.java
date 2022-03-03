package pl.sii.upskills.speech.service.model;

import java.util.Set;

public class SpeakersForSpeechInput {

    private final Set<Long>  speakersIds;

    public SpeakersForSpeechInput(Set<Long> speakersIds) {
        this.speakersIds = speakersIds;
    }

    public Set<Long> getIds() {
        return speakersIds;
    }
}
