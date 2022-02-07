package pl.sii.upskills.speaker.service.query;

public class SpeakerOutput {
    private final Long Id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String bio;

    public SpeakerOutput(Long id, String firstName, String lastName, String phoneNumber, String email, String bio) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
    }

    public Long getId() {
        return Id;
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


}
