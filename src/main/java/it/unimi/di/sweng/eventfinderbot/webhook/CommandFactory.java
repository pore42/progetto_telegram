package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.Update;

/**
 * Created by Imran on 09/06/16.
 */
public class CommandFactory {

    public HereTodayCommand createHereTodayCommand(Update update){
        return new HereTodayCommand(update);
    }

    public InvalidCommand createrInvalidCommand(Update update){
        return new InvalidCommand(update);
    }

    public StartCommand createStartCommand(Update update){
        return new StartCommand(update);
    }

    public Command createDetailsCommad(Update update) {
        return new DetailsCommand(update);
    }
}
