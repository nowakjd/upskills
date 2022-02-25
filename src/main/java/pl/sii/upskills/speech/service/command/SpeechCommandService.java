package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Service
public class SpeechCommandService {
    private final ConferenceRepository conferenceRepository;
    private final SpeechInputValidator speechInputValidator;
    private final SpeechMapper speechMapper = new SpeechMapper();
    private final SpeechOutputMapper speechOutputMapper = new SpeechOutputMapper();

    public SpeechCommandService(ConferenceRepository conferenceRepository,
                                SpeechInputValidator speechInputValidator) {
        this.conferenceRepository = conferenceRepository;
        this.speechInputValidator = speechInputValidator;
    }

    public SpeechOutput createSpeech(UUID conferenceId, SpeechInput speechInput) {
        Conference conference = conferenceRepository
                .findById(conferenceId)
                .filter(c -> c.getStatus().equals(ConferenceStatus.DRAFT))
                .orElseThrow(() -> new ConferenceDraftNotFoundException(conferenceId));
        speechInputValidator.validate(speechInput, conference);
        Speech speech = speechMapper.apply(new Speech(), speechInput);
        conference.addSpeech(speech);
        return speechOutputMapper.apply(speech);
    }
}
