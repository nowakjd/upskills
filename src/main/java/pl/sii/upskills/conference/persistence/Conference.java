package pl.sii.upskills.conference.persistence;

import pl.sii.upskills.speech.persistence.Speech;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.STRING)
    private ConferenceStatus status;
    @Embedded
    private MoneyVO price;
    @Embedded
    private TimeSlotVO timeSlotVO;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "conference_id")
    private final List<Speech> listOfSpeeches = new ArrayList<>();

    public Conference() {
    }

    public Conference(UUID id, String name, String title, int numberOfPlaces, ConferenceStatus status, MoneyVO price,
                      TimeSlotVO timeSlotVO) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.status = status;
        this.price = price;
        this.timeSlotVO = timeSlotVO;
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

    public MoneyVO getPrice() {
        return price;
    }

    public void setPrice(MoneyVO price) {
        this.price = price;
    }

    public TimeSlotVO getTimeSlot() {
        return timeSlotVO;
    }

    public void setTimeSlot(TimeSlotVO timeSlotVO) {
        this.timeSlotVO = timeSlotVO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Conference publish() {
        setStatus(ConferenceStatus.PUBLISHED);
        return this;
    }

    public List<Speech> getListOfSpeeches() {
        return Collections.unmodifiableList(listOfSpeeches);
    }

    public void addSpeech(Speech speech) {
        listOfSpeeches.add(speech);
    }

    public void removeSpeech(Speech speech) {
        listOfSpeeches.remove(speech);
    }
}
