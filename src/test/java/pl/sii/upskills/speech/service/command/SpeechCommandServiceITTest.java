package pl.sii.upskills.speech.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.model.ConferenceInput;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.TreeSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpeechCommandServiceITTest {

    @Autowired
    ConferenceCommandService conferenceCommandService;
    @Autowired
    SpeechCommandService underTest;

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2024, 1, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1), NOW_FOR_TEST.plusDays(1).plusHours(2));
    private static final TimeSlotVO CONFERENCE_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));

    @Test
    @DisplayName("Should add speech to database")
    void happyPath() {
        // given
        UUID conferenceId = createConference();
        SpeechInput speechInput = new SpeechInput("Holy Hand Grenade", CORRECT_TIMESLOT, new TreeSet<>());

        // when
        SpeechOutput speechOutput = underTest.createSpeech(conferenceId, speechInput);

        // then
        assertThat(speechOutput.getId()).isNotNull();
        assertThat(speechOutput.getTitle()).isEqualTo("Holy Hand Grenade");
    }

    private UUID createConference() {
        return conferenceCommandService.createConference(new ConferenceInput("Worms", "Armageddon ?",
                        200, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
                        CONFERENCE_TIMESLOT)).getId();
    }
}
