package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.service.LogService;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private final Component component;

    public Server() {

        component = new Component();
        component.getServers().add(Protocol.HTTP, Configs.INSTANCE.PORT);
        component.getDefaultHost().attachDefault(new BotApplication());
        component.setLogService(new LogService(false)); // disable access logging

        EventFinderBot.initialize(new MockConciergeFactory());
    }

    public void start() throws Exception {
        component.start();
    }

    public void stop() throws Exception {
        component.stop();
    }

    public static void main(String[] args) throws Exception {
        System.err.format("Server configured at http://localhost:%d/bot/%s\n", Configs.INSTANCE.PORT, Configs.INSTANCE.SERVER_TOKEN);
        final Server server = new Server();
        server.start();
    }

    private class MockConciergeFactory extends AbstractConciergeFactory {

        @Override
        public IConcierge instance() {

            return new IConcierge() {
                @Override
                public Response execute(Request request) {
                    List<Event> events = new ArrayList<Event>();
                    Event event = new Event("12343212", "Concerto Verdi","VIVA Verdi" , "06/06/2016", "Milan ", "verdi.jpg");
                    events.add(event);
                    return new Response(events, Response.ResponseType.HERE_AND_NOW, request.getChatId());
                }
            };
        }
    }
}
