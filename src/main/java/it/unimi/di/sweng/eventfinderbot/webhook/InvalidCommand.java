package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.webhook.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 09/06/16.
 */
public class InvalidCommand extends Command {

    public  InvalidCommand(Update update){
        super.update = update;
        super.messagesList = new ArrayList<SendMessage>();
    }

    @Override
    public List<SendMessage> execute() {
        long chatId = update.message().chat().id();
        SendMessage invalidCommandMessage = new SendMessage(chatId, "Comando non valido");
        messagesList.add(invalidCommandMessage);
        sendKeyboard(chatId);

        return messagesList;
    }

    private void sendKeyboard(long chatId){
        SendMessage keyboardMessage = new SendMessage(chatId, "Cosa vuoi fare?");
        keyboardMessage.replyMarkup(BotConfigs.INSTANCE.KEYBOARD_BUTTONS);
        messagesList.add(keyboardMessage);
    }
}
