package pl.sii.upskills.speech.service.mapper;

import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.util.function.Function;

public class SpeechOutputMapper implements Function<Speech, SpeechOutput> {

    @Override
    public SpeechOutput apply(Speech speech) {
        return new SpeechOutput(speech.getTitle(),
                speech.getTimeSlotVO(),
                speech.getSpeakerSet(),
                speech.getId());
    }
}
