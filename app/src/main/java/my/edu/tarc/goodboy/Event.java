package my.edu.tarc.goodboy;

public class Event {
    private String eventID;
    private String eventName;
    private String eventDesc;
    private String eventDateTime;
    private String eventLocation;
    private String eventOrganiser;

    public Event(String eventID, String eventName, String eventDesc, String eventDateTime, String eventLocation, String eventOrganiser) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.eventDateTime = eventDateTime;
        this.eventLocation = eventLocation;
        this.eventOrganiser = eventOrganiser;
    }

    public Event() {
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventOrganiser() {
        return eventOrganiser;
    }

    public void setEventOrganiser(String eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
    }
}
