package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.model.Location;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import it.unimi.di.sweng.eventfinderbot.model.RequestDetails;
import it.unimi.di.sweng.eventfinderbot.model.RequestHereToday;

import java.util.Date;

/**
 * Created by Imran on 02/06/16.
 */
public class RequestFactory {
    private Update update;
    private Message message;
    private Chat chat;

    public RequestFactory(Update update){
        this.update = update;
    }

    public Request createHereTodayRequest(){

        message = update.message();
        chat = message.chat();

        if (message.location() == null)
            return null;

        Date date = new Date(message.date());
        Location location = new Location(message.location().latitude(), message.location().longitude());
        return new RequestHereToday(chat.id(), location, date);
    }

    public Request createDetailsRequest(){
        message = update.message();
        chat = message.chat();

        long chatId = chat.id();
        int index;

        String msg = message.text();
        msg = msg.replace(BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(1),"");
        if(msg.length()> 0) {
            try {
                index = Integer.parseInt(msg);
            } catch (NumberFormatException nfe){
                return null;
            }
            if(index >=0 && index <=4)
                return new RequestDetails(chatId, index);
        }
        return null;
    }
}
