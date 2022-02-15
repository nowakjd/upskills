package pl.sii.upskills.speaker.service.mapper;

import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.function.Function;

public class SpeakerOutputMapper implements Function<Speaker, SpeakerOutput> {
    @Override
    public SpeakerOutput apply(Speaker speaker) {
        return new SpeakerOutput(speaker.getId(),
                speaker.getFirstName(),
                speaker.getLastName(),
                speaker.getPhoneNumber(),
                speaker.getEmail(),
                speaker.getBio());
    }
}
