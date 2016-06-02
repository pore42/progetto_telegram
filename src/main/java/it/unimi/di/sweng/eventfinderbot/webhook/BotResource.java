package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import it.unimi.di.sweng.eventfinderbot.concierge.AbstractConciergeFactory;
import it.unimi.di.sweng.eventfinderbot.concierge.IConcierge;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Request;
import it.unimi.di.sweng.eventfinderbot.model.Response;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.util.List;

public class BotResource extends ServerResource {

	@Post
	public Representation update(Representation data) throws IOException {

		System.out.print("Request received\n");
		final String token = getAttribute("token");
		if (!Configs.INSTANCE.SERVER_TOKEN.equals(token)) {
			setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Worng server token");
			return null;
		}

		final Update update = BotUtils.parseUpdate(data.getText());

		if (update.updateId() == null) {
			getLogger().warning("Can't parse update, text was: \"" + data.getText() + "\"");
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Can't parse the update");
			return null;
		}

		final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);

		RequestBuilder requestBuilder = new RequestBuilder(update);
		Request request = requestBuilder.createRequest();

		long chatId = request.getChatId();
		EventFinderBot eventFinderBot = EventFinderBot.instance();
		if (eventFinderBot.isNewUser(request.getChatId())) {

			String message = "Benvenuto "+update.message().chat().firstName();
			SendMessage msg = new SendMessage(chatId, message);
			bot.execute(msg);

			eventFinderBot.addNewUser(request.getChatId());
		}

		Response response = eventFinderBot.executeCommmand(request);

		String eventMessage = "";
		SendMessage msg;
		List<Event> events = response.getContent();
		for(int i = 0; i < events.size(); i++){
			eventMessage = events.get(i).getName() + "\n" +
					"/dettagli" + i;
			msg = new SendMessage(chatId, eventMessage);
			bot.execute(msg);
		}

		return null;
	}
}
