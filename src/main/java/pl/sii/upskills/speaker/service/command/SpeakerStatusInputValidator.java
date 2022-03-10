package pl.sii.upskills.speaker.service.command;

import org.springframework.stereotype.Component;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;

import java.util.Arrays;

@Component
class SpeakerStatusInputValidator {


    boolean validateStatus(SpeakerStatus speakerStatus) {

        SpeakerValidationException speakerValidationException = new SpeakerValidationException();

        if (speakerStatus == null) {
            speakerValidationException.addError("You have provided wrong status!"
                    + "Please use one of the following statuses : " + Arrays.toString(SpeakerStatus.values()));
        }

        if (!speakerValidationException.getErrors().isEmpty()) {
            throw speakerValidationException;
        }

        return true;
    }
}


