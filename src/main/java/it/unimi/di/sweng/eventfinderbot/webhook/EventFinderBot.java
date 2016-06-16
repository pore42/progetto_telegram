package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Imran on 02/06/16.
 */
public class EventFinderBot {

    private static EventFinderBot instance = null;
    private AbstractConciergeFactory conciergeFactory;
    private Map<Long, IConcierge> userList;

    private EventFinderBot(AbstractConciergeFactory conciergeFactory){
        this.conciergeFactory = conciergeFactory;
        this.userList = new HashMap<Long, IConcierge>();
    }

    public static synchronized void initialize(AbstractConciergeFactory conciergeFactory) {

        if (instance == null) {
            if (conciergeFactory == null) {
                throw new IllegalArgumentException();
            }
            instance = new EventFinderBot(conciergeFactory);
        }
        else {
            throw new IllegalStateException();
        }
    }

    public static synchronized EventFinderBot instance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public boolean isNewUser(long chatId){
        return !userList.containsKey(chatId);
    }

    public boolean addNewUser(long chatId) {
        if (!userList.containsKey(chatId)) {
            userList.put(chatId, this.conciergeFactory.instance());
            return true;
        }
        return false;
    }

    public Response executeCommmand(Request request){

        IConcierge userConcierge = userList.get(request.getChatId());
        if(userConcierge == null)
            throw new IllegalStateException();
        return userConcierge.execute(request);
    }

    public IConcierge getUserConcierge(long userId){
        return userList.get(userId);
    }

    public static boolean destroy(){
        instance = null;
        return true;
    }
}
