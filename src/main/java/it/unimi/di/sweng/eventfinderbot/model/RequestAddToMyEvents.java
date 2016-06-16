package it.unimi.di.sweng.eventfinderbot.model;

/**
 * Created by istopped on 16/06/16.
 */
public class RequestAddToMyEvents extends Request {

	private int index;

	public RequestAddToMyEvents(long chatId, int index) {
		super(chatId);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
