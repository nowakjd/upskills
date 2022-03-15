package pl.sii.upskills.speaker.service.model;

import pl.sii.upskills.speaker.persistence.SpeakerStatus;

public class SpeakerInput {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String bio;
    private final SpeakerStatus status;

    public SpeakerInput(String firstName, String lastName, String phoneNumber, String email,
                        String bio, SpeakerStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
        this.status = status;
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
}
