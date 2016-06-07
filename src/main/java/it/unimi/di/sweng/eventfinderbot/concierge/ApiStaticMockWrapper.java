package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Riccardo Bianchi on 03/06/16.
 */
public final class ApiStaticMockWrapper {

    private static IEbApi api;

    public static void initMock(IEbApi mock) {

        api = mock;
    }

    public static List<Event> getEvents(Location location, LocalDate date) {

        return api.getEvents(location, date);
    }
}
