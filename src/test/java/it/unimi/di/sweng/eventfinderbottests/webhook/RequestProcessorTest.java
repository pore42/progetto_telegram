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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void testRequestStartCommand(){
        String startUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/start\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";
        Update update = BotUtils.parseUpdate(startUpdate);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(2, message.size());
    }

    @Test
    public void testNotNewUser(){

        Update update = BotUtils.parseUpdate(updateText);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        reqProcessor.process();
        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }

    @Test
    public void testRequestNotStartCommand(){
        String startUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"notstart\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(startUpdate);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(2, message.size());
    }

    @Test
    public void testInvalidUpdate(){
        String invalidUpdate =  "{ \"update_id\": 50926120, \"message\": null, \"inline_query\": null, \"chosen_inline_result\": null, \"callback_query\": null }";
        Update update = BotUtils.parseUpdate(invalidUpdate);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(0, message.size());
    }

    @Test
    public void testRequestDetails(){

        String detailsUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/details0\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(detailsUpdate);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }

    @Test
    public void testRequestDetailsInvalidEventId(){

        String detailsUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/details100\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(detailsUpdate);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }

    @Test
    public void testRequestDetailsEmptyList(){

        String detailsUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/details1\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(detailsUpdate);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }

    @Test
    public void testRequestDetailsIdFormatInvalid(){

        String detailsUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/detailsd12asd\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(detailsUpdate);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactoryEmpty());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(1, message.size());
    }

    @Test
    public void testRequestAddToMyEvents(){

        String addEventUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"/add0\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        Update update = BotUtils.parseUpdate(addEventUpdate);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new MockConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals("Fatto! Agggiunto alla tua lista.", message.get(0).getParameters().get("text").toString());
    }
}
