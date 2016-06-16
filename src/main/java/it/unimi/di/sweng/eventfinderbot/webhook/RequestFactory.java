package it.unimi.di.sweng.eventfinderbot.webhook;

import it.unimi.di.sweng.eventfinderbot.model.*;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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

    public Request createAddToMyEventsRequest(){
        message = update.message();
        chat = message.chat();

        long chatId = chat.id();
        int index;

        String msg = message.text();
        msg = msg.replace(BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(2),"");
        if(msg.length()> 0) {
            try {
                index = Integer.parseInt(msg);
            } catch (NumberFormatException nfe){
                return null;
            }
            if(index >=0 && index <=4)
                return new RequestAddToMyEvents(chatId, index);
        }
        return null;
    }

    public Request createGetMyEventsRequest() {
        chat = update.message().chat();
        return new RequestGetMyEvents(chat.id());
    }

    public Request createStartFindRequest() {
        return new RequestStartFind(update.message().chat().id());
    }

    public Request createSetCityRequest() {
        message = update.message();
        return new RequestSetCity(message.chat().id(), message.text());
    }

    public Request createSetDateRequest() {
        message = update.message();

        SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(message.text());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new RequestSetDate(message.chat().id(), date);
    }
}
