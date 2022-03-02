package pl.sii.upskills.speaker.service.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sii.upskills.speaker.persistence.Speaker;
import pl.sii.upskills.speaker.persistence.SpeakerRepository;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.sii.upskills.speaker.persistence.SpeakerStatus.ACTIVE;

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
        list.add(new Speaker(1L, "John", "Doe", "123456789", "john@email.com", "My bio", ACTIVE));
        list.add(new Speaker(2L, "John", "Doe", "123456789", "john@email.com", "My bio", ACTIVE));
        list.add(new Speaker(3L, "John", "Doe", "123456789", "john@email.com", "My bio", ACTIVE));
        when(repository.findAll()).thenReturn(list);

        //when
        List<SpeakerOutput> result = underTest.findAllSpeakers();

        //then
        assertThat(result)
                .hasSize(3)
                .anyMatch(s -> s.equals(new SpeakerOutput(1L, "John", "Doe", "123456789", "john@email.com", "My bio",
                        ACTIVE)))
                .anyMatch(s -> s.equals(new SpeakerOutput(2L, "John", "Doe", "123456789", "john@email.com", "My bio",
                        ACTIVE)))
                .anyMatch(s -> s.equals(new SpeakerOutput(3L, "John", "Doe", "123456789", "john@email.com", "My bio",
                        ACTIVE)));
    }
}