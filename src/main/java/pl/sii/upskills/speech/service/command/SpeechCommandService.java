package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Service;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class SpeechCommandService {
    private final SpeechRepository speechRepository;
    private final ConferenceRepository conferenceRepository;
    private final SpeechInputValidator speechInputValidator;
    private final SpeechOutputMapper speechOutputMapper;
    private final SpeakerQueryService speakerQueryService;
    private final SpeechMapper speechMapper = new SpeechMapper();
    private final SpeechConferenceValidator speechConferenceValidator = new SpeechConferenceValidator();

    public SpeechCommandService(SpeechRepository speechRepository, ConferenceRepository conferenceRepository,
                                SpeechInputValidator speechInputValidator, SpeechOutputMapper speechOutputMapper,
                                SpeakerQueryService speakerQueryService) {
        this.speechRepository = speechRepository;
        this.conferenceRepository = conferenceRepository;
        this.speechInputValidator = speechInputValidator;
        this.speechOutputMapper = speechOutputMapper;
        this.speakerQueryService = speakerQueryService;
    }

    @Transactional
    public SpeechOutput createSpeech(UUID conferenceId, SpeechInput speechInput) {
        Conference conference = getConference(conferenceId);
        speechInputValidator.validate(speechInput, conference);
        Speech speech = speechMapper.apply(new Speech(), speechInput);
        speech.setConference(conference);
        speechRepository.save(speech);
        return speechOutputMapper.apply(speech);
    }

    @Transactional
    public SpeechOutput addSpeakers(UUID conferenceId, Long id, SpeechSpeakersInput speechSpeakersInput) {
        Speech speech = getSpeech(id);
        Conference conference = getConference(conferenceId);
        speechConferenceValidator.validate(conference, speech);
        Set<Speaker> speakerSet = speakerQueryService.getSpeakersByIds(speechSpeakersInput.getIds());
        speech.setSpeakerSet(speakerSet);
        SpeakerSetValidator speakerSetValidator = new SpeakerSetValidator(speech, speechSpeakersInput);
        speakerSetValidator.validate();
        return speechOutputMapper.apply(speech);
    }

    private Conference getConference(UUID conferenceId) {
        return conferenceRepository
                .findById(conferenceId)
                .filter(c -> c.getStatus().equals(ConferenceStatus.DRAFT))
                .orElseThrow(() -> new ConferenceDraftNotFoundException(conferenceId));
    }

    private Speech getSpeech(Long id) {
        return speechRepository
                .findById(id)
                .orElseThrow(() -> new SpeechNotFoundException(id));
    }
}
