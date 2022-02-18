package pl.sii.upskills.conference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class ConferenceController {
    final ConferenceCommandService commandService;

    public ConferenceController(ConferenceCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/conferences")
    ResponseEntity<ConferenceOutput> conferenceResponseEntity(@RequestBody ConferenceInput conferenceInput) {
        return new ResponseEntity<>(commandService.createConference(conferenceInput), HttpStatus.CREATED);
    }

    @PutMapping("/conferences/{uuid}")
    ResponseEntity<ConferenceOutput> updateConference(@PathVariable("uuid") UUID id,
                                                      @RequestBody ConferenceInput conferenceInput) {
        return new ResponseEntity<>(commandService.updateConference(id, conferenceInput), HttpStatus.OK);
    }
}
