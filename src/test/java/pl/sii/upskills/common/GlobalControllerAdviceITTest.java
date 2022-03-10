package pl.sii.upskills.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GlobalControllerAdviceITTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String API_ROOT = "http://localhost:7070/api/v1/conferences";
    private static final String API_ROOTS = "http://localhost:7070/api/v1/speakers";

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"DRA", "  ", "\t", " DRAFT", "DRAFT   "})
    @DisplayName("shouldReturn4xxWhenBadStatusConferencesRequest")
    void handleConferenceBadRequestException(String parameter) {

        // when:
        ResponseEntity<String> response = restTemplate.getForEntity(API_ROOT + "?status=" + parameter, String.class);

        // then:

        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(),
                "[\"You have provided wrong status!Please use one of the following statuses : "
                        + Arrays.toString(ConferenceStatus.values()) + "\"]");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"ACTIV", "  ", "\t", " ACTIVE", "INACTIVE   "})
    @DisplayName("shouldReturn4xxWhenBadStatusSpeakerRequest")
    void handleSpeakerBadRequestException(String parameter) {

        // when:
        ResponseEntity<String> response = restTemplate.getForEntity(API_ROOTS + "?speakerStatus="
                + parameter, String.class);

        // then:

        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(),
                "[\"You have provided wrong status!Please use one of the following statuses : "
                        + Arrays.toString(SpeakerStatus.values()) + "\"]");
    }
}
