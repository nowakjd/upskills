package pl.sii.upskills.conference.service.model;

import pl.sii.upskills.Generated;
import pl.sii.upskills.conference.persistence.ConferenceStatus;

import java.util.Objects;
import java.util.UUID;

public class ConferenceOutput {

    private final String name;
    private final String title;
    private final int numberOfPlaces;
    private final Money price;
    private final TimeSlot timeSlot;
    private final ConferenceStatus status;
    private final UUID id;

    public ConferenceOutput(String name, String title, int numberOfPlaces,
                            Money price, TimeSlot timeSlot,
                            ConferenceStatus status, UUID id) {
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.timeSlot = timeSlot;
        this.status = status;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public Money getPrice() {
        return price;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public ConferenceStatus getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConferenceOutput that)) return false;
        return numberOfPlaces == that.numberOfPlaces
                && Objects.equals(name, that.name)
                && Objects.equals(title, that.title)
                && Objects.equals(price, that.price)
                && Objects.equals(timeSlot, that.timeSlot)
                && status == that.status
                && Objects.equals(id, that.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name, title, numberOfPlaces, price, timeSlot, status, id);
    }
}
