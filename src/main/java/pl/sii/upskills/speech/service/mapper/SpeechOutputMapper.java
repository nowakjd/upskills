package pl.sii.upskills.speech.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SpeechOutputMapper implements Function<Speech, SpeechOutput> {

    private final Function<Speaker, SpeakerOutput>  speakerSpeakerOutputMapper;

    public SpeechOutputMapper(Function<Speaker, SpeakerOutput> speakerSpeakerOutputMapper) {
        this.speakerSpeakerOutputMapper = speakerSpeakerOutputMapper;
    }

    @Override
    public SpeechOutput apply(Speech speech) {
        return new SpeechOutput(speech.getTitle(),
                speech.getTimeSlotVO(),
                speech.getId(),
                speech.getSpeakerSet()
                .stream()
                .map(speakerSpeakerOutputMapper)
                .collect(Collectors.toSet()));
    }
}
