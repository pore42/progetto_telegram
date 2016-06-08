package it.unimi.di.sweng.eventfinderbot.ebapi;

import org.restlet.resource.Get;

public interface GetEventsResource {

    @Get
    EBApiResponse getEvents();
}
