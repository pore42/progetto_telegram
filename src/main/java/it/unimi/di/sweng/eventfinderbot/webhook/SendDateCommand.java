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
public class SendDateCommand extends Command {

    public SendDateCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        RequestFactory requestFactory = new RequestFactory(update);
        Request request = requestFactory.createSetDateRequest();

        long chatId = request.getChatId();

        Response response = EventFinderBot.instance().executeCommmand(request);

        String eventName = "";
        SendMessage eventMessage;
        List<Event> events = response.getContent();
        if(!events.isEmpty()){

            String headerMessage = "*Ho trovato questi eventi";
            super.addHeadermessage(chatId, headerMessage);

            int eventIndex = 0;
            for (Event event : events) {
                eventName = event.getName() + "\n";
                eventName += BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(1) + eventIndex + "\n";
                eventName += BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(2) + eventIndex;
                eventMessage = new SendMessage(chatId, eventName);
                messagesList.add(eventMessage);

                eventIndex++;
            }
        }else{
            String headerMessage = "*Non ho trovato eventi oggi nei tuoi dintorni*";
            super.addHeadermessage(chatId, headerMessage);
        }
        return messagesList;
    }
}
