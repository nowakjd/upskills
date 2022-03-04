package pl.sii.upskills.speaker.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerStatusInput;
import java.util.function.BiFunction;

@Component
public class SpeakerStatusInputMapper implements BiFunction<Speaker, SpeakerStatusInput, Speaker> {

    @Override
    public Speaker apply(Speaker speaker, SpeakerStatusInput speakerStatusInput) {
        speaker.setSpeakerStatus(speakerStatusInput.getStatus());
        return speaker;
    }
}
