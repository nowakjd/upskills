package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Service
public class SpeechCommandService {
    private final SpeechRepository speechRepository;
    private final ConferenceRepository conferenceRepository;
    private final SpeechInputValidator speechInputValidator;
    private final SpeechOutputMapper speechOutputMapper;
    private final SpeechMapper speechMapper = new SpeechMapper();

    public SpeechCommandService(SpeechRepository speechRepository, ConferenceRepository conferenceRepository,
                                SpeechInputValidator speechInputValidator, SpeechOutputMapper speechOutputMapper) {
        this.speechRepository = speechRepository;
        this.conferenceRepository = conferenceRepository;
        this.speechInputValidator = speechInputValidator;
        this.speechOutputMapper = speechOutputMapper;
    }

    public SpeechOutput createSpeech(UUID conferenceId, SpeechInput speechInput) {
        Conference conference = conferenceRepository
                .findById(conferenceId)
                .filter(c -> c.getStatus().equals(ConferenceStatus.DRAFT))
                .orElseThrow(() -> new ConferenceDraftNotFoundException(conferenceId));
        speechInputValidator.validate(speechInput, conference);
        Speech speech = speechMapper.apply(new Speech(), speechInput);
        speech.setConference(conference);
        speechRepository.save(speech);
        return speechOutputMapper.apply(speech);
    }

    public SpeechOutput addSpeakers(UUID conferenceId, Long id, SpeechInput speechInput) {
        Conference conference = conferenceRepository
                .findById(conferenceId)
                .filter(c -> c.getStatus().equals(ConferenceStatus.DRAFT))
                .orElseThrow(() -> new ConferenceDraftNotFoundException(conferenceId));
        speechInputValidator.validate(speechInput, conference);
        return speechRepository.findById(id)
                .map(s -> {
                    s.setSpeakerSet(speechInput.getSpeakerSet());
                    return  s; })
                .map(speechOutputMapper)
                .orElseThrow(() -> new SpeechNotFoundException(id));
    }
}
