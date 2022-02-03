package pl.sii.upskills.speaker.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

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

        if (!isEmpty(speakerInput.getEmail()) && isEmailInvalid(speakerInput.getEmail())){
            speakerValidationException.addError("email must be valid");
        }

        if (!speakerValidationException.getErrors().isEmpty()) {
            throw speakerValidationException;
        }
        return true;
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }

    public static boolean isEmailInvalid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return ! EMAIL_REGEX.matcher(email).matches();
    }
}
