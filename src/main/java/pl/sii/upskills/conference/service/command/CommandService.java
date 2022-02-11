package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.ConferenceInput;

public class CommandService {

    public Conference createConference(ConferenceInput conferenceInput) {
        speakerInputValidator.validate(speakerInput);
        Conference conference = mapper.apply(speakerInput);
        return speakerRepository.save(speaker);
    }
}
