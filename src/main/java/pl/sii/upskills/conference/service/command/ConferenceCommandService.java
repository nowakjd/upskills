package pl.sii.upskills.conference.service.command;

import org.springframework.stereotype.Controller;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.mapper.ConferenceMapper;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

@Controller
public class ConferenceCommandService {
    private ConferenceInputValidator conferenceInputValidator;
    private ConferenceMapper conferenceMapper;
    private ConferenceOutputMapper conferenceOutputMapper;
    private ConferenceRepository conferenceRepository;

    public ConferenceOutput createConference(ConferenceInput conferenceInput) {
        conferenceInputValidator.validate(conferenceInput);
        Conference conference = conferenceMapper.apply(conferenceInput);
        conference.setStatus(ConferenceStatus.DRAFT);
        conference = conferenceRepository.save(conference);
        return conferenceOutputMapper.apply(conference);

    }
}
