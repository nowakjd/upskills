package pl.sii.upskills.speaker.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.service.model.SpeakerStatusInput;

@Component
class SpeakerStatusInputValidator {

    boolean validateStatus(SpeakerStatusInput speakerStatusInput) {
        SpeakerValidationException speakerValidationException = new SpeakerValidationException();

        /**
         * TO DO
        */

//        if (isEmpty(speakerStatusInput.getSpeakerStatus().name())) {
//            speakerValidationException.addError("Status must be valid");
//        }
//
//        if (!speakerValidationException.getErrors().isEmpty()) {
//            throw speakerValidationException;
//        }
        return true;
    }

    private boolean isEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }
}
