package pl.sii.upskills.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sii.upskills.speaker.service.SpeakerValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        //List<ConstraintViolation<?>> wyjatek = List.copyOf(message.getConstraintViolations());
        //return wyjatek.stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.toList());
        return message.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.toList());
    }

}
