package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.TimeSlot;

import java.time.LocalDateTime;

class ConferenceInputValidator {

    public static final int minimalDaysBeforeConference = 7;

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

        if (isNotEnoughIntoFutureIfExist(conferenceInput.getTimeSlot(), minimalDaysBeforeConference)) {
            conferenceValidationException.addError("Start date must be "
                    + minimalDaysBeforeConference
                    + " days in the future");
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

    private boolean isNotEnoughIntoFutureIfExist(TimeSlot timeSlot, int days) {
        if (timeSlot == null) {
            return false;
        }
        return timeSlot.getStartDate().isBefore(LocalDateTime.now().plusDays(7));
    }

    private boolean hasEndBeforeStartIfExist(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }
        return timeSlot.getEndDate().isBefore(timeSlot.getStartDate());
    }
}

