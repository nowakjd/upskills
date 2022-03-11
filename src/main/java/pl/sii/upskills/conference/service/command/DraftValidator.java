package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.common.TimeService;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speech.persistence.Speech;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DraftValidator {
    TimeService timeService;

    DraftValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    Optional<Set<String>> validateDraft(Conference conference) {
        Set<String> errors = new HashSet<>();
        hasName(conference).ifPresent(errors::add);
        hasTitle(conference).ifPresent(errors::add);
        hasStartDate(conference.getTimeSlot()).ifPresent(errors::add);
        isInFuture(conference.getTimeSlot()).ifPresent(errors::add);
        hasEndDate(conference.getTimeSlot()).ifPresent(errors::add);
        hasEndAfterStart(conference.getTimeSlot()).ifPresent(errors::add);
        hasPositiveNumberOfPlaces(conference).ifPresent(errors::add);
        allSpeechesAreInTimeFrame(conference).ifPresent(errors::addAll);
        return optionalOf(errors);
    }

    private Optional<String> hasEndAfterStart(TimeSlot timeSlot) {
        if (isComplete(timeSlot) && startAfterEnd(timeSlot)) {
            return Optional.of("The end date cannot be faster than start date");
        }
        return Optional.empty();
    }

    private boolean startAfterEnd(TimeSlot timeSlot) {
        return timeSlot.getStartDate().isAfter(timeSlot.getEndDate());
    }

    private boolean isComplete(TimeSlot timeSlot) {
        return timeSlot != null && timeSlot.getStartDate() != null && timeSlot.getEndDate() != null;
    }

    private Optional<String> hasEndDate(TimeSlot timeSlot) {
        if (timeSlot == null || timeSlot.getEndDate() == null) {
            return Optional.of("End date is required");
        }
        return Optional.empty();
    }

    private Optional<String> isInFuture(TimeSlot timeSlot) {
        if (timeSlot != null && timeSlot.getStartDate() != null && isInPast(timeSlot)) {
            return Optional.of("Start date must be in the future");

        }
        return Optional.empty();
    }

    private boolean isInPast(TimeSlot timeSlot) {
        return timeSlot.getStartDate().isBefore(timeService.get());
    }

    private Optional<String> hasStartDate(TimeSlot timeSlot) {
        if (timeSlot == null || timeSlot.getStartDate() == null) {
            return Optional.of("Start date is required");
        }
        return Optional.empty();

    }


    Optional<Set<String>> optionalOf(Set<String> errors) {
        if (errors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errors);
    }

    Optional<Set<String>> allSpeechesAreInTimeFrame(Conference conference) {
        Set<String> errors = new HashSet<>();
        conference
                .getListOfSpeeches()
                .forEach(speech -> isInTimeFrame(speech, conference).ifPresent(errors::add));
        return optionalOf(errors);
    }

    private Optional<String> isInTimeFrame(Speech speech, Conference conference) {
        if (speech.getTimeSlotVO().fitIn(conference.getTimeSlot())) {
            return Optional.empty();
        }
        return Optional.of("Speech with id " + speech.getId() + " is beyond conference time slot");
    }

    Optional<String> hasPositiveNumberOfPlaces(Conference conference) {
        if (conference.getNumberOfPlaces() < 1) {
            return Optional.of("Number of places must be positive");
        }
        return Optional.empty();
    }


    Optional<String> hasTitle(Conference conference) {
        if (isEmpty(conference.getTitle())) {
            return Optional.of("Title is required");
        }
        return Optional.empty();
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }


    Optional<String> hasName(Conference conference) {
        if (isEmpty(conference.getName())) {
            return Optional.of("Name is required");
        }
        return Optional.empty();
    }


}