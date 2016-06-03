package it.unimi.di.sweng.eventfinderbot.webhook;

public enum Configs {
	INSTANCE;

	public final int PORT;
	public final String SERVER_TOKEN;
	public final String BOT_TOKEN;

	private Configs() {
		PORT = Integer.parseInt(System.getenv("PORT"));
		SERVER_TOKEN = System.getenv("TELEGRAM_SERVER_TOKEN");
		BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
	}

}
