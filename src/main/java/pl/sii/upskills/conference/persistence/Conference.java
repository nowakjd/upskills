package pl.sii.upskills.conference.persistence;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Conference {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(nullable = false,length = 250)
    private String name;
    @Column(nullable = false,length = 250)
    private String title;
    @Column(nullable = false)
    private int numberOfPlaces;
    @Enumerated
    private ConferenceStatus status;
    @Embedded
    private Money price;
    @Embedded
    private TimeSlot timeSlot;

    public Conference() {
    }

    public Conference(UUID id, String name, String title, int numberOfPlaces, ConferenceStatus status, Money price, TimeSlot timeSlot) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.status = status;
        this.price = price;
        this.timeSlot = timeSlot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public ConferenceStatus getStatus() {
        return status;
    }

    public void setStatus(ConferenceStatus status) {
        this.status = status;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
