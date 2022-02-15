package pl.sii.upskills.conference.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.MoneyDAO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.util.function.Function;

@Component
public class ConferenceMapper implements Function<ConferenceInput, Conference> {
    @Override
    public Conference apply(ConferenceInput conferenceInput) {
        Conference conference = new Conference();
        conference.setName(conferenceInput.getName());
        conference.setTitle(conferenceInput.getTitle());
        conference.setNumberOfPlaces(conferenceInput.getNumberOfPlaces());
        conference.setPrice((MoneyDAO) conferenceInput.getPrice());
        conference.setTimeSlot((TimeSlotVO) conferenceInput.getTimeSlot());
        conference.setStatus(ConferenceStatus.DRAFT);
        return conference;
    }
}
