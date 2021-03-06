package it.unimi.di.sweng.eventfinderbot.model;

import java.util.Date;

/**
 * Created by istopped on 02/06/16.
 */
public class RequestHereToday extends Request {

    private Location location;
    private Date today;

    public RequestHereToday(long chatId, Location location, Date date) {
        super(chatId);
        this.location = location;
        this.today = date;
    }

    public Location getLocation() {
        return location;
    }

    public Date getToday() {
        return today;
    }
}
