package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Riccardo Bianchi on 02/06/16.
 */
public class Concierge implements IConcierge {

    private Response lastResponse;

    public Concierge() { }

    @Override
    public Response execute(Request request) {

        Response current = null;

        if (request instanceof RequestHereToday) {

            Date userDate = ((RequestHereToday) request).getToday();
            Location location = ((RequestHereToday) request).getLocation();
			List<Event> content = EBApiStaticWrapper.getEvents(location, userDate);

            current = new Response(content, Response.ResponseType.HERE_AND_NOW, request.getChatId());
        }

        if (current == null) throw new IllegalStateException();

        lastResponse = current;
        return lastResponse;
    }
}