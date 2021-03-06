package it.unimi.di.sweng.eventfinderbottests.integration;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.webhook.ConcreteConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.webhook.EventFinderBot;
import it.unimi.di.sweng.eventfinderbot.webhook.RequestProcessor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardOneTests {

    @Test
    public void testRequestHereToday() {

        String updateText = "{\"update_id\":50926074,\n" +
                "\"message\":{\"message_id\":268,\"from\":" +
                "{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\"},\"" +
                "chat\":{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\",\"type\":\"private\"}," +
                "\"date\":1464962038,\"location\":{\"latitude\":45.818298,\"longitude\":8.832383}}}";

        Update update = BotUtils.parseUpdate(updateText);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new ConcreteConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertTrue(message.size() >= 1 && message.size() <= 6);
    }

    @Test
    public void testNoneFounds() {

        String noneText = "{\"update_id\":50926074,\n" +
                "\"message\":{\"message_id\":268,\"from\":" +
                "{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\"},\"" +
                "chat\":{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\",\"type\":\"private\"}," +
                "\"date\":1464962038,\"location\":{\"latitude\":-59,\"longitude\":101}}}"; // Speriamo che nel mezzo dell'oceano antartico non ci sia niente

        Update update = BotUtils.parseUpdate(noneText);
        EventFinderBot.destroy();
        EventFinderBot.initialize(new ConcreteConciergeFactory());
        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertTrue(message.size() == 1);
    }

    @Test
    public void testRequestStartCommand(){
        String startText = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
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

        Update update = BotUtils.parseUpdate(startText);

        EventFinderBot.destroy();
        EventFinderBot.initialize(new ConcreteConciergeFactory());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(2, message.size());
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
        EventFinderBot.initialize(new ConcreteConciergeFactory());

        RequestProcessor reqProcessor = new RequestProcessor(update);

        List<SendMessage> message = reqProcessor.process();
        assertEquals(2, message.size());
    }
}
