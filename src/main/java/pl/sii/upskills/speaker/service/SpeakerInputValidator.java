package pl.sii.upskills.speaker.service;

import org.springframework.stereotype.Component;

@Component
class SpeakerInputValidator {

    boolean validate(SpeakerInput speakerInput) {
        SpeakerValidationException speakerValidationException = new SpeakerValidationException();

        if (isEmpty(speakerInput.getFirstName())) {
            speakerValidationException.addError("First name is required");
        }
        if (isEmpty(speakerInput.getLastName())) {
            speakerValidationException.addError("Last name is required");
        }
        if (isEmpty(speakerInput.getEmail()) && isEmpty(speakerInput.getPhoneNumber())) {
            speakerValidationException.addError("Email or phone number is required");
        }

        if (speakerValidationException.getErrors().size() > 0) {
            throw speakerValidationException;
        }
        return true;
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }
}
