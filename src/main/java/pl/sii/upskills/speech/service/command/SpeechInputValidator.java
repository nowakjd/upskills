package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.common.TimeService;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speech.service.model.SpeechInput;

import java.time.Duration;

@Component
public class SpeechInputValidator {
    private static final int MINIMAL_DURATION_IN_MINUTES = 5;
    private static final int MAX_DURATION_IN_HOURS = 8;
    private static final Duration MINIMAL_DURATION = Duration.ofMinutes(MINIMAL_DURATION_IN_MINUTES);
    private static final Duration MAXIMUM_DURATION = Duration.ofHours(MAX_DURATION_IN_HOURS);
    private final TimeService timeService;

    public SpeechInputValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    void validate(SpeechInput speechInput, Conference conference) {

        SpeechValidationException speechValidationException = new SpeechValidationException();

        if (isEmpty(speechInput.getTitle())) {
            speechValidationException.addError("Title is required");
        }

        if (speechInput.getTimeSlot() == null) {
            speechValidationException.addError("Start date and end date are required");
        } else if (speechInput.getTimeSlot().getStartDate() == null) {
            speechValidationException.addError("Start date is required");
        } else if (speechInput.getTimeSlot().getEndDate() == null) {
            speechValidationException.addError("End date is required");
        } else if (isInPast(speechInput.getTimeSlot())) {
            speechValidationException.addError("Start date must be in the future");
        } else if (hasEndBeforeStartIfExist(speechInput.getTimeSlot())) {
            speechValidationException.addError("The end date cannot be faster than start date");
        } else if (speechDurationIsInvalid(speechInput)) {
            speechValidationException.addError("Duration of speech must be at least 5 minutes and it"
                    + " cannot be longer than 8 hours");
        } else if (!isInConference(conference, speechInput)) {
            speechValidationException.addError("Speech must be in range of conference");
        }

        if (!speechValidationException.getErrors().isEmpty()) {
            throw speechValidationException;
        }
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }

    private boolean isInPast(TimeSlot timeSlot) {
        if (timeSlot == null || timeSlot.getStartDate() == null) {
            return false;
        }
        return timeSlot.getStartDate().isBefore(timeService.get());
    }

    private boolean hasEndBeforeStartIfExist(TimeSlot timeSlot) {
        if (timeSlot == null || timeSlot.getStartDate() == null || timeSlot.getEndDate() == null) {
            return false;
        }
        return timeSlot.getEndDate().isBefore(timeSlot.getStartDate());
    }

    private boolean speechDurationIsInvalid(SpeechInput speechInput) {
        Duration speechDuration = speechInput.getTimeSlot().toDuration();
        boolean isTooShort = speechDuration.compareTo(MINIMAL_DURATION) < 0;
        boolean isTooLong = speechDuration.compareTo(MAXIMUM_DURATION) > 0;

        return isTooLong || isTooShort;
    }

    private boolean isInConference(Conference conference, SpeechInput speechInput) {
        return (conference.getTimeSlot().getStartDate().isBefore(speechInput.getTimeSlot().getStartDate())
                && conference.getTimeSlot().getEndDate().isAfter(speechInput.getTimeSlot().getEndDate()));
    }

}
