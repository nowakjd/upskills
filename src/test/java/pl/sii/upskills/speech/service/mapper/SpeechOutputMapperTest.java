package pl.sii.upskills.speech.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.MoneyVO;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.TreeSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SpeechOutputMapperTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    private static final UUID ID_OF_DRAFT_IN_DATABASE =
            UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf");

    TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(1).plusHours(3));
    Conference conference = new Conference(ID_OF_DRAFT_IN_DATABASE, "To iron or not ?", "Masterful ironing",
            30, ConferenceStatus.DRAFT, new MoneyVO(BigDecimal.valueOf(9.00), Currency.getInstance("USD")),
            timeSlot);

    @Test
    @DisplayName("Should map speech to speechoutput")
    void shouldMapSpeechOutput() {
        // given
        SpeechOutputMapper underTest = new SpeechOutputMapper(new SpeakerOutputMapper());
        Speech speech = new Speech(3L, "Modern ironing techniques", timeSlot, conference, new TreeSet<>());

        // when
        SpeechOutput result = underTest.apply(speech);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Modern ironing techniques");
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getSpeakers()).isEmpty();
    }
}