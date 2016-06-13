package it.unimi.di.sweng.eventfinderbot.ebapi;

import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ConcreteEBApi implements IEbApi {

	private String url;

	public ConcreteEBApi() {
		this(Configs.INSTANCE.EBAPI_ENDPOINT());
	}

	public ConcreteEBApi(String url){
    	this.url = url;
    }
	


	private GetEventsResource setupClientHttp(String requestUrl){
		final GetEventsResource ge = ClientResource.create(url + requestUrl, GetEventsResource.class);
		return ge;
	}
	
	private GetEventsResource setupClientHttps(String requestUrl) {
		final Client client = new Client(new Context(), Protocol.HTTPS);
		final ClientResource cr = new ClientResource(url + requestUrl);
		cr.setNext(client);
		final GetEventsResource ge = cr.wrap(GetEventsResource.class);
		return ge;
	}


	@Override
    public List<Event> getEvents(Location location, Date date) {

		String requestUrl = createUrl(location, date);

        final GetEventsResource ge = getClientResource(requestUrl);
        EBApiResponse response = ge.getEvents();
        List<Event> events = response.getEvents();
        
        return events.subList(0, events.size() < Configs.INSTANCE.EBAPI_LIMIT() ? events.size() : Configs.INSTANCE.EBAPI_LIMIT());
    }

	private GetEventsResource getClientResource(String requestUrl) {
		final GetEventsResource ge;
        if(url.contains("https")) 
        	ge = setupClientHttps(requestUrl);
        else
        	ge = setupClientHttp(requestUrl);
		return ge;
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




    @Override
    public List<Event> getEvents(String city, Date date) {
        return null;
    }

    @Override
    public Event getEventById(String id) {
        return null;
    }
}
