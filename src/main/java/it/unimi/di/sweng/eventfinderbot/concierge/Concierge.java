package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Riccardo Bianchi on 02/06/16.
 */
public class Concierge implements IConcierge {

    private Response lastResponse;
	private List<Event> userEvents;

    public Concierge() { }

    @Override
    public Response execute(Request request) {

        if (request instanceof RequestHereToday) {

            Date userDate = ((RequestHereToday) request).getToday();
            Location location = ((RequestHereToday) request).getLocation();
			List<Event> content = EBApiStaticWrapper.getEvents(location, userDate);

            Response current = new Response(content, Response.ResponseType.HERE_AND_NOW, request.getChatId());
            lastResponse = current;
            return current;
        }
		else if (request instanceof RequestDetails) {

			try {
				Event event = lastResponse.getContent().get(((RequestDetails) request).getIndex());
				return new Response(Arrays.asList(event), Response.ResponseType.EVENT_DETAILS, request.getChatId());
			}
			catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		else if (request instanceof RequestAddToMyEvents) {

			try {
				Event event = lastResponse.getContent().get(((RequestAddToMyEvents) request).getIndex());
				if (userEvents == null) userEvents = new ArrayList<Event>();
				userEvents.add(event);
				return new Response(Arrays.asList(), Response.ResponseType.ADD_TO_MY_EVENTS, request.getChatId());
			}
			catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		else if (request instanceof RequestGetMyEvents) {

			List<Event> shadowCopy = (userEvents == null) ? new ArrayList<>() : new ArrayList<>(userEvents);
			return new Response(shadowCopy, Response.ResponseType.MY_EVENTS, request.getChatId());
		}

        throw new IllegalStateException();
    }

	@Override
	public ConciergeState getState() {
		return null;
	}
}