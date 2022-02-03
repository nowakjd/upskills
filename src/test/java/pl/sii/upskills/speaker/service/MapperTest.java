package pl.sii.upskills.speaker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;

import static org.assertj.core.api.Assertions.assertThat;


class MapperTest {



    @Test
    @DisplayName("Should map SpeakerInput to Speaker")
    void shouldMapSpeaker() {
        // given
        Mapper underTest = new Mapper();
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "My bio");
        Speaker speaker = new Speaker(null,"John", "Doe", "123456789", "john@email.com", "My bio");
        // when
        Speaker result = underTest.apply(speakerInput);

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