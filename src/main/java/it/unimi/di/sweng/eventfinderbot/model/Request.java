package it.unimi.di.sweng.eventfinderbot.model;


public abstract class Request {

    private final int chatId;

    protected Request(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }
}