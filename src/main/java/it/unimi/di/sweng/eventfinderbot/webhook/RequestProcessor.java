package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor {
    final private EventFinderBot eventFinderBot = EventFinderBot.instance();

    private List<SendMessage> messagesList;
    final Update update;

    public RequestProcessor(Update update) {
        this.update = update;
    }

    public List<SendMessage> process() {

        messagesList = new ArrayList<SendMessage>();
        Message message = update.message();
        Chat chat = message.chat();

        final boolean IS_NEW_USER = eventFinderBot.isNewUser(chat.id());
        final boolean IS_HERE_AND_NOW_COMMAND = isHereAndNowCommand(message);

        if(IS_NEW_USER)
            addNewUser(message);

        if(IS_HERE_AND_NOW_COMMAND){
            RequestBuilder requestBuilder = new RequestBuilder(update);
            Request request = requestBuilder.createRequest();

            long chatId = request.getChatId();

            Response response = eventFinderBot.executeCommmand(request);

            String eventName = "";
            SendMessage eventMessage;
            List<Event> events = response.getContent();
            if(!events.isEmpty()){

                String headerMessage = "*Eventi in programma oggi nei tuoi dintorni*";
                addHeadermessage(chatId, headerMessage);

                for (Event event : events) {
                    eventName = event.getName();
                    eventMessage = new SendMessage(chatId, eventName);
                    messagesList.add(eventMessage);
                }
            }else{
                String headerMessage = "*Non ho trovato eventi oggi nei tuoi dintorni*";
                addHeadermessage(chatId, headerMessage);
            }
            return messagesList;
        }

        return messagesList;
    }

    private void addNewUser(Message message) {
        eventFinderBot.addNewUser(message.chat().id());
    }

    private boolean isHereAndNowCommand(Message message) {
        return (message.location() != null);
    }

    private void addHeadermessage(long chatId, String message) {
        SendMessage headerMessage = new SendMessage(chatId, message);
        headerMessage.parseMode(ParseMode.Markdown);
        messagesList.add(headerMessage);
    }
}
