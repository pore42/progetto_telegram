package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

/**
 * Created by Imran on 09/06/16.
 */
public abstract class Command {

    protected Update update;
    protected List<SendMessage> messagesList;

    protected void addHeadermessage(long chatId, String message) {
        SendMessage headerMessage = new SendMessage(chatId, message);
        headerMessage.parseMode(ParseMode.Markdown);
        messagesList.add(headerMessage);
    }

    protected void sendCommandsKeyboard(long chatId){
        SendMessage keyboardMessage = new SendMessage(chatId, "Cosa vuoi fare?");
        keyboardMessage.replyMarkup(BotConfigs.INSTANCE.KEYBOARD_BUTTONS);
        messagesList.add(keyboardMessage);
    }
    public abstract List<SendMessage> execute();
}
