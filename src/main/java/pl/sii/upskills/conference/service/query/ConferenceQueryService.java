package pl.sii.upskills.conference.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceBadRequestException;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class ConferenceQueryService {

    private final Function<Conference, ConferenceOutput> conferenceOutputMapper;
    private final ConferenceRepository conferenceRepository;

    public ConferenceQueryService(Function<Conference, ConferenceOutput> conferenceOutputMapper,
                                  ConferenceRepository conferenceRepository) {
        this.conferenceOutputMapper = conferenceOutputMapper;
        this.conferenceRepository = conferenceRepository;
    }

    public List<ConferenceOutput> findByStatus(String status) {
        return getConferenceStatus(status)
                .map(conferenceRepository::findByStatus)
                .orElseGet(conferenceRepository::findAll)
                .stream()
                .map(conferenceOutputMapper)
                .toList();
    }

    public Conference findDraft(UUID id) {
        return  conferenceRepository
                .findById(id)
                .filter(con -> con.getStatus().equals(ConferenceStatus.DRAFT))
                .orElseThrow(() -> new ConferenceDraftNotFoundException(id));
    }

    private Optional<ConferenceStatus> getConferenceStatus(String status) {
        try {
            return Optional.ofNullable(status)
                    .map(ConferenceStatus::valueOf);
        } catch (IllegalArgumentException e) {
            throw new ConferenceBadRequestException("You have provided wrong status!"
                    + "Please use one of the following statuses : " + Arrays.toString(ConferenceStatus.values()));
        }
    }
}

