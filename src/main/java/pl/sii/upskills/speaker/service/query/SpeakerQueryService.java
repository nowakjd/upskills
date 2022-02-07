package pl.sii.upskills.speaker.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

import java.util.List;
import java.util.function.Function;

@Service
public class SpeakerQueryService {
    private final Function<Speaker, SpeakerOutput> speakerOutputMapper;
    private final SpeakerRepository speakerRepository;

    public SpeakerQueryService(Function<Speaker, SpeakerOutput> speakerOutputMapper, SpeakerRepository speakerRepository) {
        this.speakerOutputMapper = speakerOutputMapper;
        this.speakerRepository = speakerRepository;
    }

    public List<SpeakerOutput> findAll() {
        return speakerRepository
                .findAll()
                .stream()
                .map(speakerOutputMapper)
                .toList();
    }

}
