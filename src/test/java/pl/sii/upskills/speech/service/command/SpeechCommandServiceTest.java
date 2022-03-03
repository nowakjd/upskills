package pl.sii.upskills.speech.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.ConferenceRepository;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.conference.service.command.ConferenceDraftNotFoundException;
import pl.sii.upskills.speaker.service.mapper.SpeakerOutputMapper;
import pl.sii.upskills.speaker.service.query.SpeakerQueryService;
import pl.sii.upskills.speech.persistence.Speech;
import pl.sii.upskills.speech.persistence.SpeechRepository;
import pl.sii.upskills.speech.service.mapper.SpeechMapper;
import pl.sii.upskills.speech.service.mapper.SpeechOutputMapper;
import pl.sii.upskills.speech.service.model.SpeakersForSpeechInput;
import pl.sii.upskills.speech.service.model.SpeechInput;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeechCommandServiceTest {

    private static final LocalDateTime NOW_FOR_TEST =
            LocalDateTime.of(2020, 1, 1, 0, 1);
    private static final TimeSlotVO CORRECT_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1).plusSeconds(1), NOW_FOR_TEST.plusDays(1).plusHours(2));
    private static final TimeSlotVO CONFERENCE_TIMESLOT
            = new TimeSlotVO(NOW_FOR_TEST.plusDays(1), NOW_FOR_TEST.plusDays(2));
    private static final UUID ID_OF_DRAFT_IN_DATABASE =
            UUID.fromString("0163c134-0141-415f-aaf6-89a502fb58bf");
    private static final UUID ID_OUTSIDE_DATABASE =
            UUID.fromString("0063c134-0141-415f-aaf6-89a502fb58bf");
    private SpeechCommandService underTest;

    @BeforeEach
    void setUp() {
        SpeechRepository repository = mock(SpeechRepository.class);
        when(repository.save(any())).thenAnswer(a -> a.getArgument(0));
        ConferenceRepository conferenceRepository = mock(ConferenceRepository.class);
        when(conferenceRepository.findById(ID_OF_DRAFT_IN_DATABASE)).thenReturn(Optional.of(
                new Conference(ID_OF_DRAFT_IN_DATABASE, "name", "title", 100,
                        ConferenceStatus.DRAFT, null, CONFERENCE_TIMESLOT)));
        when(conferenceRepository.findById(ID_OUTSIDE_DATABASE)).thenReturn(Optional.empty());
        SpeechInputValidator speechInputValidator = new SpeechInputValidator(() -> NOW_FOR_TEST);
        SpeechOutputMapper outputMapper = new SpeechOutputMapper(new SpeakerOutputMapper());
        BiFunction<Speech, SpeechInput, Speech> inputMapper = new SpeechMapper();
        SpeakerQueryService speakerQueryService = mock(SpeakerQueryService.class);
        underTest = new SpeechCommandService(repository, conferenceRepository, speechInputValidator,
                outputMapper, speakerQueryService);
    }

    @Test
    @DisplayName("Should add speech")
    void addSpeech() {
        //given
        SpeechInput speechInput = new SpeechInput("Swimming with boots on ?", CORRECT_TIMESLOT);

        //when
        SpeechOutput result = underTest.createSpeech(ID_OF_DRAFT_IN_DATABASE, speechInput);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(speechInput.getTitle());
        assertThat(result.getTimeSlot()).isEqualTo(speechInput.getTimeSlot());
    }

    @DisplayName("Should throw exception when conference is not found in database")
    @Test
    void noConference() {
        //given
        SpeechInput speechInput = new SpeechInput("Swimming with boots on ?", CORRECT_TIMESLOT);

        //when
        Executable lambdaUnderTest = () -> underTest.createSpeech(ID_OUTSIDE_DATABASE, speechInput);

        //then
        assertThrows(ConferenceDraftNotFoundException.class, lambdaUnderTest);
    }

    @DisplayName("Should throw exception when speech is not found in database while adding speakers")
    @Test
    void noSpeech() {
        //given
        SpeakersForSpeechInput speakersForSpeechInput = new SpeakersForSpeechInput(
                new TreeSet<Long>(Arrays.asList(1L, 2L, 3L)));

        //when
        Executable lambdaUnderTest = () -> underTest.addSpeakers(ID_OF_DRAFT_IN_DATABASE, 223L,
                speakersForSpeechInput);

        //then
        assertThrows(SpeechNotFoundException.class, lambdaUnderTest);
    }

}