package it.unimi.di.sweng.eventfinderbottests.webhook;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;
import it.unimi.di.sweng.eventfinderbot.webhook.EventFinderBot;
import it.unimi.di.sweng.eventfinderbot.webhook.RequestProcessor;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessorTest {

    static String updateText = "{\"update_id\":50926074,\n" +
                "\"message\":{\"message_id\":268,\"from\":" +
                "{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\"},\"" +
                "chat\":{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\",\"type\":\"private\"}," +
                "\"date\":1464962038,\"location\":{\"latitude\":45.818298,\"longitude\":8.832383}}}";

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

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

    private class MockConciergeFactoryEmpty extends AbstractConciergeFactory {

        @Override
        public IConcierge instance() {
            return new IConcierge() {
                @Override
                public Response execute(Request request) {
                    List<Event> events = new ArrayList<Event>();
                    return new Response(events, Response.ResponseType.HERE_AND_NOW, request.getChatId());
                }
            };
        }
    }

    @Test
    public void testRequestHereToday(){

        Update update = BotUtils.parseUpdate(updateText);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(2, message.size());
    }

    @Test
    public void testRequestHereTodayNotEvents(){

        Update update = BotUtils.parseUpdate(updateText);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }
}
