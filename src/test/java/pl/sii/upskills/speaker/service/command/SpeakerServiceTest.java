package pl.sii.upskills.speaker.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.persistence.SpeakerStatus;
import pl.sii.upskills.speaker.service.mapper.SpeakerInputMapper;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeakerServiceTest {
    public static final Long ID_OUTSIDE_DATABASE = 666L;
    public static final Long ID_INSIDE_DATABASE = 3L;
    public static final Speaker SPEAKER_INSIDE_DATABASE = new Speaker(ID_INSIDE_DATABASE, "John", "Doe",
            "123456789", "john@email.com", "My bio", SpeakerStatus.ACTIVE);
    SpeakerCommandService underTest;

    @BeforeEach
    void setUp() {
        SpeakerRepository repository = mock(SpeakerRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        when(repository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());
        when(repository.findById(ID_INSIDE_DATABASE)).thenReturn(Optional.of(SPEAKER_INSIDE_DATABASE));
        SpeakerInputValidator validator = new SpeakerInputValidator();
        Function<Speaker, SpeakerOutput> outputMapper = new SpeakerOutputMapper();
        BiFunction<Speaker, SpeakerInput, Speaker> inputMapper = new SpeakerInputMapper();
        underTest = new SpeakerCommandService(repository, validator, outputMapper, inputMapper);
    }

    @Test
    @DisplayName("Should return Speaker after it's creation")
    void shouldAddSpeaker() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789",
                "john@email.com", "My bio", SpeakerStatus.ACTIVE);

        // when
        SpeakerOutput result = underTest.addSpeaker(speakerInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(speakerInput.getFirstName());
        assertThat(result.getLastName()).isEqualTo(speakerInput.getLastName());
        assertThat(result.getPhoneNumber()).isEqualTo(speakerInput.getPhoneNumber());
        assertThat(result.getEmail()).isEqualTo(speakerInput.getEmail());
        assertThat(result.getBio()).isEqualTo(speakerInput.getBio());
    }

    @Test
    @DisplayName("Should return Speaker after it's update")
    void shouldUpdateSpeaker() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe",
                "123456789", "john@email.com", "My bio", SpeakerStatus.INACTIVE);

        // when
        SpeakerOutput result = underTest.updateSpeaker(ID_INSIDE_DATABASE, speakerInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getFirstName()).isEqualTo(speakerInput.getFirstName());
        assertThat(result.getLastName()).isEqualTo(speakerInput.getLastName());
        assertThat(result.getPhoneNumber()).isEqualTo(speakerInput.getPhoneNumber());
        assertThat(result.getEmail()).isEqualTo(speakerInput.getEmail());
        assertThat(result.getBio()).isEqualTo(speakerInput.getBio());
        assertThat(result.getStatus()).isEqualTo(speakerInput.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when speaker given to update doesn't exists in database")
    void exceptionWhenSpeakerNotFound() {
        //given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe",
                "123456789", "john@email.com", "My bio", SpeakerStatus.INACTIVE);

        //when
        Executable underTestLambda = () -> underTest.updateSpeaker(ID_OUTSIDE_DATABASE, speakerInput);

        //then
        SpeakerNotFoundException exception = assertThrows(SpeakerNotFoundException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(e -> e.contains("Speaker with id = " + ID_OUTSIDE_DATABASE + " was not found"));
    }
}
