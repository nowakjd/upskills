package pl.sii.upskills.speaker.service.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sii.upskills.speaker.service.model.SpeakerInput;
import pl.sii.upskills.speaker.service.model.SpeakerOutput;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpeakerServiceITTest {

    @Autowired
    SpeakerCommandService underTest;

    @Test
    void happyPath() {
        // given
        SpeakerInput speakerInput = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "My bio");

        // when
        SpeakerOutput speakerOutput = underTest.addSpeaker(speakerInput);

        // then
        assertThat(speakerOutput.getId()).isNotNull();
        assertThat(speakerOutput.getEmail()).isEqualTo(speakerInput.getEmail());
    }
}
