package pl.sii.upskills.conference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;


@RestController
@RequestMapping("/api/v1")
public class ConferenceController {
    ConferenceCommandService commandService;

    public ConferenceController(ConferenceCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/conferences")
    ResponseEntity<ConferenceOutput> conferenceResponseEntity(@RequestBody ConferenceInput conferenceInput) {
        return new ResponseEntity<>(commandService.createConference(conferenceInput), HttpStatus.CREATED);
    }
}
