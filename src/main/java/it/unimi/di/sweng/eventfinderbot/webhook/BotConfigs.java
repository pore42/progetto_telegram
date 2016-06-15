package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public enum BotConfigs {
    INSTANCE;

    public final Keyboard KEYBOARD_BUTTONS;
    public final List<String> ACCEPTED_COMMANDS;

    BotConfigs() {

        ACCEPTED_COMMANDS = new ArrayList<>();
        ACCEPTED_COMMANDS.add("/start");
        ACCEPTED_COMMANDS.add("/details");

        KEYBOARD_BUTTONS = new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton("\uD83D\uDCCD Eventi").requestLocation(true)
                }
        ).resizeKeyboard(true);
    }
}
