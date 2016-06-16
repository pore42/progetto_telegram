package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 09/06/16.
 */
public class InvalidCommand extends Command {

    private String errorMessage;
    public  InvalidCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
        this.errorMessage = "Comando non valido";
    }

    public  InvalidCommand(Update update, String errorMessage){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
        this.errorMessage = errorMessage;
    }

    @Override
    public List<SendMessage> execute() {
        long chatId = update.message().chat().id();
        SendMessage invalidCommandMessage = new SendMessage(chatId, errorMessage);
        messagesList.add(invalidCommandMessage);
        
        super.sendCommandsKeyboard(chatId);

        return messagesList;
    }

}
