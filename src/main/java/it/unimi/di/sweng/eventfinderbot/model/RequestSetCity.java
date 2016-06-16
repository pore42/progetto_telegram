package it.unimi.di.sweng.eventfinderbot.model;

/**
 * Created by istopped on 16/06/16.
 */
public class RequestSetCity extends Request {

	private String city;

	protected RequestSetCity(long chatId, String city) {
		super(chatId);
		this.city = city;
	}

	public String getCity() {
		return city;
	}
}

