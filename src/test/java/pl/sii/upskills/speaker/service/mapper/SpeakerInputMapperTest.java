package pl.sii.upskills.speaker.service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.service.model.SpeakerInput;

import static org.assertj.core.api.Assertions.assertThat;

class SpeakerInputMapperTest {

    @Test
    @DisplayName("Should map SpeakerInput to Speaker")
    void shouldMapSpeaker() {
        // given
        SpeakerInputMapper underTest = new SpeakerInputMapper();
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe",
                "123456789", "john@email.com", "My bio");
        Speaker speaker = new Speaker();
        // when
        Speaker result = underTest.apply(speaker, speakerInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getPhoneNumber()).isEqualTo("123456789");
        assertThat(result.getEmail()).isEqualTo("john@email.com");
        assertThat(result.getBio()).isEqualTo("My bio");
    }

}