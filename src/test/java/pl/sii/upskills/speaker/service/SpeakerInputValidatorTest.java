package pl.sii.upskills.speaker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpeakerInputValidatorTest {

    @Test
    @DisplayName("Validation should pass")
    void happyPath() {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "My bio");

        // when
        boolean validate = underTest.validate(input);

        // then
        assertTrue(validate);
    }

    @Test
    @DisplayName("Validation should pass without email")
    void noEmail() {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "123456789", "", "My bio");

        // when
        boolean validate = underTest.validate(input);

        // then
        assertTrue(validate);
    }

    @Test
    @DisplayName("Validation should pass without phone number")
    void noPhone() {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "", "john@email.com", "My bio");

        // when
        boolean validate = underTest.validate(input);

        // then
        assertTrue(validate);
    }

    @Test
    @DisplayName("Validation should pass without bio")
    void noBio() {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "");

        // when
        boolean validate = underTest.validate(input);

        // then
        assertTrue(validate);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    @DisplayName("Validation should throw exception when first name is empty")
    void firstNameEmpty(String firstName) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput(firstName, "Doe", "123456789", "john@email.com", "My bio");

        // when
        Executable underTestLambda = () -> underTest.validate(input);

        // then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(e -> e.contains("First name is required"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "    ", "\t"})
    @DisplayName("Validation should throw exception when last name is empty")
    void lastNameEmpty(String lastName) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", lastName, "123456789", "john@email.com", "My bio");

        // when
        Executable underTestLambda = () -> underTest.validate(input);

        // then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(e -> e.contentEquals("Last name is required"));
    }

    @ParameterizedTest
    @MethodSource("twoArgsProvider")
    @DisplayName("Validation should throw exception when email or phone number is empty")
    void emailAndPhoneEmpty(String email, String phoneNumber) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", email, phoneNumber, "My bio");

        // when
        Executable underTestLambda = () -> underTest.validate(input);

        // then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(1)
                .allMatch(e -> e.contains("Email or phone number is required"));
    }

    @Test
    @DisplayName("Validation should throw exception when all fields are empty")
    void allFieldAreEmpty() {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("", "", "", "", "");

        // when
        Executable underTestLambda = () -> underTest.validate(input);

        // then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(3)
                .anyMatch(e -> e.contains("First name is required"))
                .anyMatch(e -> e.contains("Last name is required"))
                .anyMatch(e -> e.contains("Email or phone number is required"));
    }

    @ParameterizedTest
    @MethodSource("twoArgsProvider")
    @DisplayName("Validation should throw exception when first and last name are empty")
    void firstNameAndLastNameAreEmpty(String firstName, String lastName) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput(firstName, lastName, "123456789", "john@email.com", "My bio");

        //when
        Executable underTestLambda = () -> underTest.validate(input);

        //then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(2)
                .anyMatch(e -> e.contains("First name is required"))
                .anyMatch(e -> e.contains("Last name is required"));
    }

    @ParameterizedTest
    @MethodSource("threeArgsProvider")
    @DisplayName("Validation should throw exception when first name and email and phone number are empty")
    void firstNameAndPhoneNumberAndEmailEmpty(String firstName, String phoneNumber, String email) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput(firstName, "Doe", phoneNumber, email, "");

        //when
        Executable underTestLambda = () -> underTest.validate(input);

        //then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(2)
                .anyMatch(e -> e.contains("First name is required"))
                .anyMatch(e -> e.contains("Email or phone number is required"));
    }

    @ParameterizedTest
    @MethodSource("threeArgsProvider")
    @DisplayName("Validation should throw exception when last name and email and phone number are empty")
    void lastNameAndPhoneNumberAndEmailEmpty(String lastName, String phoneNumber, String email) {
        // given
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", lastName, phoneNumber, email, "");

        //when
        Executable underTestLambda = () -> underTest.validate(input);

        //then
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, underTestLambda);
        assertThat(exception.getErrors())
                .hasSize(2)
                .anyMatch(e -> e.contains("Last name is required"))
                .anyMatch(e -> e.contains("Email or phone number is required"));
    }

    private static Stream<Arguments> twoArgsProvider() {
        return Stream.of(
                arguments(" ", "    "),
                arguments(" ", "\t"),
                arguments("    ", " "),
                arguments("\t", ""),
                arguments(null, null),
                arguments(null, " ")

        );
    }

    private static Stream<Arguments> threeArgsProvider() {
        return Stream.of(
                arguments(" ", "    ", "\t"),
                arguments(" ", "\t", null),
                arguments("    ", " ", ""),
                arguments("\t", "", "   "),
                arguments(null, null, null),
                arguments(null, " ", null)

        );
    }

}
