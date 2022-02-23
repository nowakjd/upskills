package pl.sii.upskills.conference.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.mapper.ConferenceMapper;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    public ConferenceOutput createConference(ConferenceInput conferenceInput) {
        conferenceInputValidator.validate(conferenceInput);
        return Optional.of(conferenceInput)
                .map(s -> conferenceMapper.apply(new Conference(), s))
                .map(conferenceRepository::save)
                .map(conferenceOutputMapper)
                .orElseThrow();
    }

    @Transactional
    public ConferenceOutput updateConference(UUID id, ConferenceInput conferenceInput) {
        conferenceInputValidator.validate(conferenceInput);
        return conferenceRepository
                .findById(id)
                .filter(conference -> conference.getStatus().equals(ConferenceStatus.DRAFT))
                .map(conference -> conferenceMapper.apply(conference, conferenceInput))
                .map(conferenceRepository::save)
                .map(conferenceOutputMapper)
                .orElseThrow(() -> new ConferenceDraftNotFoundException(id));
    }
}
