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
    private final ConferenceMapper conferenceMapper;
    private final ConferenceOutputMapper conferenceOutputMapper;
    private final ConferenceRepository conferenceRepository;
    private final ConferenceValidator conferenceValidator;

    public ConferenceCommandService(ConferenceMapper conferenceMapper,
                                    ConferenceOutputMapper conferenceOutputMapper,
                                    ConferenceRepository conferenceRepository,
                                    ConferenceValidator conferenceValidator) {
        this.conferenceMapper = conferenceMapper;
        this.conferenceOutputMapper = conferenceOutputMapper;
        this.conferenceRepository = conferenceRepository;
        this.conferenceValidator = conferenceValidator;
    }

    @Transactional
    public ConferenceOutput createConference(ConferenceInput conferenceInput) {
        return Optional.of(conferenceInput)
                .map(s -> conferenceMapper.apply(new Conference(), s))
                .map(conferenceValidator)
                .map(conferenceRepository::save)
                .map(conferenceOutputMapper)
                .orElseThrow();
    }

    @Transactional
    public ConferenceOutput updateConference(UUID id, ConferenceInput conferenceInput) {
        return conferenceRepository
                .findById(id)
                .filter(conference -> conference.getStatus().equals(ConferenceStatus.DRAFT))
                .map(conference -> conferenceMapper.apply(conference, conferenceInput))
                .map(conferenceValidator)
                .map(conferenceRepository::save)
                .map(conferenceOutputMapper)
                .orElseThrow(() -> new ConferenceDraftNotFoundException(id));
    }

    private ConferenceOutput publish(UUID id) {
        return conferenceRepository.findById(id)
                .filter(conference -> conference.getStatus().equals(ConferenceStatus.DRAFT))
                .map(Conference::publish)
                .map(conferenceValidator)
                .map(conferenceOutputMapper)
                .orElseThrow(() -> new ConferenceDraftNotFoundException(id));
    }

    @Transactional
    public ConferenceOutput changeStatus(UUID id, ConferenceStatus status) {
        return switch (status) {
            case PUBLISHED -> publish(id);
            default -> throw
                    new ConferenceBadRequestException("Changing status to " + status + " is not allowed");
        };
    }
}
