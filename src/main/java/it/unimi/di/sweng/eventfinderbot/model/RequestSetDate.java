package it.unimi.di.sweng.eventfinderbot.model;

import java.util.Date;

/**
 * Created by istopped on 16/06/16.
 */
public class RequestSetDate extends Request {

	private Date date;

	public RequestSetDate(long chatId, Date date) {
		super(chatId);
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
