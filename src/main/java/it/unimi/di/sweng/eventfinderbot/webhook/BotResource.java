package it.unimi.di.sweng.eventfinderbot.webhook;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.util.List;

public class BotResource extends ServerResource {

	final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);

	@Post
	public Representation update(Representation data) throws IOException {

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

		RequestProcessor requestProcessor = new RequestProcessor(update);
		List<SendMessage> messagesList = requestProcessor.process();
		for(SendMessage toSend : messagesList)
				bot.execute(toSend);

		return null;
	}
}
