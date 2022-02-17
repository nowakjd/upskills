package pl.sii.upskills.conference.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.service.mapper.ConferenceMapper;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.util.Optional;

@Service
public class ConferenceCommandService {
    private final ConferenceInputValidator conferenceInputValidator;
    private final ConferenceMapper conferenceMapper;
    private final ConferenceOutputMapper conferenceOutputMapper;
    private final ConferenceRepository conferenceRepository;

    public ConferenceCommandService(ConferenceInputValidator conferenceInputValidator,
                                    ConferenceMapper conferenceMapper, ConferenceOutputMapper conferenceOutputMapper,
                                    ConferenceRepository conferenceRepository) {
        this.conferenceInputValidator = conferenceInputValidator;
        this.conferenceMapper = conferenceMapper;
        this.conferenceOutputMapper = conferenceOutputMapper;
        this.conferenceRepository = conferenceRepository;
    }

    public ConferenceOutput createConference(ConferenceInput conferenceInput) {
        conferenceInputValidator.validate(conferenceInput);
        return Optional.of(conferenceInput)
                .map(conferenceMapper)
                .map(conferenceRepository::save)
                .map(conferenceOutputMapper)
                .orElseThrow();
    }
}
