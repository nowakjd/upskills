package pl.sii.upskills.conference.service.model;

import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.configuration.Generated;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.util.*;

public class ConferenceOutput {

    private final String name;
    private final String title;
    private final int numberOfPlaces;
    private final Money price;
    private final TimeSlot timeSlot;
    private final ConferenceStatus status;
    private final UUID id;
    private final Set<SpeechOutput> speeches;

    public ConferenceOutput(String name, String title, int numberOfPlaces,
                            Money price, TimeSlot timeSlot,
                            ConferenceStatus status, UUID id, Set<SpeechOutput> speeches) {
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.timeSlot = timeSlot;
        this.status = status;
        this.id = id;
        this.speeches = speeches;
    }

    public ConferenceOutput(ConferenceOutputBuilder builder) {

        this.name = builder.name;
        this.title = builder.title;
        this.numberOfPlaces = builder.numberOfPlaces;
        this.price = builder.price;
        this.timeSlot = builder.timeSlot;
        this.status = builder.status;
        this.id = builder.id;
        this.speeches = builder.speeches;
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

    public Set<SpeechOutput> getSpeeches() {
        return Collections.unmodifiableSet(speeches);
    }

    static ConferenceOutputBuilder conferenceOutputBuilder() {
        return new ConferenceOutputBuilder();
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
                && Objects.equals(id, that.id)
                && Objects.equals(speeches, that.speeches);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name, title, numberOfPlaces, price, timeSlot, status, id, speeches);
    }

    public static final class ConferenceOutputBuilder {

        private String name;
        private String title;
        private int numberOfPlaces;
        private Money price;
        private TimeSlot timeSlot;
        private ConferenceStatus status;
        private UUID id;
        private Set<SpeechOutput> speeches;

        public ConferenceOutputBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public ConferenceOutputBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ConferenceOutputBuilder withNumberOfPlaces(int numberOfPlaces) {
            this.numberOfPlaces = numberOfPlaces;
            return this;
        }

        public ConferenceOutputBuilder withPrice(Money price) {
            this.price = price;
            return this;
        }

        public ConferenceOutputBuilder withTimeSlot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        public ConferenceOutputBuilder withStatus(ConferenceStatus status) {
            this.status = status;
            return this;
        }

        public ConferenceOutputBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ConferenceOutputBuilder withSpeeches(Set<SpeechOutput> speeches) {
            this.speeches = speeches;
            return this;
        }

        public ConferenceOutput build() {
            return new ConferenceOutput(this);
        }
    }
}
