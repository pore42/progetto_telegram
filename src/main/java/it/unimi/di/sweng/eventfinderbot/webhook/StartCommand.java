package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 09/06/16.
 */
public class StartCommand extends Command {

    public  StartCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        long chatId = update.message().chat().id();
        String name = update.message().chat().firstName();
        SendMessage welcomeMessage = new SendMessage(chatId, "Benvenuto "+name);
        messagesList.add(welcomeMessage);
        super.sendCommandsKeyboard(chatId);
        return messagesList;
    }

}
