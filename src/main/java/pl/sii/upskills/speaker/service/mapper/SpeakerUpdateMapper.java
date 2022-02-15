package pl.sii.upskills.speaker.service.mapper;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerInput;

import java.util.function.BiFunction;

public class SpeakerUpdateMapper implements BiFunction<Speaker, SpeakerInput, Speaker> {

    public Speaker apply(Speaker speaker, SpeakerInput speakerInput) {
        speaker.setFirstName(speakerInput.getFirstName());
        speaker.setLastName(speakerInput.getLastName());
        speaker.setPhoneNumber(speakerInput.getPhoneNumber());
        speaker.setEmail(speakerInput.getEmail());
        speaker.setBio(speakerInput.getBio());
        return speaker;
    }
}
