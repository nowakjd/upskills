package pl.sii.upskills.speaker.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import javax.transaction.Transactional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Transactional
@Service
public class SpeakerCommandService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;
    private final Function<Speaker, SpeakerOutput> speakerOutputMapper;
    private final BiFunction<Speaker, SpeakerInput, Speaker> speakerInputMapper;
    private final SpeakerStatusInputValidator speakerStatusInputValidator;

    public SpeakerCommandService(SpeakerRepository speakerRepository, SpeakerInputValidator speakerInputValidator,
                                 Function<Speaker, SpeakerOutput> speakerOutputMapper,
                                 BiFunction<Speaker, SpeakerInput, Speaker> speakerInputMapper,
                                 SpeakerStatusInputValidator speakerStatusInputValidator) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator = speakerInputValidator;
        this.speakerOutputMapper = speakerOutputMapper;
        this.speakerInputMapper = speakerInputMapper;
        this.speakerStatusInputValidator = speakerStatusInputValidator;
    }

    public SpeakerOutput addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = speakerInputMapper.apply(new Speaker(), speakerInput);
        speaker.setSpeakerStatus(SpeakerStatus.ACTIVE);
        speakerRepository.save(speaker);
        return speakerOutputMapper.apply(speaker);
    }

    public SpeakerOutput updateSpeaker(Long id, SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        return speakerRepository
                .findById(id)
                .map(s -> speakerInputMapper.apply(s, speakerInput))
                .map(speakerRepository::save)
                .map(speakerOutputMapper)
                .orElseThrow(() -> new SpeakerNotFoundException(id));
    }

    public SpeakerOutput changeStatus(Long id, SpeakerStatus speakerStatus) {
        speakerStatusInputValidator.validateStatus(speakerStatus);
        return speakerRepository
                .findById(id)
                .map(s -> applyStatus(s, speakerStatus))
                .map(speakerRepository::save)
                .map(speakerOutputMapper)
                .orElseThrow(() -> new SpeakerNotFoundException(id));
    }

    private Speaker applyStatus(Speaker speaker, SpeakerStatus speakerStatus) {
        speaker.setSpeakerStatus(speakerStatus);
        return  speaker;
    }
}
