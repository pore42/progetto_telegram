package it.unimi.di.sweng.eventfinderbot.model;


public abstract class Request {

    private final long chatId;

    protected Request(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }
}