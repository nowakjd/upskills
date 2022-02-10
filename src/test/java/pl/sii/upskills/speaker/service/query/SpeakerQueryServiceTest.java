package pl.sii.upskills.speaker.service.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeakerQueryServiceTest {
    private SpeakerQueryService underTest;
    private SpeakerRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SpeakerRepository.class);
        Function<Speaker, SpeakerOutput> mapper = new SpeakerOutputMapper();
        underTest = new SpeakerQueryService(mapper, repository);
    }

    @Test
    @DisplayName("Should return list from repository")
    void findAll() {
        //given
        List<Speaker> list = new ArrayList<>();

        Speaker speaker1 = new Speaker(1L, "John", "Doe", "123456789", "john@email.com", "My bio");
        list.add(speaker1);
        Speaker speaker2 = new Speaker(2L, "John", "Doe", "123456789", "john@email.com", "My bio");
        list.add(speaker2);
        Speaker speaker3 = new Speaker(3L, "John", "Doe", "123456789", "john@email.com", "My bio");
        list.add(speaker3);
        when(repository.findAll()).thenReturn(list);
        SpeakerOutput speakerOutput1 = new SpeakerOutput(1L, "John", "Doe", "123456789", "john@email.com", "My bio");
        SpeakerOutput speakerOutput2 = new SpeakerOutput(2L, "John", "Doe", "123456789", "john@email.com", "My bio");
        SpeakerOutput speakerOutput3 = new SpeakerOutput(3L, "John", "Doe", "123456789", "john@email.com", "My bio");
        //when
        List<SpeakerOutput> result = underTest.findAll();
        //then
        assertThat(result)
                .hasSize(3)
                .anyMatch(s -> s.equals(speakerOutput1))
                .anyMatch(s -> s.equals(speakerOutput2))
                .anyMatch(s -> s.equals(speakerOutput3));
    }
}