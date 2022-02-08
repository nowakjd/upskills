package pl.sii.upskills.speaker.service.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;

import static org.assertj.core.api.Assertions.assertThat;

class SpeakerOutputMapperTest {
    @Test
    @DisplayName("Should map Speaker to SpeakerOutput")
    void shouldMapSpeaker() {
        // given
        SpeakerOutputMapper underTest = new SpeakerOutputMapper();
        Speaker speaker = new Speaker(1L, "John", "Doe", "123456789", "john@email.com", "My bio");

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