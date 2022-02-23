package pl.sii.upskills.conference.service.query;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceBadRequestException;
import pl.sii.upskills.conference.service.mapper.ConferenceOutputMapper;
import pl.sii.upskills.conference.service.model.ConferenceOutput;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConferenceQueryServiceTest {

    private ConferenceQueryService underTest;
    private ConferenceRepository repository;
    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);

    @BeforeEach
    void setUp() {
        repository = mock(ConferenceRepository.class);
        Function<Conference, ConferenceOutput> mapper = new ConferenceOutputMapper();
        underTest = new ConferenceQueryService(mapper, repository);
    }

    @Test
    @DisplayName("Should return list of conference drafts from repository")
    void findByStatus() {
        //given
        TimeSlotVO timeSlot = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));

        Conference conference1 = new Conference(UUID.fromString("1963c134-0141-415f-aaf6-89a502fb58bf"),
                "Conference1", "THE BEST", 50, ConferenceStatus.DRAFT,
                null, timeSlot);
        Conference conference2 = new Conference(UUID.fromString("2963c134-0141-415f-aaf6-89a502fb58bf"),
                "Conference2", "THE BEST", 50, ConferenceStatus.DRAFT,
                null, timeSlot);
        Conference conference3 = new Conference(UUID.fromString("3963c134-0141-415f-aaf6-89a502fb58bf"),
                "Conference3", "THE BEST", 50, ConferenceStatus.DRAFT,
                null, timeSlot);

        when(repository.findByStatus(ConferenceStatus.DRAFT))
                .thenReturn(Arrays.asList(conference1, conference2, conference3));

        //when
        List<ConferenceOutput> result = underTest.findByStatus(ConferenceStatus.DRAFT.name());

        //then
        assertThat(result)
                .hasSize(3)
                .anyMatch(s -> conference1.getName().equals(s.getName()))
                .anyMatch(s -> conference2.getName().equals(s.getName()))
                .anyMatch(s -> conference3.getName().equals(s.getName()))
                .allMatch(s -> ConferenceStatus.DRAFT.equals(s.getStatus()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "draft", "PUBLISHD", "DRAFt"})
    @DisplayName("should throw bad request exception when provided wrong status")
    void throwException(String inputStatus) {
        //give
        when(repository.findByStatus(any())).thenReturn(Collections.emptyList());

        //when
        ThrowableAssert.ThrowingCallable findByStatus = () -> underTest.findByStatus(inputStatus);

        //then
        assertThatThrownBy(findByStatus).isInstanceOf(ConferenceBadRequestException.class);
    }

    @Test
    @DisplayName("should return not empty list when status isn't provided")
    void statusIsNull() {
        //given
        //when(repository.findByStatus(any())).thenReturn(Collections.emptyList());

        //when
        List<ConferenceOutput> result = underTest.findByStatus(null);

        //then
        assertThat(result).isNotNull();
    }
}

