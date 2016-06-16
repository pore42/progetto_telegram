package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.ebapi.ConcreteEBApi;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import java.util.Date;
import java.util.List;

/**
 * Created by Riccardo Bianchi on 03/06/16.
 */
public final class EBApiStaticWrapper {

    private static IEbApi api = new ConcreteEBApi();

    public static void initMock(IEbApi mockApi) {
        api = mockApi;
    }

    public static List<Event> getEvents(Location location, Date date) {

        return api.getEvents(location, date);
    }
    public static List<Event> getEvents(String city, Date date) {

        return api.getEvents(city, date);
    }
}
