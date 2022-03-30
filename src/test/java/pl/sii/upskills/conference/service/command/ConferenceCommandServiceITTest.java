package pl.sii.upskills.conference.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.conference.service.query.ConferenceQueryService;
import pl.sii.upskills.speaker.service.command.SpeakerCommandService;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speech.service.command.SpeechCommandService;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechSpeakersInput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class ConferenceCommandServiceITTest {

    @Autowired
    ConferenceCommandService underTest;

    @Autowired
    SpeechCommandService speechCommandService;

    @Autowired
    SpeakerCommandService speakerCommandService;

    @Autowired
    ConferenceQueryService conferenceQueryService;

    @Test
    @DisplayName("Should add conference to database")
    void happyPath() {
        // given
        ConferenceInput conferenceInput = new ConferenceInput("First", "Our First Conference",
                200, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
                new TimeSlotVO(LocalDateTime.of(2023, 1, 1, 0, 1),
                        LocalDateTime.of(2023, 1, 1, 8, 1)));

        // when
        ConferenceOutput conferenceOutput = underTest.createConference(conferenceInput);

        // then
        assertThat(conferenceOutput.getId()).isNotNull();
        assertThat(conferenceOutput.getNumberOfPlaces()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should publish eligible conference")
    void publish() {
        //given
        UUID idOfEligibleConferenceDraft = getIdOfEligibleConference();
        //when
        ConferenceOutput publishedConference = underTest.changeStatus(idOfEligibleConferenceDraft,
                ConferenceStatus.PUBLISHED);
        //then
        assertThat(publishedConference.getStatus()).isEqualTo(ConferenceStatus.PUBLISHED);
        assertThrows(ConferenceNotFoundException.class,
                () -> conferenceQueryService.findDraft(idOfEligibleConferenceDraft));
        assertThat(conferenceQueryService.findByStatus("PUBLISHED"))
                .anyMatch(publishedConference::equals);
    }

    private UUID getIdOfEligibleConference() {
        ConferenceInput conferenceInput = new ConferenceInput("First", "Our First Conference",
                200, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
                new TimeSlotVO(LocalDateTime.of(2023, 1, 1, 0, 1),
                        LocalDateTime.of(2023, 1, 1, 8, 1)));
        UUID result = underTest.createConference(conferenceInput).getId();
        SpeechInput speechInput = new SpeechInput("Title", new TimeSlotVO(
                LocalDateTime.of(2023, 1, 1, 1, 1),
                LocalDateTime.of(2023, 1, 1, 2, 1)));
        Long speechId = speechCommandService.createSpeech(result, speechInput).getId();
        SpeakerInput speakerInput = new SpeakerInput("name", "lastName", "123",
                "email@email.email", "bio");
        Long speakerId = speakerCommandService.addSpeaker(speakerInput).getId();
        speechCommandService.addSpeakers(result, speechId, new SpeechSpeakersInput(Set.of(speakerId)));
        return result;
    }

}
