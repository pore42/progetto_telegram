package it.unimi.di.sweng.eventfinderbot.concierge;

/**
 * Created by istopped on 02/06/16.
 */
public abstract class AbstractConciergeFactory {

    protected IConcierge concierge;

    public abstract IConcierge instance();
}
