package pl.sii.upskills.speaker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.SpeakerService;

@RestController
@RequestMapping("/api/v1")
class SpeakerController {
    SpeakerService speakerService;

    SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PostMapping("/speakers")
    ResponseEntity<Speaker> createSpeaker(@RequestBody Speaker speaker){
        return new ResponseEntity<>(speakerService.addSpeaker(speaker), HttpStatus.CREATED);
        //return  ResponseEntity.(speakerService.addSpeaker(speaker));

    }
}
