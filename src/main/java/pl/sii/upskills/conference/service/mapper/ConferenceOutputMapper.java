package pl.sii.upskills.conference.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.util.function.Function;

@Component
public class ConferenceOutputMapper implements Function<Conference, ConferenceOutput> {

    @Override
    public ConferenceOutput apply(Conference conference) {
        return new ConferenceOutput(conference.getName(),
                conference.getTitle(),
                conference.getNumberOfPlaces(),
                conference.getPrice(),
                conference.getTimeSlot(),
                conference.getStatus(),
                conference.getId());
    }
}
