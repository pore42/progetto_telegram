package it.unimi.di.sweng.eventfinderbot.model;

import java.util.Date;

/**
 * Created by istopped on 02/06/16.
 */
public class RequestHereToday extends Request {

    private Location location;
    private Date today;

    public RequestHereToday(int chatId, Location location) {
        super(chatId);
        this.location = location;
        this.today = new Date();
    }
}
