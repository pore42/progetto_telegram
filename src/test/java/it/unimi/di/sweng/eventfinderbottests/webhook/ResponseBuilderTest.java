package it.unimi.di.sweng.eventfinderbottests.webhook;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import it.unimi.di.sweng.eventfinderbot.model.RequestHereToday;
import it.unimi.di.sweng.eventfinderbot.webhook.RequestBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Imran on 02/06/16.
 */
public class ResponseBuilderTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
    private static double DELTA  = .1E-10;

    @Test
    public void BuilderInstaceTest(){
        Update update = mock(Update.class);
        RequestBuilder builder = new RequestBuilder(update);
        assertNotNull(builder);
    }
    @Test
    public void BuildHereTodayTest(){

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Location location = mock(Location.class);
        Chat chat = mock(Chat.class);

        when(location.latitude()).thenReturn((float) 45.7971310);
        when(location.longitude()).thenReturn((float) 8.8827430);
        when(message.location()).thenReturn(location);
        when(chat.id()).thenReturn((long) 124567890);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        RequestBuilder builder = new RequestBuilder(update);
        RequestHereToday req = (RequestHereToday) builder.createRequest();

        assertEquals(location.latitude(), req.getLocation().getLatitude(), DELTA);
        assertEquals(location.longitude(), req.getLocation().getLongitude(), DELTA);
    }
    
    @Test
    public void BuildHereTodayNullTest(){
    	  Update update = mock(Update.class);
          Message message = mock(Message.class);
          Chat chat = mock(Chat.class);

          when(chat.id()).thenReturn((long) 124567890);
          when(message.chat()).thenReturn(chat);
          when(message.location()).thenReturn(null);
          when(update.message()).thenReturn(message);

          RequestBuilder builder = new RequestBuilder(update);
          assertNull(builder.createRequest());
         
    }
}
