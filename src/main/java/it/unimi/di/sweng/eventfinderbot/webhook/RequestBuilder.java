package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.model.Location;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import it.unimi.di.sweng.eventfinderbot.model.RequestHereToday;

import java.util.Date;

/**
 * Created by Imran on 02/06/16.
 */
public class RequestBuilder {
    private Update update;
    private Message message;
    private Chat chat;

    public RequestBuilder(Update update){
        this.update = update;
    }

    public Request createRequest(){
        message = update.message();
        chat = message.chat();
        Date date = new Date(message.date());
        if(message.location() != null){
            Location location = new Location(message.location().latitude(), message.location().longitude());
            return new RequestHereToday(chat.id(), location, date);
        }
        return null;
    }
}
