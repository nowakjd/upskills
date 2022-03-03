package pl.sii.upskills.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sii.upskills.conference.service.command.ConferenceBadRequestException;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.conference.service.command.ConferenceValidationException;
import pl.sii.upskills.speaker.service.command.SpeakerBadRequestException;
import pl.sii.upskills.speaker.service.command.SpeakerNotFoundException;
import pl.sii.upskills.speaker.service.command.SpeakerValidationException;
import pl.sii.upskills.speech.service.command.SpeakerSetValidationException;
import pl.sii.upskills.speech.service.command.SpeechValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
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

    @ExceptionHandler(ConferenceValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handlingConferenceValidationException(ConferenceValidationException e) {
        return e.getErrors();
    }

    @ExceptionHandler(ConferenceBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handleConferenceBadRequestException(ConferenceBadRequestException e) {
        return Collections.singletonList(e.getMessage());
    }

    @ExceptionHandler(SpeakerBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handleSpeakerBadRequestException(SpeakerBadRequestException e) {
        return Collections.singletonList(e.getMessage());
    }

    @ExceptionHandler(SpeakerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    List<String> handSpeakerNotFoundException(SpeakerNotFoundException e) {
        return e.getErrors();
    }

    @ExceptionHandler(ConferenceDraftNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    String handlingConferenceDraftNotFoundException(ConferenceDraftNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(SpeechValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handlingSpeechValidationException(SpeechValidationException e) {
        return e.getErrors();
    }

    @ExceptionHandler(SpeakerSetValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<String> handlingSpeakerSetValidationException(SpeakerSetValidationException e) {
        return e.getErrors();
    }

}
