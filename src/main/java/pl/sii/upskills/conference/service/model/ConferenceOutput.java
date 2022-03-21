package pl.sii.upskills.conference.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.ConferenceStatus;
import pl.sii.upskills.configuration.Generated;
import pl.sii.upskills.speech.service.model.SpeechOutput;

import java.util.*;

@JsonDeserialize(builder = ConferenceOutput.Builder.class)
public class ConferenceOutput {

    private final String name;
    private final String title;
    private final int numberOfPlaces;
    private final Money price;
    private final TimeSlot timeSlot;
    private final ConferenceStatus status;
    private final UUID id;
    private final Set<SpeechOutput> speeches;

    ConferenceOutput(Builder builder) {

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

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {

        private String name;
        private String title;
        private int numberOfPlaces;
        private Money price;
        private TimeSlot timeSlot;
        private ConferenceStatus status;
        private UUID id;
        private Set<SpeechOutput> speeches;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withNumberOfPlaces(int numberOfPlaces) {
            this.numberOfPlaces = numberOfPlaces;
            return this;
        }

        public Builder withPrice(Money price) {
            this.price = price;
            return this;
        }

        public Builder withTimeSlot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        public Builder withStatus(ConferenceStatus status) {
            this.status = status;
            return this;
        }

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withSpeeches(Set<SpeechOutput> speeches) {
            this.speeches = speeches;
            return this;
        }

        public ConferenceOutput build() {
            return new ConferenceOutput(this);
        }
    }
}
