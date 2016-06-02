package it.unimi.di.sweng.eventfinderbot.model;

public class Event {

    private String id;
    private String name;
    private String description;
    private String start;
    private String city;
    private String thumbnail;

    public Event(String id, String name, String description, String start, String city, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.city = city;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}