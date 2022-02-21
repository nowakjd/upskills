package pl.sii.upskills.speaker.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpeakerControllerITTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private URI createServerAddress() throws URISyntaxException {
        return new URI("http://localhost:7070/api/v1/speakers");
    }

    private URI updateServerAddress() throws URISyntaxException {
        return new URI ("http://localhost:7070/api/v1/speakers/7");
    }

    @Test
    @DisplayName("shouldReturn2xxWhenCreateSpeakerIsSuccessfull")
    void createSpeaker() throws Exception {
        // given:
        SpeakerInput speakerInput = new SpeakerInput("John", "Fowler", "987654321",
                "john.fowler@gmail.com", "It's my life");
        // when:
        RequestEntity<SpeakerInput> request = RequestEntity
                .post(createServerAddress())
                .contentType(MediaType.APPLICATION_JSON)
                .body(speakerInput);
        ResponseEntity<SpeakerOutput> response = restTemplate.exchange(request, SpeakerOutput.class);
        // then:
        SpeakerOutput body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("John", body.getFirstName());
        assertEquals("Fowler", body.getLastName());
        assertEquals("987654321", body.getPhoneNumber());
        assertEquals("john.fowler@gmail.com", body.getEmail());
        assertEquals("It's my life", body.getBio());
    }

    @Test
    @DisplayName("shouldReturn2xxWhenUpdateSpeakerIsSuccessfull")
    void updateSpeaker() throws Exception {
        // given:
        SpeakerInput speakerInput = new SpeakerInput("John", "Fowler", "987654321",
                "john.fowler@gmail.com", "It's my life");
        // when:
        RequestEntity<SpeakerInput> request = RequestEntity
                .put(updateServerAddress())
                .contentType(MediaType.APPLICATION_JSON)
                .body(speakerInput);
        ResponseEntity<SpeakerOutput> response = restTemplate.exchange(request, SpeakerOutput.class);
        // then:
        SpeakerOutput body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("John", body.getFirstName());
        assertEquals("Fowler", body.getLastName());
        assertEquals("987654321", body.getPhoneNumber());
        assertEquals("john.fowler@gmail.com", body.getEmail());
        assertEquals("It's my life", body.getBio());
    }
}
