package it.unimi.di.sweng.eventfinderbottests.concierge;

import it.unimi.di.sweng.eventfinderbot.concierge.EBApiStaticWrapper;
import it.unimi.di.sweng.eventfinderbot.concierge.Concierge;
import it.unimi.di.sweng.eventfinderbot.concierge.ConciergeFactoryConcrete;
import it.unimi.di.sweng.eventfinderbot.ebapi.AbstractEbApiFactory;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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
}
