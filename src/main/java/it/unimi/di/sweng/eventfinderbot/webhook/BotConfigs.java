package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public enum BotConfigs {
    INSTANCE;

    public final Keyboard KEYBOARD_BUTTONS;
    public final List<String> ACCEPTED_COMMMANDS;

    private BotConfigs() {

        ACCEPTED_COMMMANDS = new ArrayList<String>();
        ACCEPTED_COMMMANDS.add("/start");

        KEYBOARD_BUTTONS = new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton("\uD83D\uDCCD Eventi").requestLocation(true)
                }
        ).resizeKeyboard(true);
    }
}
