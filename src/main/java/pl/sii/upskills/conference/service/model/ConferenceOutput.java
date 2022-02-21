package pl.sii.upskills.conference.service.model;

import pl.sii.upskills.conference.persistence.ConferenceStatus;

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
}
