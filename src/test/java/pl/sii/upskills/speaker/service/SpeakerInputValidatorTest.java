package pl.sii.upskills.speaker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "123456789", "", "My bio");
        boolean validate = underTest.validate(input);
        assertTrue(validate);
    }

    @Test
    @DisplayName("Validation should pass without phone number")
    void noPhone() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "", "john@email.com", "My bio");
        boolean validate = underTest.validate(input);
        assertTrue(validate);
    }

    @Test
    @DisplayName("Validation should pass without bio")
    void noBio() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "123456789", "john@email.com", "");
        boolean validate = underTest.validate(input);
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

    @Test
    @DisplayName("Validation should throw exception when last name is empty")
    void lastNameEmpty() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "", "123456789", "john@email.com", "My bio");
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, () -> {
            boolean validate = underTest.validate(input);
        });
        String expectedMessage = "Last name is required";
        if (exception.getErrors().size() == 1) {
            String actualMessage = exception.getErrors().get(0);
            assertTrue(actualMessage.contains(expectedMessage));
        } else assertEquals(1, exception.getErrors().size());
    }

    @Test
    @DisplayName("Validation should throw exception when email or phone number is empty")
    void emailAndPhoneEmpty() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "Doe", "", "", "My bio");
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, () -> {
            boolean validate = underTest.validate(input);
        });
        String expectedMessage = "Email or phone number is required";
        if (exception.getErrors().size() == 1) {
            String actualMessage = exception.getErrors().get(0);
            assertTrue(actualMessage.contains(expectedMessage));
        } else assertEquals(1, exception.getErrors().size());
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

//        String expectedFirstNameMessage = "First name is required";
//        String expectedLastNameMessage = "Last name is required";
//        String expectedPhoneOrEmailMessage = "Email or phone number is required";
//        if (exception.getErrors().size() == 3) {
//            String actualFirstNameMessage = exception.getErrors().get(0);
//            assertTrue(expectedFirstNameMessage.contains(actualFirstNameMessage));
//            String actualLastNameMessage = exception.getErrors().get(1);
//            assertTrue(expectedLastNameMessage.contains(actualLastNameMessage));
//            String actualPhoneOrEmailMessage = exception.getErrors().get(2);
//            assertTrue(expectedPhoneOrEmailMessage.contains(actualPhoneOrEmailMessage));
//        } else assertEquals(3, exception.getErrors().size());
    }

    @Test
    @DisplayName("Validation should throw exception when first and last name are empty")
    void firstNameAndLastNameAreEmpty() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("", "", "123456789", "john@email.com", "My bio");
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, () -> {
            boolean validate = underTest.validate(input);
        });
        String expectedFirstNameMessage = "First name is required";
        String expectedLastNameMessage = "Last name is required";
        if (exception.getErrors().size() == 2) {
            String actualFirstNameMessage = exception.getErrors().get(0);
            assertTrue(expectedFirstNameMessage.contains(actualFirstNameMessage));
            String actualLastNameMessage = exception.getErrors().get(1);
            assertTrue(expectedLastNameMessage.contains(actualLastNameMessage));
        } else assertEquals(2, exception.getErrors().size());
    }

    @Test
    @DisplayName("Validation should throw exception when first name and email and phone number are empty")
    void firstNameAndPhoneNumberAndEmailEmpty() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("", "Doe", "", "", "");
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, () -> {
            boolean validate = underTest.validate(input);
        });
        String expectedFirstNameMessage = "First name is required";
        String expectedPhoneOrEmailMessage = "Email or phone number is required";
        if (exception.getErrors().size() == 2) {
            String actualFirstNameMessage = exception.getErrors().get(0);
            assertTrue(expectedFirstNameMessage.contains(actualFirstNameMessage));
            String actualPhoneOrEmailMessage = exception.getErrors().get(1);
            assertTrue(expectedPhoneOrEmailMessage.contains(actualPhoneOrEmailMessage));
        } else assertEquals(2, exception.getErrors().size());
    }

    @Test
    @DisplayName("Validation should throw exception when last name and email and phone number are empty")
    void lastNameAndPhoneNumberAndEmailEmpty() {
        SpeakerInputValidator underTest = new SpeakerInputValidator();
        SpeakerInput input = new SpeakerInput("John", "", "", "", "");
        SpeakerValidationException exception = assertThrows(SpeakerValidationException.class, () -> {
            boolean validate = underTest.validate(input);
        });
        String expectedLastNameMessage = "Last name is required";
        String expectedPhoneOrEmailMessage = "Email or phone number is required";
        if (exception.getErrors().size() == 2) {
            String actualLastNameMessage = exception.getErrors().get(0);
            assertTrue(expectedLastNameMessage.contains(actualLastNameMessage));
            String actualPhoneOrEmailMessage = exception.getErrors().get(1);
            assertTrue(expectedPhoneOrEmailMessage.contains(actualPhoneOrEmailMessage));
        } else assertEquals(2, exception.getErrors().size());
    }

}
