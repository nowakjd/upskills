package pl.sii.upskills.speech.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.common.TimeService;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.service.model.TimeSlot;
import pl.sii.upskills.speech.service.model.SpeechInput;

@Component
public class SpeechInputValidator {

    private final TimeService timeService;

    public SpeechInputValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    boolean validate(SpeechInput speechInput, Conference conference) {

        SpeechValidationException speechValidationException = new SpeechValidationException();

        if (isEmpty(speechInput.getTitle())) {
            speechValidationException.addError("Title is required");
        }

        if (speechInput.getTimeSlot() == null) {
            speechValidationException.addError("Start date and end date are required");
        }

        if (speechInput.getTimeSlot() != null
                && speechInput.getTimeSlot().getStartDate() == null) {
            speechValidationException.addError("Start date is required");
        }

        if (speechInput.getTimeSlot() != null
                && speechInput.getTimeSlot().getEndDate() == null) {
            speechValidationException.addError("End date is required");
        }

        if (isInPast(speechInput.getTimeSlot())) {
            speechValidationException.addError("Start date must be in the future");
        }

        if (hasEndBeforeStartIfExist(speechInput.getTimeSlot())) {
            speechValidationException.addError("The end date cannot be faster than start date");
        }

        if (speechInput.getTimeSlot() != null && speechInput.getTimeSlot().getStartDate() != null
                && speechInput.getTimeSlot().getEndDate() != null
                && !speechDurationIsValid(speechInput)) {
            speechValidationException.addError("Duration of speech must be at least 5 minutes and it"
                    + " cannot be longer than 8 hours");
        }

        if (speechInput.getTimeSlot() != null && speechInput.getTimeSlot().getStartDate() != null
                && speechInput.getTimeSlot().getEndDate() != null
                && !isInConference(conference, speechInput)) {
            speechValidationException.addError("Speech must be in range of conference");
        }

        if (!speechValidationException.getErrors().isEmpty()) {
            throw speechValidationException;
        }
        return true;
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

    private boolean speechDurationIsValid(SpeechInput speechInput) {
        return (speechInput.getTimeSlot().getStartDate().isBefore(
                speechInput.getTimeSlot().getEndDate().minusMinutes(5).minusSeconds(1))
                && speechInput.getTimeSlot().getStartDate().plusHours(8).plusSeconds(1).isAfter(
                        speechInput.getTimeSlot().getEndDate())
                );
    }

    private boolean isInConference(Conference conference, SpeechInput speechInput) {
        return (conference.getTimeSlot().getStartDate().isBefore(speechInput.getTimeSlot().getStartDate())
                && conference.getTimeSlot().getEndDate().isAfter(speechInput.getTimeSlot().getEndDate()));
    }

}
