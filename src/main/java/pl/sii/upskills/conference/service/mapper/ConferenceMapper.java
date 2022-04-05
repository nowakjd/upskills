package pl.sii.upskills.conference.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.util.function.BiFunction;

@Component
public class ConferenceMapper implements BiFunction<Conference, ConferenceInput, Conference> {
    @Override
    public Conference apply(Conference conference,
                            ConferenceInput conferenceInput) {
        conference.setName(conferenceInput.getName());
        conference.setTitle(conferenceInput.getTitle());
        conference.setNumberOfPlaces(conferenceInput.getNumberOfPlaces());
        conference.setPrice((MoneyVO) conferenceInput.getPrice());
        conference.setTimeSlot((TimeSlotVO) conferenceInput.getTimeSlot());
        conference.setStatus(ConferenceStatus.DRAFT);
        return conference;
    }
}
