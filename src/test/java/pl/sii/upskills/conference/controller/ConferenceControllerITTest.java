package pl.sii.upskills.conference.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ConferenceControllerITTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private URI createServerAddress() throws URISyntaxException {
        return new URI("http://localhost:7070/api/v1/conferences");
    }

    private static final LocalDateTime START_FOR_TEST =
            LocalDateTime.of(2023, 1, 1, 0, 1);
    private static final LocalDateTime END_FOR_TEST =
            LocalDateTime.of(2023, 1, 1, 8, 1);

    @Test
    @DisplayName("shouldReturn2xxWhenCreateConferenceIsSuccessful")
    void createConference() throws Exception {
        // given:
        MoneyVO moneyVO = new MoneyVO(BigDecimal.valueOf(8.00), Currency.getInstance("USD"));
        TimeSlotVO timeSlotVO = new TimeSlotVO(START_FOR_TEST, END_FOR_TEST);

        ConferenceInput conferenceInput = new ConferenceInput("First ever", "Our first conference",
                200, moneyVO, timeSlotVO);
        // when:
        RequestEntity<ConferenceInput> request = RequestEntity
                .post(createServerAddress())
                .contentType(MediaType.APPLICATION_JSON)
                .body(conferenceInput);
        ResponseEntity<ConferenceOutput> response = restTemplate.exchange(request, ConferenceOutput.class);
        // then:
        ConferenceOutput body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("First ever", body.getName());
        assertEquals("Our first conference", body.getTitle());
        assertEquals(200, body.getNumberOfPlaces());
        assertEquals(LocalDateTime.of(2023, 1, 1, 8, 1),
                body.getTimeSlot().getEndDate());
    }
}
