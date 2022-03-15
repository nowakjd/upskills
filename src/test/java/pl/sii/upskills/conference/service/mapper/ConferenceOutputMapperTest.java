package pl.sii.upskills.conference.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConferenceOutputMapperTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);

    TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));

    @Test
    @DisplayName("Should map Conference to ConferenceOutPut")
    void shouldMapConference() {
        // given
        ConferenceOutputMapper underTest = new ConferenceOutputMapper(new SpeechOutputMapper(new SpeakerOutputMapper()));
        Conference conference = new Conference(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"), "adam",
                "damian", 15, ConferenceStatus.DRAFT, null, timeSlot);
        // when
        ConferenceOutput result = underTest.apply(conference);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("adam");
        assertThat(result.getTitle()).isEqualTo("damian");
        assertThat(result.getNumberOfPlaces()).isEqualTo(15);
        assertThat(result.getPrice()).isNull();
        assertThat(result.getTimeSlot()).isEqualTo(timeSlot);
        assertThat(result.getStatus()).isEqualTo(ConferenceStatus.DRAFT);
        assertThat(result.getId()).isEqualByComparingTo(UUID.fromString("0963c134-0141-415f-aaf6-89a502fb58bf"));
    }
}

