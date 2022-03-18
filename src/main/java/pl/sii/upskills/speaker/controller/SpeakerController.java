package pl.sii.upskills.speaker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.command.SpeakerBadRequestException;
import pl.sii.upskills.speaker.service.command.SpeakerCommandService;
import pl.sii.upskills.speaker.service.mapper.SpeakerStatusInput;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
class SpeakerController {
    final SpeakerCommandService speakerService;
    final SpeakerQueryService speakerQueryService;

    SpeakerController(SpeakerCommandService speakerService, SpeakerQueryService speakerQueryService) {
        this.speakerService = speakerService;
        this.speakerQueryService = speakerQueryService;
    }

    @PostMapping("/speakers")
    ResponseEntity<SpeakerOutput> createSpeaker(@RequestBody SpeakerInput speakerInput) {
        return new ResponseEntity<>(speakerService.addSpeaker(speakerInput), HttpStatus.CREATED);
    }

    @GetMapping("/speakers")
    ResponseEntity<List<SpeakerOutput>> findAllWithStatus(@RequestParam(required = false) String speakerStatus) {
        List<SpeakerOutput> speakers = Optional.ofNullable(speakerStatus)
                .map(this::mapToEnum)
                .map(speakerQueryService::findSpeakersByStatus)
                .orElseGet(speakerQueryService::findAllSpeakers);
        return new ResponseEntity<>(speakers, HttpStatus.OK);
    }

    @PutMapping("/speakers/{id}")
    ResponseEntity<SpeakerOutput> updateSpeaker(@RequestBody SpeakerInput speakerInput, @PathVariable Long id) {
        return new ResponseEntity<>(speakerService.updateSpeaker(id, speakerInput), HttpStatus.OK);
    }

    @PutMapping("/speakers/{id}/status")
    ResponseEntity<SpeakerOutput> changeStatus(@RequestBody SpeakerStatusInput speakerStatusInput,
                                               @PathVariable Long id) {
        SpeakerStatus speakerStatus = mapToEnum(speakerStatusInput.getStatus());
        return new ResponseEntity<>(speakerService.changeStatus(id, speakerStatus), HttpStatus.OK);
    }

    private SpeakerStatus mapToEnum(String speakerStatus) {
        try {
            return SpeakerStatus.valueOf(speakerStatus);
        } catch (IllegalArgumentException e) {
            throw new SpeakerBadRequestException("You have provided wrong status!"
                    + "Please use one of the following statuses : " + Arrays.toString(SpeakerStatus.values()));
        }
    }
}
