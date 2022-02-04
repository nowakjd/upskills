package pl.sii.upskills.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sii.upskills.speaker.service.SpeakerValidationException;

import java.util.List;

@RestControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(SpeakerValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handlingSpeakerValidationException(SpeakerValidationException e) {
        return e.getErrors();
    }
}
