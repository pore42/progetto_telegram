package it.unimi.di.sweng.eventfinderbot.ebapi;

import org.restlet.resource.Get;

import it.unimi.di.sweng.eventfinderbot.model.Event;

public interface GetEventByIdResource 
{
	@Get
	public Event getEventById(String id);
	
}
