package pl.sii.upskills.speaker.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.command.SpeakerBadRequestException;
import pl.sii.upskills.speaker.service.command.SpeakerNotFoundException;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.*;
import java.util.function.Function;

@Service
public class SpeakerQueryService {
    private final Function<Speaker, SpeakerOutput> speakerOutputMapper;
    private final SpeakerRepository speakerRepository;

    public SpeakerQueryService(Function<Speaker, SpeakerOutput> speakerOutputMapper,
                               SpeakerRepository speakerRepository) {
        this.speakerOutputMapper = speakerOutputMapper;
        this.speakerRepository = speakerRepository;
    }

    public List<SpeakerOutput> findAllSpeakers() {
        return speakerRepository
                .findAll()
                .stream()
                .map(speakerOutputMapper)
                .toList();
    }

    public List<SpeakerOutput> findSpeakers(String speakerStatus) {
        if (speakerStatus == null) {
            return findAllSpeakers();
        } else {
            return speakerRepository
                    .findBySpeakerStatus(mapToEnum(speakerStatus))
                    .stream()
                    .map(speakerOutputMapper)
                    .toList();
        }
    }

    private SpeakerStatus mapToEnum(String speakerStatus) {
        try {
            return SpeakerStatus.valueOf(speakerStatus);
        } catch (IllegalArgumentException e) {
            throw new SpeakerBadRequestException("You have provided wrong status!"
                    + "Please use one of the following statuses : " + Arrays.toString(SpeakerStatus.values()));
        }
    }

    public Set<Speaker> getSpeakersByIds(Set<Long> ids) {
        return speakerRepository.findByIdIn(ids);
    }

    public Speaker getSpeakerById(Long id) {
        return speakerRepository
                .findById(id)
                .orElseThrow(() -> new SpeakerNotFoundException(id));
    }
}
