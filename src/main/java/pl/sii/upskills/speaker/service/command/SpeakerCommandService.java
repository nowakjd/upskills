package pl.sii.upskills.speaker.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.function.Function;

@Service
public class SpeakerCommandService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerInputValidator speakerInputValidator;
    private final Function<SpeakerInput, Speaker> mapper;
    private final Function<Speaker, SpeakerOutput> outputMapper = new SpeakerOutputMapper();

    public SpeakerCommandService(SpeakerRepository speakerRepository,
                                 SpeakerInputValidator speakerInputValidator,
                                 Function<SpeakerInput, Speaker> mapper) {
        this.speakerRepository = speakerRepository;
        this.speakerInputValidator = speakerInputValidator;
        this.mapper = mapper;
    }

    public SpeakerOutput addSpeaker(SpeakerInput speakerInput) {
        speakerInputValidator.validate(speakerInput);
        Speaker speaker = mapper.apply(speakerInput);
        speakerRepository.save(speaker);
        return outputMapper.apply(speaker);
    }
}
