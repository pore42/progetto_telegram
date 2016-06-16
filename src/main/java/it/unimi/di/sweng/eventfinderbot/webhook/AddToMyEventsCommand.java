package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 16/06/16.
 */
public class AddToMyEventsCommand extends Command {

    public AddToMyEventsCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        RequestFactory requestFactory = new RequestFactory(update);
        Request request = requestFactory.createAddToMyEventsRequest();

        if(request == null){
            String headerMessage = "*Errore: id evento non valido!";
            super.addHeadermessage(update.message().chat().id(), headerMessage);
        }else {
            long chatId = request.getChatId();

            EventFinderBot.instance().executeCommmand(request);

            String successMessage = "Fatto! Agggiunto alla tua lista.";
            super.addHeadermessage(chatId, successMessage);
        }
        return messagesList;
    }
}
