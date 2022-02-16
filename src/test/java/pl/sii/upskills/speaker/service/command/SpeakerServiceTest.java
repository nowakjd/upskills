package pl.sii.upskills.speaker.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
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
    SpeakerCommandService underTest;
    public static final Long idOutsideDatabase = 666L;
    public static final Long idInsideDatabase = 3L;
    public static final Speaker speakerInsideDatabase =
            new Speaker(idInsideDatabase, "John", "Doe",
                    "123456789", "john@email.com", "My bio");


    @BeforeEach
    void setUp() {
        SpeakerRepository repository = mock(SpeakerRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        when(repository.findById(idOutsideDatabase)).thenReturn(Optional.empty());
        when(repository.findById(idInsideDatabase)).thenReturn(Optional.of(speakerInsideDatabase));
        SpeakerInputValidator validator = new SpeakerInputValidator();
        Function<Speaker, SpeakerOutput> outputMapper = new SpeakerOutputMapper();
        BiFunction<Speaker, SpeakerInput, Speaker> inputMapper = new SpeakerInputMapper();
        underTest = new SpeakerCommandService(repository, validator, outputMapper, inputMapper);
    }

    @Test
    @DisplayName("Should add Speaker to repository")
    void shouldAddSpeaker() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "My bio");

        // when
        SpeakerOutput result = underTest.addSpeaker(speakerInput);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getPhoneNumber()).isEqualTo("123456789");
        assertThat(result.getEmail()).isEqualTo("john@email.com");
        assertThat(result.getBio()).isEqualTo("My bio");
    }

    @Test
    @DisplayName("Should throw exception when speaker given to update doesn't exists in database")
    void exceptionWhenSpeakerNotFound() {
        //given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe",
                "123456789", "john@email.com", "My bio");

        //when
        Executable underTestLambda = () -> underTest.updateSpeaker(idOutsideDatabase, speakerInput);

        //then
        SpeakerNotFoundException exception = assertThrows(SpeakerNotFoundException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(1)
                .anyMatch(e -> e.contains("Speaker with id = " + idOutsideDatabase + " was not found"));
    }
}
