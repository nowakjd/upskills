package pl.sii.upskills.service;

import org.springframework.stereotype.Service;
import pl.sii.upskills.model.Speaker;
import pl.sii.upskills.repository.SpeakerRepository;

@Service
public class SpeakerService {
    SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public Speaker addSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }
}
