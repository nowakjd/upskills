package pl.sii.upskills.speech.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Collections;
import java.util.Set;

public class SpeechSpeakersInput {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private final Set<Long> speakersIds;

    public SpeechSpeakersInput(Set<Long> speakersIds) {
        this.speakersIds = speakersIds;
    }

    public Set<Long> getIds() {
        return Collections.unmodifiableSet(speakersIds);
    }

}
