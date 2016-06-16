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

    public Command createDetailsCommand(Update update) {
        return new DetailsCommand(update);
    }

    public Command createAddToMyEventsCommnad(Update update) {
        return new AddToMyEventsCommand(update);
    }

    public Command createGetMyEventsCommand(Update update) {
        return new GetMyEventsCommand(update);
    }

    public Command createSearchCommand(Update update) {
        return new StartSearchCommand(update);
    }

    public Command createSendPositionCommand(Update update) {
        return new SendPositionCommand(update);
    }

    public Command createSendDateCommand(Update update) {
        return new SendDateCommand(update);
    }
}
