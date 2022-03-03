package pl.sii.upskills.speech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speech.service.command.SpeechCommandService;
import pl.sii.upskills.speech.service.model.SpeakersForSpeechInput;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

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
    ResponseEntity<SpeechOutput> addSpeakers(@PathVariable("conferenceId") UUID uuid, @PathVariable("id") Long id,
                                             @RequestBody SpeakersForSpeechInput speakersForSpeechInput) {
        return new ResponseEntity<>(speechCommandService.addSpeakers(uuid, id, speakersForSpeechInput),
                HttpStatus.CREATED);
    }

}
