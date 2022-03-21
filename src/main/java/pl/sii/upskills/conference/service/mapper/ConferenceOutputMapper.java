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
        return ConferenceOutput.builder()
                .withName(conference.getName())
                .withTitle(conference.getTitle())
                .withNumberOfPlaces(conference.getNumberOfPlaces())
                .withPrice(conference.getPrice())
                .withTimeSlot(conference.getTimeSlot())
                .withStatus(conference.getStatus())
                .withId(conference.getId())
                .withSpeeches(conference.getListOfSpeeches()
                        .stream()
                        .map(speechOutputMapper)
                        .collect(Collectors.toSet()))
                .build();
    }
}