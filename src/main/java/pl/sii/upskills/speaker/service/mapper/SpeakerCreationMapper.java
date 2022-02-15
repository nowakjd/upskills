package pl.sii.upskills.speaker.service.mapper;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerInput;

import java.util.function.Function;

public class SpeakerCreationMapper implements Function<SpeakerInput, Speaker> {
    @Override
    public Speaker apply(SpeakerInput input) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(input.getFirstName());
        speaker.setLastName(input.getLastName());
        speaker.setEmail(input.getEmail());
        speaker.setPhoneNumber(input.getPhoneNumber());
        speaker.setBio(input.getBio());
        return speaker;
    }
}
