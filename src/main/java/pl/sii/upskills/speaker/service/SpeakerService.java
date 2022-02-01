package pl.sii.upskills.speaker.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

@Service
public class SpeakerService {
    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public Speaker addSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }
}
