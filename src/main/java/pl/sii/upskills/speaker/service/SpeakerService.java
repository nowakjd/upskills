package pl.sii.upskills.speaker.service;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

@Service
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;
    private final Mapper mapper;

    public SpeakerService(SpeakerRepository speakerRepository, SpeakerInputValidator speakerInputValidator, Mapper mapper) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator =speakerInputValidator;
        this.mapper = mapper;
    }

    public Speaker addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = mapper.apply(speakerInput);
        return speakerRepository.save(speaker);
    }
}
