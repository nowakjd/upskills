package pl.sii.upskills.speaker.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.service.mapper.SpeakerCreationMapper;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.mapper.SpeakerUpdateMapper;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.function.Function;

@Service
public class SpeakerCommandService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;
    private final Function<SpeakerInput, Speaker> speakerCreationMapper = new SpeakerCreationMapper();
    private final Function<Speaker, SpeakerOutput> speakerOutputMapper = new SpeakerOutputMapper();
    private final SpeakerUpdateMapper speakerUpdateMapper = new SpeakerUpdateMapper();

    public SpeakerCommandService(SpeakerRepository speakerRepository,
                                 SpeakerInputValidator speakerInputValidator) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator = speakerInputValidator;
    }

    public SpeakerOutput addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = speakerCreationMapper.apply(speakerInput);
        speakerRepository.save(speaker);
        return speakerOutputMapper.apply(speaker);
    }

    public SpeakerOutput updateSpeaker(Long id, SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        return speakerRepository
                .findById(id)
                .map(s -> speakerUpdateMapper.apply(s, speakerInput))
                .map(speakerRepository::save)
                .map(speakerOutputMapper)
                .orElseThrow(() -> new SpeakerNotFoundException(id));
    }
}
