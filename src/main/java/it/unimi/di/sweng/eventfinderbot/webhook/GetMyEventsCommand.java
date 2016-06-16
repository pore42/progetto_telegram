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
public class GetMyEventsCommand extends Command {

    public GetMyEventsCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        RequestFactory requestFactory = new RequestFactory(update);
        Request request = requestFactory.createGetMyEventsRequest();

        long chatId = request.getChatId();

        Response response = EventFinderBot.instance().executeCommmand(request);

        if(response.getContent().size() > 0) {
            composeEventsMessages(chatId, response);
        }else {
            String headerMessage = "Non hai salvato nessun evento.";
            super.addHeadermessage(chatId, headerMessage);
        }

        return messagesList;
    }

    private void composeEventsMessages(long chatId, Response response) {
        String eventName = "";
        SendMessage eventMessage;
        List<Event> events = response.getContent();
        if (!events.isEmpty()) {

            for(Event event: events) {
                String shortDesc = "";
                if(event.getDescription().length() < 100 )
                    shortDesc = event.getDescription();
                else
                    shortDesc = event.getDescription().substring(0, 100);

                eventName = "Nome: " + event.getName() + "\n" +
                    "Descrizione: " + shortDesc +"\n" +
                    "Inizio: " + event.getStart() + "\n";

                eventMessage = new SendMessage(chatId, eventName);
                messagesList.add(eventMessage);
            }
        }
    }
}
