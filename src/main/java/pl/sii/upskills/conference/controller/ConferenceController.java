package pl.sii.upskills.conference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.conference.service.query.ConferenceQueryService;

import java.util.List;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class ConferenceController {
    final ConferenceCommandService commandService;
    final ConferenceQueryService conferenceQueryService;

    public ConferenceController(ConferenceCommandService commandService,
                                ConferenceQueryService conferenceQueryService) {
        this.commandService = commandService;
        this.conferenceQueryService = conferenceQueryService;
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


    @GetMapping("/conferences/statuses")
    @ResponseStatus(HttpStatus.OK)
    ConferenceStatus[] status() {
        return ConferenceStatus.values();
    }

    @GetMapping("/conferences")
    ResponseEntity<List<ConferenceOutput>> findByStatus(@RequestParam(required = false) String status) {
        return new ResponseEntity<>(conferenceQueryService.findByStatus(status), HttpStatus.OK);
    }
}