package it.unimi.di.sweng.eventfinderbot.concierge;

/**
 * Created by Riccardo Bianchi on 02/06/16.
 */
public class ConciergeFactoryConcrete extends AbstractConciergeFactory {

    @Override
    public IConcierge instance() {

        return concierge;
    }

    public ConciergeFactoryConcrete() {

        concierge = new Concierge();
    }
}
