package it.unimi.di.sweng.eventfinderbottests.ebapi;

import it.unimi.di.sweng.eventfinderbot.ebapi.ConcreteEBApi;
import it.unimi.di.sweng.eventfinderbot.ebapi.Configs;
import it.unimi.di.sweng.eventfinderbot.ebapi.EventNotFoundException;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class EBApiTestsIntegration {

	@Test
	public void testSize() {

		IEbApi api = new ConcreteEBApi();
		Location milano = new Location(45.4654220, 9.1859240);
		List<Event> events = api.getEvents(milano, new Date());
		assertTrue(events.size() >= 0 && events.size() <= Configs.INSTANCE.EBAPI_LIMIT());
	}

	@Test
	public void testOrari() {
		IEbApi api = new ConcreteEBApi();

		Date d = new Date();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss");
		df.setTimeZone(tz);
		String data = df.format(d);

		List<Event> events = api.getEvents("milano", d);

		for (int i = 0; i < events.size(); i++)
			assertTrue(0 <= (events.get(i).getStart().compareTo(data)));

	}

	@Test
	public void testGetEventByIdReturnsNotNullEvent() throws Exception {
		IEbApi api = new ConcreteEBApi();
		Location milano = new Location(45.4654220, 9.1859240);
		Event someEvent = api.getEvents(milano, new Date()).get(Configs.INSTANCE.EBAPI_LIMIT() - 1);
		Event event = api.getEventById(someEvent.getId());
		assertTrue(event != null);
		assertEquals(someEvent.getId(), event.getId());
		assertEquals(someEvent.getName(), event.getName());
		assertEquals(someEvent.getDescription(), event.getDescription());
		assertEquals(someEvent.getStart(), event.getStart());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetEventByIdThrowsEventNotFoundException() throws Exception {
		String id = "-1";
		thrown.expect(EventNotFoundException.class);
		thrown.expectMessage(id);
		IEbApi api = new ConcreteEBApi();
		api.getEventById(id);

	}

	@Test
	public void testStartDate() throws Exception {

	}

}
