package it.unimi.di.sweng.eventfinderbot.ebapi;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import it.unimi.di.sweng.eventfinderbot.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EBApiResponse {

    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    @JsonAnySetter
    public void set(String name, Object value){}
}
