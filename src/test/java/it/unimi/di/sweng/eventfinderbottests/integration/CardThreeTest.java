package it.unimi.di.sweng.eventfinderbottests.integration;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.concierge.ConciergeFactoryConcrete;
import it.unimi.di.sweng.eventfinderbot.webhook.EventFinderBot;
import it.unimi.di.sweng.eventfinderbot.webhook.RequestProcessor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Imran on 16/06/16.
 */
public class CardThreeTest {
    @Test
    public void testRequestSearch(){

        String searchUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"\uD83D\uDD0E Cerca Eventi\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";


        String positionUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"Varese\", " +
                "\"entities\": [ { \"type\": \"bot_command\", \"offset\": 0, \"length\": 6, " +
                "\"url\": \"\\\"null\\\"\" } ], \"audio\": null, \"document\": null, \"photo\": null, " +
                "\"sticker\": null, \"video\": null, \"voice\": null, \"caption\": \"\\\"null\\\"\", " +
                "\"contact\": null, \"location\": null, \"venue\": null, \"new_chat_member\": null, " +
                "\"left_chat_member\": null, \"new_chat_title\": \"\\\"null\\\"\", \"new_chat_photo\": null," +
                " \"delete_chat_photo\": null, \"group_chat_created\": null, \"supergroup_chat_created\": null, " +
                "\"channel_chat_created\": null, \"migrate_to_chat_id\": null, \"migrate_from_chat_id\": null, " +
                "\"pinned_message\": null }, \"inline_query\": null, \"chosen_inline_result\": null, " +
                "\"callback_query\": null }";

        String dateUpdate = "{ \"update_id\": 50926117, \"message\": { \"message_id\": 381, \"from\": { \"id\": 15241231, " +
                "\"first_name\": \"Imran\", \"last_name\": \"Zazzá\", \"username\": \"Zazza\" }, \"date\": 1464977821," +
                " \"chat\": { \"id\": 15241231, \"type\": \"private\", \"first_name\": \"Imran\", \"last_name\": " +
                "\"Zazzá\", \"username\": \"Zazza\", \"title\": \"\\\"null\\\"\" }, \"forward_from\": null, " +
                "\"forward_from_chat\": null, \"forward_date\": null, \"reply_to_message\": null, \"text\": \"16/06/2016\", " +
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

        EventFinderBot.initialize(new ConciergeFactoryConcrete());

        Update update = BotUtils.parseUpdate(searchUpdate);
        RequestProcessor reqProcessor = new RequestProcessor(update);
        reqProcessor.process();

        update = BotUtils.parseUpdate(positionUpdate);
        reqProcessor = new RequestProcessor(update);
        reqProcessor.process();

        update = BotUtils.parseUpdate(dateUpdate);
        reqProcessor = new RequestProcessor(update);
        List<SendMessage> message = reqProcessor.process();
//
        assertTrue(message.size() > 0);
    }
}
