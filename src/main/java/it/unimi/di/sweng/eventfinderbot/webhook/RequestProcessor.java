package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor {
    final private EventFinderBot eventFinderBot = EventFinderBot.instance();

    private List<SendMessage> messagesList;
    final private Update update;
    private Message message;
    private Chat chat;

    boolean IS_NEW_USER, IS_HERE_AND_NOW_COMMAND, IS_START_COMMAND,IS_VALID_COMMAND;

    public RequestProcessor(Update update) {
        this.update = update;
    }

    public List<SendMessage> process() {

        messagesList = new ArrayList<>();

        if (! setAndCheckMessageAndChat()) return messagesList;

        setRequestType();

        if(IS_NEW_USER)
            addNewUser(message);

        if(IS_HERE_AND_NOW_COMMAND){
            Command com = new HereTodayCommand(update);
            return com.execute();
        }

        else if(IS_START_COMMAND){
            Command com = new InvalidCommand(update);
            return com.execute();
        }

        else if(!IS_VALID_COMMAND){
            Command com = new InvalidCommand(update);
            return com.execute();
        }

        return messagesList;
    }

    public void setRequestType(){
        IS_NEW_USER = eventFinderBot.isNewUser(chat.id());
        IS_HERE_AND_NOW_COMMAND = isHereAndNowCommand(message);

        if(!IS_HERE_AND_NOW_COMMAND){
            IS_START_COMMAND = isStartCommand(message.text());
            IS_VALID_COMMAND = isValidCommand(message.text());
        }
    }

    private boolean setAndCheckMessageAndChat() {
        try {
            message = update.message();
            chat = message.chat();
        }catch(NullPointerException npe){
            System.out.println("Received invalid update, message == null");
            return false;
        }
        return true;
    }

    private void addNewUser(Message message) {
        eventFinderBot.addNewUser(message.chat().id());
    }

    private boolean isHereAndNowCommand(Message message) {
        return (message.location() != null);
    }

    private boolean isStartCommand(String text) {
        return text.equals(BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(0));
    }

    private boolean isValidCommand(String text) {
        return BotConfigs.INSTANCE.ACCEPTED_COMMANDS.contains(text);
    }
}
