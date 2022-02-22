package pl.sii.upskills.conference.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    public List<ConferenceOutput> findByStatus(ConferenceStatus status) {
        //conferenceInputValidator.validate(conferenceInput);
        return Optional.ofNullable(status)
                .map(conferenceRepository::findByStatus)
                .orElseGet(conferenceRepository::findAll)
                .stream()
                .map(conferenceOutputMapper)
                .toList();
    }
}

