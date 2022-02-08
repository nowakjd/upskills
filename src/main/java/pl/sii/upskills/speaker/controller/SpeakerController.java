package pl.sii.upskills.speaker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.SpeakerInput;
import pl.sii.upskills.speaker.service.SpeakerService;
import pl.sii.upskills.speaker.service.query.SpeakerOutput;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;

import javax.validation.Valid;
import pl.sii.upskills.speaker.service.query.SpeakerOutput;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
class SpeakerController {
    SpeakerService speakerService;
    SpeakerQueryService speakerQueryService;

    SpeakerController(SpeakerService speakerService, SpeakerQueryService speakerQueryService) {
        this.speakerService = speakerService;
        this.speakerQueryService = speakerQueryService;
    }

    @PostMapping("/speakers")
    ResponseEntity<Speaker> createSpeaker(@RequestBody SpeakerInput speakerInput) {
        return new ResponseEntity<>(speakerService.addSpeaker(speakerInput), HttpStatus.CREATED);
    }

    @GetMapping("/speakers")
    ResponseEntity<List<SpeakerOutput>> findAll() {
        return new ResponseEntity<>(speakerQueryService.findAll(), HttpStatus.OK);
    }
}
