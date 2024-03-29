package pl.sii.upskills.speaker.service.model;

import pl.sii.upskills.speaker.persistence.SpeakerStatus;

import java.util.Objects;

public class SpeakerOutput {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String bio;
    private final SpeakerStatus status;

    public SpeakerOutput(Long id, String firstName, String lastName,
                         String phoneNumber, String email, String bio, SpeakerStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public SpeakerStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerOutput that = (SpeakerOutput) o;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(email, that.email)
                && Objects.equals(bio, that.bio)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber, email, bio, status);
    }
}
