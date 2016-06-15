package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 15/06/16.
 */
public class DetailsCommand extends Command {
    public DetailsCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        RequestFactory requestFactory = new RequestFactory(update);
        Request request = requestFactory.createDetailsRequest();

        if(request == null){
            String headerMessage = "*Errore: id evento non valido!";
            super.addHeadermessage(update.message().chat().id(), headerMessage);
        }else {
            long chatId = request.getChatId();

            Response response = EventFinderBot.instance().executeCommmand(request);

            String detailsMessage = "";
            SendMessage eventMessage;
            List<Event> events = response.getContent();
            if (!events.isEmpty()) {

                Event event = events.get(0);

                String shortDesc = "";
                if(event.getDescription().length() < 100 )
                    shortDesc = event.getDescription();
                else
                    shortDesc = event.getDescription().substring(0, 100);

                detailsMessage = "Dettagli evento\n" +
                        "Nome: " + event.getName() + "\n" +
                        "Descrizione: " + shortDesc +"\n" +
                        "Inizio: " + event.getStart() + "\n" +
                        "Url: " + event.getUrl();

                eventMessage = new SendMessage(chatId, detailsMessage);

                messagesList.add(eventMessage);
            } else {
                String headerMessage = "*Errore: impossibile trovare dettagli evento.";
                super.addHeadermessage(chatId, headerMessage);
            }
        }
        return messagesList;
    }
}
