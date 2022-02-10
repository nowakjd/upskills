package pl.sii.upskills.speaker.service;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

import java.util.function.Function;

@Service
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;
    private final Function<SpeakerInput, Speaker> mapper;

    public SpeakerService(SpeakerRepository speakerRepository,
                          SpeakerInputValidator speakerInputValidator,
                          Function<SpeakerInput, Speaker> mapper) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator = speakerInputValidator;
        this.mapper = mapper;
    }

    public Speaker addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = mapper.apply(speakerInput);
        return speakerRepository.save(speaker);
    }
}
