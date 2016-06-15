package it.unimi.di.sweng.eventfinderbot.model;

public class RequestDetails extends Request {

    private int index;

    public RequestDetails(long chatId, int index) {
        super(chatId);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}