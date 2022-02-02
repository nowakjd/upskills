package pl.sii.upskills.speaker.service;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

@Service
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;

    public SpeakerService(SpeakerRepository speakerRepository, SpeakerInputValidator speakerInputValidator) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator =speakerInputValidator;
    }

    public Speaker addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = Mapper.map(speakerInput);
        return speakerRepository.save(speaker);
    }
}
