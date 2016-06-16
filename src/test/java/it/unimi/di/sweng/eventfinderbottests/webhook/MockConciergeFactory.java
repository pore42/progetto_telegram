package it.unimi.di.sweng.eventfinderbottests.webhook;

import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.ConciergeState;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockConciergeFactory extends AbstractConciergeFactory {

    @Override
    public IConcierge instance() {
        return new IConcierge() {
            @Override
            public Response execute(Request request) {
                List<Event> events = new ArrayList<Event>();

                Event event = mock(Event.class);
                when(event.getId()).thenReturn("1234212");
                when(event.getName()).thenReturn("Concerto Verdi");
                when(event.getDescription()).thenReturn("VIVA Verdi");
                when(event.getStart()).thenReturn("2016-06-11T03:00:00");
                when(event.getUrl()).thenReturn("www.amazon.it");
                events.add(event);
                return new Response(events, Response.ResponseType.HERE_AND_NOW, request.getChatId());
            }

            @Override
            public ConciergeState getState() {
                return null;
            }
        };
    }
}