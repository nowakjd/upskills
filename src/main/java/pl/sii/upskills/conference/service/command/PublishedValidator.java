package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.Money;
import pl.sii.upskills.speech.persistence.Speech;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class PublishedValidator {


    Optional<Set<String>> validatePublished(Conference conference) {
        Set<String> errors = new HashSet<>();
        allSpeechesHaveAtLeastOneSpeaker(conference.getListOfSpeeches()).ifPresent(errors::addAll);
        priceIsNotNull(conference.getPrice()).ifPresent(errors::add);
        return optionalOf(errors);
    }

    private Optional<String> priceIsNotNull(Money price) {
        if (price == null) {
            return Optional.of("Price is required");
        }
        return Optional.empty();
    }

    private Optional<Set<String>> allSpeechesHaveAtLeastOneSpeaker(Collection<Speech> speeches) {
        Set<String> errors = new HashSet<>();
        speeches.forEach(speech -> hasAtLeastOneSpeaker(speech).ifPresent(errors::add));
        return optionalOf(errors);
    }

    private Optional<Set<String>> optionalOf(Set<String> errors) {
        if (errors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errors);
    }

    private Optional<String> hasAtLeastOneSpeaker(Speech speech) {
        if (speech.hasZeroSpeaker()) {
            return Optional.of("Speech with id " + speech.getId() + " doesn't have any speakers");
        }
        return Optional.empty();
    }
}