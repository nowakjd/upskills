//package pl.sii.upskills.speaker.service.command;
//
//import org.assertj.core.api.Assertions;
//import org.assertj.core.api.ThrowableAssert;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.EnumSource;
//import pl.sii.upskills.speaker.persistence.SpeakerStatus;
//
//class SpeakerStatusInputValidatorTest {
//
//    @ParameterizedTest
//    @EnumSource(SpeakerStatus.class)
//    void happyPath(SpeakerStatus speakerStatus) {
//
//        //given
//        SpeakerStatusInputValidator underTest = new SpeakerStatusInputValidator();
//
//        //when
//        ThrowableAssert.ThrowingCallable execute = () -> underTest.validateStatus(speakerStatus);
//
//        Assertions.assertThatNoException().isThrownBy(execute);
//
//    }
//
//    @Test
//    void inputIsNull() {
//        //given
//        SpeakerStatusInputValidator underTest = new SpeakerStatusInputValidator();
//
//        //when
//        ThrowableAssert.ThrowingCallable execute = () -> underTest.validateStatus(null);
//
//        Assertions.assertThatThrownBy(execute).isInstanceOf(SpeakerValidationException.class);
//    }
//}
