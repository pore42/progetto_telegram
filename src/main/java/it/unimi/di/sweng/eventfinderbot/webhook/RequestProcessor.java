package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor {

    final private Update update;
    private Message message;
    private Chat chat;

    public RequestProcessor(Update update) {
        this.update = update;
    }

    public List<SendMessage> process() {

        List<SendMessage> messagesList = new ArrayList<>();

        if (! setAndCheckMessageAndChat()) return messagesList;

        if(EventFinderBot.instance().isNewUser(chat.id()))
            addNewUser(message);

        Command command = createCommand();

        return command.execute();
    }

    private Command createCommand(){
        CommandFactory commandFactory = new CommandFactory();
        if(isHereAndNowCommand(message))
            return commandFactory.createHereTodayCommand(update);

        if(isStartCommand(message.text()))
            return commandFactory.createStartCommand(update);

        return commandFactory.createrInvalidCommand(update);

    }

    private boolean setAndCheckMessageAndChat() {
        try {
            message = update.message();
            chat = message.chat();
        }catch(NullPointerException npe){
            System.err.print(npe.getMessage());
            return false;
        }
        return true;
    }

    private void addNewUser(Message message) {
        EventFinderBot.instance().addNewUser(message.chat().id());
    }

    private boolean isHereAndNowCommand(Message message) {
        return (message.location() != null);
    }

    private boolean isStartCommand(String text) {
        return text.equals(BotConfigs.INSTANCE.ACCEPTED_COMMANDS.get(0));
    }

}
