package it.unimi.di.sweng.eventfinderbot.ebapi;

import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by istopped on 02/06/16.
 */
public interface IEbApi {

    List<Event> getEvents(Location location, LocalDate date);
    List<Event> getEvents(String city, LocalDate date);
    Event getEventById(String id);
}
