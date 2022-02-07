package pl.sii.upskills.speaker.service.query;

import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

import java.util.List;

@Service
public class SpeakerQueryService {
    ToSpeakerOutput toSpeakerOutput;
    SpeakerRepository speakerRepository;

    public SpeakerQueryService(ToSpeakerOutput toSpeakerOutput, SpeakerRepository speakerRepository) {
        this.toSpeakerOutput = toSpeakerOutput;
        this.speakerRepository = speakerRepository;
    }

    public List<SpeakerOutput> findAll(){
        return speakerRepository
                .findAll()
                .stream()
                .map(toSpeakerOutput)
                .toList();
    }

}
