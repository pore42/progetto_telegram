package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.RequestHereToday;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.time.ZoneId;
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

            List<Event> content = ApiStaticMockWrapper.getEvents(

                    ((RequestHereToday) request).getLocation(),
                    ((RequestHereToday) request).getToday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );

            current = new Response(content, Response.ResponseType.HERE_AND_NOW, request.getChatId());
        }

        if (current == null) throw new IllegalStateException();

        history.push(current);

        return current;
    }
}