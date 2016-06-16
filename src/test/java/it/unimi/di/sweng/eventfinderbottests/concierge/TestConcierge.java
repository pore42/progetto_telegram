package it.unimi.di.sweng.eventfinderbottests.concierge;

import it.unimi.di.sweng.eventfinderbot.concierge.EBApiStaticWrapper;
import it.unimi.di.sweng.eventfinderbot.concierge.Concierge;
import it.unimi.di.sweng.eventfinderbot.concierge.ConciergeFactoryConcrete;
import it.unimi.di.sweng.eventfinderbot.ebapi.AbstractEbApiFactory;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.api.support.membermodification.MemberModifier;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Riccardo Bianchi on 02/06/16.
 */
public class TestConcierge {

    private static AbstractEbApiFactory apiFactory;

    @BeforeClass
    public static void init() {

        apiFactory = new AbstractEbApiFactory() {

            @Override
            public IEbApi instance() {

                return mock(IEbApi.class);
            }
        };
    }

    @Test
    public void test1() {

        IEbApi api = apiFactory.instance();
        EBApiStaticWrapper.initMock(api);

        ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
        Concierge concierge = (Concierge) factory.instance();

        RequestHereToday request = new RequestHereToday(

                123456,
                new Location(45.7782314, 8.8753254),
                new Date()
        );

        // Mocking

        when(api.getEvents(request.getLocation(), request.getToday())).thenReturn(new ArrayList<Event>());

        assertEquals(Response.ResponseType.HERE_AND_NOW, concierge.execute(request).getType());
    }

    @Test(expected = IllegalStateException.class)
    public void test2() {

        IEbApi api = apiFactory.instance();
        EBApiStaticWrapper.initMock(api);

        ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
        Concierge concierge = (Concierge) factory.instance();

        concierge.execute(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testDetails1() {

        ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
        Concierge concierge = (Concierge) factory.instance();

        RequestDetails request = new RequestDetails(

                123456,
                0
        );

        assertEquals(Response.ResponseType.EVENT_DETAILS, concierge.execute(request).getType());
    }

    @Test
    public void testDetails2() throws IllegalAccessException {

        ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
        Concierge concierge = (Concierge) factory.instance();

        RequestDetails request = new RequestDetails(

                123456,
                0
        );

        List<Event> events = new ArrayList<Event>();
        Event event = mock(Event.class);
        when(event.getId()).thenReturn("1234212");
        when(event.getName()).thenReturn("Concerto Verdi");
        when(event.getDescription()).thenReturn("VIVA Verdi");
        when(event.getStart()).thenReturn("2016-06-11T03:00:00");
        events.add(event);
        Response response = new Response(events, Response.ResponseType.EVENT_DETAILS, request.getChatId());

        MemberModifier.field(Concierge.class, "lastResponse").set(concierge, response);

        Response actualResponse = concierge.execute(request);
        assertEquals(Response.ResponseType.EVENT_DETAILS, actualResponse.getType());
        assertEquals(1, actualResponse.getContent().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddMyEvents() {

		ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
		Concierge concierge = (Concierge) factory.instance();

		RequestAddToMyEvents request = new RequestAddToMyEvents(

				123456,
				0
		);

		Response actualResponse = concierge.execute(request);
		assertEquals(Response.ResponseType.ADD_TO_MY_EVENTS, actualResponse.getType());
		assertEquals(0, actualResponse.getContent().size());
    }

	@Test
	public void testAddMyEvents2() throws IllegalAccessException {

		ConciergeFactoryConcrete factory = new ConciergeFactoryConcrete();
		Concierge concierge = (Concierge) factory.instance();

		RequestAddToMyEvents request = new RequestAddToMyEvents(

				123456,
				0
		);

		List<Event> events = new ArrayList<Event>();
		Event event = mock(Event.class);
		when(event.getId()).thenReturn("1234212");
		when(event.getName()).thenReturn("Concerto Verdi");
		when(event.getDescription()).thenReturn("VIVA Verdi");
		when(event.getStart()).thenReturn("2016-06-11T03:00:00");
		events.add(event);
		Response response = new Response(events, Response.ResponseType.EVENT_DETAILS, request.getChatId());

		MemberModifier.field(Concierge.class, "lastResponse").set(concierge, response);

		Response actualResponse = concierge.execute(request);
		assertEquals(Response.ResponseType.ADD_TO_MY_EVENTS, actualResponse.getType());
		assertEquals(0, actualResponse.getContent().size());
	}
}