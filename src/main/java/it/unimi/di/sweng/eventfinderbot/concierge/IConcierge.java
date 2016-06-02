package it.unimi.di.sweng.eventfinderbot.concierge;

import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

/**
 * Created by istopped on 02/06/16.
 */
public interface IConcierge {

    Response execute(Request request);
}
