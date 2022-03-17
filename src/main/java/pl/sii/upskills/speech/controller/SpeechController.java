package pl.sii.upskills.speech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speech.service.command.SpeechCommandService;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class SpeechController {

    private final SpeechCommandService speechCommandService;

    public SpeechController(SpeechCommandService speechCommandService) {
        this.speechCommandService = speechCommandService;
    }

    @PostMapping("/conferences/{conferenceId}/speeches")
    ResponseEntity<SpeechOutput> createSpeech(@PathVariable("conferenceId") UUID uuid,
                                              @RequestBody SpeechInput speechInput) {
        return new ResponseEntity<>(speechCommandService.createSpeech(uuid, speechInput), HttpStatus.CREATED);
    }

    @PutMapping("/conferences/{conferenceId}/speeches/{id}/speakers")
    ResponseEntity<SpeechOutput> addSpeakers(@PathVariable("conferenceId") UUID conferenceId,
                                             @PathVariable("id") Long id,
                                             @RequestBody SpeechSpeakersInput speechSpeakersInput) {
        return new ResponseEntity<>(speechCommandService.addSpeakers(conferenceId, id, speechSpeakersInput),
                HttpStatus.CREATED);
    }

    @PutMapping("/conferences/{conferenceId}/speeches/{id}")
    ResponseEntity<SpeechOutput> updateSpeech(@PathVariable("conferenceId") UUID conferenceId,
                                              @PathVariable("id") Long id,
                                              @RequestBody SpeechInput speechInput) {
        return new ResponseEntity<>(speechCommandService.updateSpeech(conferenceId, id, speechInput), HttpStatus.OK);
    }

    @DeleteMapping("/conferences/{conferenceId}/speeches/{speechId}/speakers/{id}")
    ResponseEntity<SpeechOutput> deleteSpeaker(@PathVariable("conferenceId") UUID conferenceId,
                                               @PathVariable("speechId") Long speechId, @PathVariable("id") Long id) {
        return new ResponseEntity<>(speechCommandService.deleteSpeaker(conferenceId, speechId, id),
                HttpStatus.OK);
    }

    @PostMapping("/conferences/{conferenceId}/speeches/{speechId}/speakers/{id}")
    ResponseEntity<SpeechOutput> addSpeaker(@PathVariable("conferenceId") UUID conferenceId,
                                               @PathVariable("speechId") Long speechId, @PathVariable("id") Long id) {
        return new ResponseEntity<>(speechCommandService.addSpeaker(conferenceId, speechId, id),
                HttpStatus.CREATED);
    }

}
