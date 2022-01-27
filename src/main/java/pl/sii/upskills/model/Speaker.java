package pl.sii.upskills.model;

import java.util.Objects;

public class Speaker {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean active;

    public Speaker(String firstName, String lastName, String phoneNumber, String email, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
    }


    public boolean activate() {
        if (!active) {
            active = true;
            System.out.println("Speaker activated.");
            return true;
        } else {
            System.out.println("Speaker is already active.");
            return false;
        }
    }
    public boolean deactivate() {
        if (active) {
            active = false;
            System.out.println("Speaker deactivated. ");
            return true;
        } else {
            System.out.println("Speaker is already deactivated. ");
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(firstName, speaker.firstName) && Objects.equals(lastName, speaker.lastName) && Objects.equals(phoneNumber, speaker.phoneNumber) && Objects.equals(email, speaker.email) && Objects.equals(active, speaker.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, email, active);
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "name='" + firstName + '\'' +
                ", surname='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
