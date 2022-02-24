package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.query.ConferenceQueryService;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SpeechCommandService {
    private final SpeechRepository speechRepository;
    private final SpeechInputValidator speechInputValidator;
    private final ConferenceQueryService conferenceQueryService;
    private SpeechMapper speechMapper = new SpeechMapper();
    private SpeechOutputMapper speechOutputMapper = new SpeechOutputMapper();

    public SpeechCommandService(SpeechRepository speechRepository, SpeechInputValidator speechInputValidator,
                                ConferenceQueryService conferenceQueryService) {
        this.speechRepository = speechRepository;
        this.speechInputValidator = speechInputValidator;
        this.conferenceQueryService = conferenceQueryService;
    }

    public SpeechOutput createSpeech(UUID conferenceId, SpeechInput speechInput) {
        speechInputValidator.validate(speechInput);
        Conference conference = conferenceQueryService.findDraft(conferenceId);
        return Optional.of(speechInput)
                .map(s -> speechMapper.apply(new Speech(), s))
                .map(speechRepository::save)
                .map(speechOutputMapper)
                .orElseThrow();
    }

}
