package it.unimi.di.sweng.eventfinderbot.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String id;
    private String name;
    private String description;
    private String url;
    private String start;
    private String end;
    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
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

    public void setName(Map<String, String> name) {
        this.name = name.get("text");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description.get("text");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStart() {
        return start;
    }

    public void setStart(Map<String, String> start) {
        this.start = start.get("local");
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(Map<String, String> end) {
        this.end = end.get("local");
    }
}