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

	public ConcreteEBApi(String url)
    {
    	this.url = url;
    }
	

	public ConcreteEBApi() {
		this.url = Configs.INSTANCE.EBAPI_ENDPOINT();
	}



	@Override
    public List<Event> getEvents(Location location, Date date) {

        String postfix = "&token=" + Configs.INSTANCE.EBAPI_TOKEN();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss");
        df.setTimeZone(tz);
        String endPoint = "/events/search/?location.latitude=" + location.getLatitude() +
                          "&location.longitude=" + location.getLongitude() +
                          "&start_date.range_start=" + df.format(date) +
                          "&sort_by=" + Configs.INSTANCE.EBAPI_SORT();

        final GetEventsResource ge;
        if(url.contains("https")) 
        	ge = setupClientHttps(endPoint, postfix);
        else
        	ge = setupClientHttp(endPoint, postfix);

        EBApiResponse response = ge.getEvents();
        List<Event> events = response.getEvents();
        return events.subList(0, events.size() < Configs.INSTANCE.EBAPI_LIMIT() ? events.size() : Configs.INSTANCE.EBAPI_LIMIT());
    }


	private GetEventsResource setupClientHttp(String endpoint, String posfix){
		final GetEventsResource ge = ClientResource.create(url + endpoint + posfix, GetEventsResource.class);
		return ge;
	}
	
	private GetEventsResource setupClientHttps(String endPoint, String postfix) {
		final Client client = new Client(new Context(), Protocol.HTTPS);
        final ClientResource cr = new ClientResource(url + endPoint + postfix);
        cr.setNext(client);
        final GetEventsResource ge = cr.wrap(GetEventsResource.class);
		return ge;
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
