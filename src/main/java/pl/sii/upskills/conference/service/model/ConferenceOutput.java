package pl.sii.upskills.conference.service.model;

public class ConferenceOutput {

    private String name;
    private String title;
    private int numberOfPlaces;
    private String value;
    private String ISOCode;
    private String startDate;
    private String endDate;

    public ConferenceOutput(String name, String title, int numberOfPlaces, String value, String ISOCode, String startDate, String endDate) {
        this.name = name;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.value = value;
        this.ISOCode = ISOCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
