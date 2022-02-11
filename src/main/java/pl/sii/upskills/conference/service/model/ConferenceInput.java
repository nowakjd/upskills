package pl.sii.upskills.conference.service.model;

public class ConferenceInput {

    private final String name;
    private final String title;
    private final int numberOfPlaces;
    private final Money price;
    private final TimeSlot timeSlot;

    public ConferenceInput(String name, String title, int numberOfPlaces, Money price, TimeSlot timeSlot) {
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.timeSlot = timeSlot;
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

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Money getPrice() {
        return price;
    }
}
