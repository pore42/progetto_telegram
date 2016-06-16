package it.unimi.di.sweng.eventfinderbottests.integration;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.webhook.ConcreteConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.webhook.EventFinderBot;
import it.unimi.di.sweng.eventfinderbot.webhook.RequestProcessor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Imran on 16/06/16.
 */
public class CardFourTest {
    @Test
    public void testAddAndGetMyEvents() {


        String updateHereToday = "{\"update_id\":50926074,\n" +
                "\"message\":{\"message_id\":268,\"from\":" +
                "{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\"},\"" +
                "chat\":{\"id\":15241231,\"first_name\":\"Imran\",\"last_name\":\"Zazz\\u00e1\",\"username\":\"Zazza\",\"type\":\"private\"}," +
                "\"date\":1464962038,\"location\":{\"latitude\":40.7127840,\"longitude\":-74.0059410}}}";

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

        String getMyEventUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"\uD83D\uDCC5 Miei Eventi\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";


        EventFinderBot.destroy();
        EventFinderBot.initialize(new ConcreteConciergeFactory());

        Update update = BotUtils.parseUpdate(updateHereToday);
        RequestProcessor reqProcessor = new RequestProcessor(update);
        List<SendMessage> message = reqProcessor.process();

        if (message.get(0).getParameters().get("text").toString().contains("*Non ho trovato eventi oggi nei tuoi dintorni*"))
            assertTrue(true);
        else {
            update = BotUtils.parseUpdate(addEventUpdate);
            reqProcessor = new RequestProcessor(update);
            reqProcessor.process();

            update = BotUtils.parseUpdate(getMyEventUpdate);
            reqProcessor = new RequestProcessor(update);
            message = reqProcessor.process();

            assertTrue(message.size() > 0);
        }
    }
}
