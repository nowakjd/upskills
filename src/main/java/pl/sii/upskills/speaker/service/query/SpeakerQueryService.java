package pl.sii.upskills.speaker.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.List;
import java.util.Set;
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

    public List<SpeakerOutput> findSpeakersByStatus(SpeakerStatus speakerStatus) {
        return speakerRepository
                .findBySpeakerStatus(speakerStatus)
                .stream()
                .map(speakerOutputMapper)
                .toList();
    }

    public Set<Speaker> getSpeakersByIds(Set<Long> ids) {
        return speakerRepository.findByIdIn(ids);
    }

}
