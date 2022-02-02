package pl.sii.upskills.speaker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeakerServiceTest {
    SpeakerService underTest;

    @BeforeEach
    void setUp() {
        SpeakerRepository repository = mock(SpeakerRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        SpeakerInputValidator validator = new SpeakerInputValidator();
        Mapper mapper = new Mapper();
        underTest = new SpeakerService(repository, validator, mapper);
    }

    @Test
    @DisplayName("Should add Speaker to repository")
    void shouldAddSpeaker() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "My bio");

        // when
        Speaker result = underTest.addSpeaker(speakerInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getPhoneNumber()).isEqualTo("123456789");
        assertThat(result.getEmail()).isEqualTo("john@email.com");
        assertThat(result.getBio()).isEqualTo("My bio");
    }


}