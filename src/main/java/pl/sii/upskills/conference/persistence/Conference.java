package pl.sii.upskills.conference.persistence;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
public class Conference {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(nullable = false)
    @Size(max = 250, message = "Name must not be length than 250 characters")
    private String name;
    @Column(nullable = false)
    @Size(max = 250, message = "Title must not be length than 250 characters")
    private String title;
    @Column(nullable = false)
    private int numberOfPlaces;
    @Enumerated
    private ConferenceStatus status;
    @Embedded
    private MoneyDAO price;
    @Embedded
    private TimeSlotDAO timeSlotDAO;

    public Conference() {
    }

    public Conference(UUID id, String name, String title, int numberOfPlaces, ConferenceStatus status, MoneyDAO price,
                      TimeSlotDAO timeSlotDAO) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.status = status;
        this.price = price;
        this.timeSlotDAO = timeSlotDAO;
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

    public MoneyDAO getPrice() {
        return price;
    }

    public void setPrice(MoneyDAO price) {
        this.price = price;
    }

    public TimeSlotDAO getTimeSlot() {
        return timeSlotDAO;
    }

    public void setTimeSlot(TimeSlotDAO timeSlotDAO) {
        this.timeSlotDAO = timeSlotDAO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
