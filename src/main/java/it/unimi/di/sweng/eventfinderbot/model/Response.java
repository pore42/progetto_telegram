package it.unimi.di.sweng.eventfinderbot.model;

import java.util.ArrayList;
import java.util.List;

public class Response {

    public enum ResponseType { HERE_AND_NOW }

    private ResponseType type;
    private long chatID;
    private List<Event> content;

    public Response(List<Event> content, ResponseType type, long chatID) {

        this.content = content;
        this.chatID = chatID;
        this.type = type;
    }

    public ArrayList<Event> getContent() {

        return new ArrayList<Event>(content);
    }
}
