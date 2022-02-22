package pl.sii.upskills.conference.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ConferenceMapperTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);

    TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));

    @Test
    @DisplayName("Should map ConferenceInput to Conference")
    void shouldMapConference() {
        // given
        ConferenceMapper underTest = new ConferenceMapper();
        ConferenceInput conferenceInput = new ConferenceInput("Conference", "Doe",
                12345, null, timeSlot);

        // when
        Conference result = underTest.apply(conferenceInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Conference");
        assertThat(result.getTitle()).isEqualTo("Doe");
        assertThat(result.getNumberOfPlaces()).isEqualTo(12345);
        assertThat(result.getPrice()).isNull();
        assertThat(result.getTimeSlot()).isEqualTo(timeSlot);
    }
}

