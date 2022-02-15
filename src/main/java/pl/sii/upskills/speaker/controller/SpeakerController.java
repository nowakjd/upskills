package pl.sii.upskills.speaker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speaker.service.command.SpeakerCommandService;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
class SpeakerController {
    SpeakerCommandService speakerService;
    SpeakerQueryService speakerQueryService;

    SpeakerController(SpeakerCommandService speakerService, SpeakerQueryService speakerQueryService) {
        this.speakerService = speakerService;
        this.speakerQueryService = speakerQueryService;
    }

    @PostMapping("/speakers")
    ResponseEntity<SpeakerOutput> createSpeaker(@RequestBody SpeakerInput speakerInput) {
        return new ResponseEntity<>(speakerService.addSpeaker(speakerInput), HttpStatus.CREATED);
    }

    @GetMapping("/speakers")
    ResponseEntity<List<SpeakerOutput>> findAll() {
        return new ResponseEntity<>(speakerQueryService.findAll(), HttpStatus.OK);
    }
}
