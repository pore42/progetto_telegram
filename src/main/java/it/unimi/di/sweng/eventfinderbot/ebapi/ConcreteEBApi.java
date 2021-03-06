package it.unimi.di.sweng.eventfinderbot.ebapi;

import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcreteEBApi implements IEbApi {

	private String url;

	public ConcreteEBApi() {
		this(Configs.INSTANCE.EBAPI_ENDPOINT());
	}

	public ConcreteEBApi(String url){
    	this.url = url;
    }

	private ClientResource setupClientHttps(String requestUrl) {
		final Client client = new Client(new Context(), Protocol.HTTPS);
		final ClientResource cr = new ClientResource(Method.GET ,url + requestUrl);
		cr.setNext(client);
		return cr;
	}

	@Override
	public List<Event> getEvents(Location location, Date date) {
		String requestUrl = createUrl(location, date);
		return performRequest(requestUrl);
	}

	@Override
	public List<Event> getEvents(String city, Date date) {
		String requestUrl = createUrl(city, date);
		return performRequest(requestUrl);
	}

	private List<Event> performRequest(String requestUrl) {
		final GetEventsResource ge = setupClientHttps(requestUrl).wrap(GetEventsResource.class);
		EBApiResponse response = ge.getEvents();
		List<Event> events = response.getEvents();

		return events.subList(0, events.size() < Configs.INSTANCE.EBAPI_LIMIT() ? events.size() : Configs.INSTANCE.EBAPI_LIMIT());
	}

	private String getStringDate(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss");
		df.setTimeZone(tz);
		String dateString = df.format(date);
		return dateString;
	}
	
	private String createUrl(Location location, Date date) {
        String token = "&token=" + Configs.INSTANCE.EBAPI_TOKEN();
		String dateString = getStringDate(date);
		String endPoint = "/events/search/";
		String arguments = "?location.latitude=" + location.getLatitude() +
                          "&location.longitude=" + location.getLongitude() +
                          "&start_date.range_start=" + dateString +
                          "&sort_by=" + Configs.INSTANCE.EBAPI_SORT();
		return endPoint +  arguments + token;
	}

	private String createUrl(String city, Date date) {
		String token = "&token=" + Configs.INSTANCE.EBAPI_TOKEN();
		String dateString = getStringDate(date);
		String endPoint = "/events/search/";
		String arguments = "?venue.city=" + city +
	                       "&start_date.range_start=" + dateString +
	                       "&sort_by=" + Configs.INSTANCE.EBAPI_SORT();
			return endPoint +  arguments + token;
		
	}

	@Override
	public Event getEventById(String id) {
		Event event = null;
		String requestUrl = "/events/" + id + "/?token=" + Configs.INSTANCE.EBAPI_TOKEN();

		final GetEventByIdResource ge = setupClientHttps(requestUrl).wrap(GetEventByIdResource.class);
		try {
			event = ge.getEventById(id);
		} catch (ResourceException e) {
			if (e.getStatus().equals(Status.CLIENT_ERROR_NOT_FOUND))
				throw new EventNotFoundException("Event with id " + id + " not found");
			Logger.getGlobal().log(Level.WARNING, "exception in getEventById", e);
		}
		return event;
	}
}
