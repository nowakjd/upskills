package pl.sii.upskills.conference.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConferenceMapperTest {
    private static final UUID TEST_UUID = UUID.fromString("92497903-d068-4243-9285-8f240517d093");
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
        Conference result = underTest.apply(new Conference(), conferenceInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Conference");
        assertThat(result.getTitle()).isEqualTo("Doe");
        assertThat(result.getNumberOfPlaces()).isEqualTo(12345);
        assertThat(result.getPrice()).isNull();
        assertThat(result.getTimeSlot()).isEqualTo(timeSlot);
    }

    @DisplayName("Should map conference input into existing conference")
    @Test
    void updateMapping() {
        // given
        ConferenceMapper underTest = new ConferenceMapper();
        ConferenceInput conferenceInput = new ConferenceInput("Conference", "Doe",
                12345, null, timeSlot);
        Conference conference = new Conference(TEST_UUID, "name", "title", 100,
                ConferenceStatus.DRAFT, null, timeSlot);

        // when
        Conference result = underTest.apply(conference, conferenceInput);

        // then
        assertThat(result.getId()).isEqualTo(conference.getId());
        assertThat(result.getName()).isEqualTo(conferenceInput.getName());
        assertThat(result.getTitle()).isEqualTo(conferenceInput.getTitle());
        assertThat(result.getPrice()).isNull();
        assertThat(result.getTimeSlot()).isEqualTo(conferenceInput.getTimeSlot());
        assertThat(result.getNumberOfPlaces()).isEqualTo(conferenceInput.getNumberOfPlaces());
        assertThat(result.getStatus()).isEqualTo(conference.getStatus());
    }

}

