package it.unimi.di.sweng.eventfinderbottests.webhook;

import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.*;
import it.unimi.di.sweng.eventfinderbot.model.Location;
import it.unimi.di.sweng.eventfinderbot.webhook.EventFinderBot;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EventFinderBotTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

    private class MockConciergeFactory extends AbstractConciergeFactory {

        @Override
        public IConcierge instance() {
            return new IConcierge() {
                @Override
                public Response execute(Request request) {
                    List<Event> events = new ArrayList<Event>();
                    Event event = new Event("12343212", "Concerto Verdi","VIVA Verdi" , "06/06/2016", "Milan ", "verdi.jpg");
                    events.add(event);
                    return new Response(events, Response.ResponseType.HERE_AND_NOW, request.getChatId());
                }
            };
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowsExcpetion(){
        EventFinderBot.destroy();
        EventFinderBot.instance();
    }

    @Test
    public void testInitialize(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();

        assertNotNull(efb);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testInitializeAgain(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot.initialize(new MockConciergeFactory());
        
    }    
    
    @Test(expected = IllegalArgumentException.class)
    public void testInitializeWithoutFactory(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(null);
      
    }


    @Test
    public void addUserTest(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();
        assertTrue(efb.addNewUser(123));
    }

    @Test
    public void isNewUserTest(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();
        efb.addNewUser(123);
        assertFalse(efb.isNewUser(123));
    }
    
    @Test
    public void isNewUser2Test(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();
        assertTrue(efb.isNewUser(123));
    }

    @Test
    public void executeCommmandTest(){

        Location location = mock(Location.class);
        when(location.getLatitude()).thenReturn((double) 45.7971310);
        when(location.getLongitude()).thenReturn((double) 8.8827430);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();
        efb.addNewUser(123);

        Request req = new RequestHereToday(123,location);
        Response res = efb.executeCommmand(req);

        assertEquals(Response.ResponseType.HERE_AND_NOW, res.getType());
    }
    
    @Test(expected = IllegalStateException.class)
    public void executeCommmandWithoutConciergeTest(){

        Location location = mock(Location.class);
        when(location.getLatitude()).thenReturn((double) 45.7971310);
        when(location.getLongitude()).thenReturn((double) 8.8827430);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();

        Request req = new RequestHereToday(123,location);
        efb.executeCommmand(req);
    }
    
    @Test
    public void addedUserTest(){
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        EventFinderBot efb = EventFinderBot.instance();
        efb.addNewUser(123);
        assertFalse(efb.addNewUser(123));
    }
}
