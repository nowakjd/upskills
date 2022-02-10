package pl.sii.upskills.speaker.service.query;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.Speaker;

import java.util.function.Function;

@Component
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
