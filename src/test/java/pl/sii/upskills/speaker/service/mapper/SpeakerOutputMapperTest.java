package pl.sii.upskills.speaker.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.sii.upskills.speaker.persistence.SpeakerStatus.ACTIVE;

class SpeakerOutputMapperTest {
    @Test
    @DisplayName("Should map Speaker to SpeakerOutput")
    void shouldMapSpeaker() {
        // given
        SpeakerOutputMapper underTest = new SpeakerOutputMapper();
        Speaker speaker = new Speaker(1L, "John", "Doe", "123456789", "john@email.com", "My bio", ACTIVE);

        // when
        SpeakerOutput result = underTest.apply(speaker);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getPhoneNumber()).isEqualTo("123456789");
        assertThat(result.getEmail()).isEqualTo("john@email.com");
        assertThat(result.getBio()).isEqualTo("My bio");
    }
}