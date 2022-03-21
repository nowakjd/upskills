package pl.sii.upskills.speaker.persistence;

import pl.sii.upskills.configuration.Generated;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity

public class Speaker implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    @Size(max = 50, message = "First name must not be longer than 50 characters")
    private String firstName;
    @Column(name = "last_name")
    @Size(max = 50, message = "Last name must not be longer than 50 characters")
    private String lastName;
    @Column(name = "phone_number")
    @Size(max = 20, message = "Phone number must not be longer than 20 characters")
    private String phoneNumber;
    @Column(name = "email")
    @Size(max = 50, message = "Email must not be longer than 50 characters")
    private String email;
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SpeakerStatus speakerStatus;

    public Speaker() {
    }

    public Speaker(Long id, String firstName, String lastName,
                   String phoneNumber, String email, String bio, SpeakerStatus speakerStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
        this.speakerStatus = speakerStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public SpeakerStatus getSpeakerStatus() {
        return speakerStatus;
    }

    public void setSpeakerStatus(SpeakerStatus speakerStatus) {
        this.speakerStatus = speakerStatus;
    }

    public boolean isActive() {
        return speakerStatus.equals(SpeakerStatus.ACTIVE);
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(id, speaker.id)
                && Objects.equals(firstName, speaker.firstName)
                && Objects.equals(lastName, speaker.lastName)
                && Objects.equals(phoneNumber, speaker.phoneNumber)
                && Objects.equals(email, speaker.email)
                && Objects.equals(bio, speaker.bio)
                && speakerStatus == speaker.speakerStatus;
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber, email, bio, speakerStatus);
    }
}
