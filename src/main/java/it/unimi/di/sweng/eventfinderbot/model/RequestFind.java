package it.unimi.di.sweng.eventfinderbot.model;

import java.util.Date;

/**
 * Created by istopped on 16/06/16.
 */
public class RequestFind extends Request {

	private String city;
	private Date date;

	public RequestFind(long chatId) {
		super(chatId);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
