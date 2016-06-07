package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.Concierge;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;

public class ConcreteConciergeFactory extends AbstractConciergeFactory {
    @Override
    public IConcierge instance() {
        return new Concierge();
    }
}
