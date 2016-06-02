package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;

import java.util.List;

/**
 * Created by istopped on 02/06/16.
 */
public interface IConcierge {

    List<Event> execute(Request request);
}
