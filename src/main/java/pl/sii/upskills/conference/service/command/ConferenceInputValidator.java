package pl.sii.upskills.conference.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.common.TimeService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.TimeSlot;

@Component
class ConferenceInputValidator {

    private final TimeService timeService;

    public ConferenceInputValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    boolean validate(ConferenceInput conferenceInput) {

        ConferenceValidationException conferenceValidationException = new ConferenceValidationException();

        if (isEmpty(conferenceInput.getName())) {
            conferenceValidationException.addError("Name is required");
        }
        if (isEmpty(conferenceInput.getTitle())) {
            conferenceValidationException.addError("Title is required");
        }
        if (conferenceInput.getNumberOfPlaces() < 1) {
            conferenceValidationException.addError("Number of places must be positive");
        }

        if (isInPast(conferenceInput.getTimeSlot())) {
            conferenceValidationException.addError("Start date must be in the future");
        }

        if (hasEndBeforeStartIfExist(conferenceInput.getTimeSlot())) {
            conferenceValidationException.addError("The end date cannot be faster than start date");
        }

        if (!conferenceValidationException.getErrors().isEmpty()) {
            throw conferenceValidationException;
        }
        return true;
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }

    private boolean isInPast(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }
        return timeSlot.getStartDate().isBefore(timeService.get());
    }

    private boolean hasEndBeforeStartIfExist(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }
        return timeSlot.getEndDate().isBefore(timeSlot.getStartDate());
    }
}

