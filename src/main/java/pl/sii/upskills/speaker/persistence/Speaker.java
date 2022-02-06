package pl.sii.upskills.speaker.persistence;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Speaker implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", length = 50)
    //@Length(min = 0, max = 50, message ="Can not be lenght")
    @Size(max = 50, message = "Can not be lenght")
    private String firstName;
    @Column(name = "last_name", length = 50)
    @Size(max = 50, message = "last name")
    private String lastName;
    @Column(name = "phone_number", length = 20)
    @Size(max = 50, message = "phone")
    private String phoneNumber;
    @Column(name = "email", length = 50)
    @Size(max = 50, message = "email")
    private String email;
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    public Speaker() {
    }

    public Speaker(Long id, String firstName, String lastName, String phoneNumber, String email, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
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

}
