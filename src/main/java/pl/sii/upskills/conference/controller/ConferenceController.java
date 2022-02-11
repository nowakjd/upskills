package pl.sii.upskills.conference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.conference.service.command.CommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;


@RestController
@RequestMapping("/api/v1")
public class ConferenceController {
    CommandService commandService;

    public ConferenceController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/conferences")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ConferenceOutput> conferenceResponseEntity (@RequestBody ConferenceInput conferenceInput) {
        return new ResponseEntity<ConferenceOutput>(commandService.createConference(ConferenceInput));
    }
}
