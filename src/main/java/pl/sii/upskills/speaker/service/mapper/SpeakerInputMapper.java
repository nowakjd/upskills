package pl.sii.upskills.speaker.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.model.SpeakerInput;

import java.util.function.BiFunction;

@Component
public class SpeakerInputMapper implements BiFunction<Speaker, SpeakerInput, Speaker> {

    @Override
    public Speaker apply(Speaker speaker, SpeakerInput speakerInput) {
        speaker.setFirstName(speakerInput.getFirstName());
        speaker.setLastName(speakerInput.getLastName());
        speaker.setPhoneNumber(speakerInput.getPhoneNumber());
        speaker.setEmail(speakerInput.getEmail());
        speaker.setBio(speakerInput.getBio());
        speaker.setSpeakerStatus(SpeakerStatus.ACTIVE);
        return speaker;
    }
}
