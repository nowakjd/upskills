package pl.sii.upskills.speech.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.service.model.SpeechInput;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class SpeechMapperTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);

    TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(1).plusHours(3));


    @Test
    @DisplayName("Should map SpeechInput to Speech")
    void shouldMapSpeech() {
        // given
        SpeechMapper underTest = new SpeechMapper();
        SpeechInput speechInput = new SpeechInput("War never changes", timeSlot);

        // when
        Speech result = underTest.apply(new Speech(), speechInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("War never changes");
        assertThat(result.getTimeSlotVO().getEndDate()).isEqualTo(timeSlot.getEndDate());
    }

}