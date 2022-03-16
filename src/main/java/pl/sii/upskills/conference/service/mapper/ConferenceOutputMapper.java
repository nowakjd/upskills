package pl.sii.upskills.conference.service.mapper;

import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConferenceOutputMapper implements Function<Conference, ConferenceOutput> {

    private final Function<Speech, SpeechOutput> speechOutputMapper;

    public ConferenceOutputMapper(Function<Speech, SpeechOutput> speechOutputMapper) {
        this.speechOutputMapper = speechOutputMapper;
    }

    @Override
    public ConferenceOutput apply(Conference conference) {
        ConferenceOutput.ConferenceOutputBuilder builder = new ConferenceOutput.ConferenceOutputBuilder();
        builder.withName(conference.getName());
        builder.withTitle(conference.getTitle());
        builder.withNumberOfPlaces(conference.getNumberOfPlaces());
        builder.withPrice(conference.getPrice());
        builder.withTimeSlot(conference.getTimeSlot());
        builder.withStatus(conference.getStatus());
        builder.withId(conference.getId());
        builder.withSpeeches(conference.getListOfSpeeches()
                .stream()
                .map(speechOutputMapper)
                .collect(Collectors.toSet()));
        return builder.build();
//        return new ConferenceOutput.ConferenceOutputBuilder(conference.getName(),
//                conference.getTitle(),
//                conference.getNumberOfPlaces(),
//                conference.getPrice(),
//                conference.getTimeSlot(),
//                conference.getStatus(),
//                conference.getId(),
//                conference.getListOfSpeeches()
//                        .stream()
//                        .map(speechOutputMapper)
//                        .collect(Collectors.toSet()));
//    }
    }
}
