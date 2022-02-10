package pl.sii.upskills.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sii.upskills.speaker.service.command.SpeakerValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(SpeakerValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handlingSpeakerValidationException(SpeakerValidationException e) {
        return e.getErrors();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handConstrainsViolations(ConstraintViolationException message) {
        return message.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .toList();
    }
}
