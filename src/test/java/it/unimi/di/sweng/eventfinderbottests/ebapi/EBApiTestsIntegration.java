package it.unimi.di.sweng.eventfinderbottests.ebapi;

import it.unimi.di.sweng.eventfinderbot.ebapi.ConcreteEBApi;
import it.unimi.di.sweng.eventfinderbot.ebapi.Configs;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
public class EBApiTestsIntegration {

    @Test
    public void testSize() {

        IEbApi api = new ConcreteEBApi();
        Location milano = new Location(45.4654220, 9.1859240);
        List<Event> events = api.getEvents(milano, new Date());
        assertTrue(events.size() >= 0 && events.size() <= Configs.INSTANCE.EBAPI_LIMIT());
    }
}
