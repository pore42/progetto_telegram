package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.model.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 16/06/16.
 */
public class SendPositionCommand extends Command {

    public SendPositionCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        RequestFactory requestFactory = new RequestFactory(update);
        Request request = requestFactory.createSetCityRequest();
        EventFinderBot.instance().executeCommmand(request);

        String requestPosition = "Quando? [ gg/mm/aaaa ]";
        SendMessage eventMessage = new SendMessage(update.message().chat().id(), requestPosition);
        messagesList.add(eventMessage);

        return  messagesList;
    }
}
