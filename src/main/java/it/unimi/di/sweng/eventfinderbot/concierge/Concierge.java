package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Created by Riccardo Bianchi on 02/06/16.
 */
public class Concierge implements IConcierge {

    private Stack<Response> history;

    public Concierge() {

        history = new Stack<Response>();
    }

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

        history.push(current);

        return current;
    }
}