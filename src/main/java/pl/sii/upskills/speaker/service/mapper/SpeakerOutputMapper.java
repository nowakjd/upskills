package pl.sii.upskills.speaker.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.Optional;
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
                speaker.getBio(),
                Optional.ofNullable(speaker.getSpeakerStatus())
                        .map(SpeakerStatus::toString)
                        .orElse("BROKEN"));
    }
}
