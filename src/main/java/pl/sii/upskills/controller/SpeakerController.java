package pl.sii.upskills.controller;

import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.model.Speaker;
import pl.sii.upskills.service.SpeakerService;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {
    SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker){
        return speaker= speakerService.addSpeaker(speaker);
    }
}
