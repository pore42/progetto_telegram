package it.unimi.di.sweng.eventfinderbottests.ebapi;

import it.unimi.di.sweng.eventfinderbot.ebapi.ConcreteEBApi;
import it.unimi.di.sweng.eventfinderbot.ebapi.Configs;
import it.unimi.di.sweng.eventfinderbot.ebapi.IEbApi;
import it.unimi.di.sweng.eventfinderbot.model.Event;
import it.unimi.di.sweng.eventfinderbot.model.Location;

import org.junit.*;
import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.Method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EBApiTestsUnit {
	

	public static MockServer server;
	public static String json;
	
	@BeforeClass
	public static void prima() throws Exception
	{
		server= new MockServer(8080);
	}
	
	@AfterClass
	public static void dopo() throws Exception
	{
		server.stop();
	}

	
	private String readFromFile(String fileName) throws IOException
    {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	BufferedReader br= new BufferedReader(new FileReader(file));
    	
    	String linea;
    	StringBuilder json = new StringBuilder();
    	while( (linea=br.readLine()) != null)
    		json.append(linea);
    	
    	br.close();
    	
    	return json.toString();
    	
    	
    }
    
    
    @Test
	public void testNumeroEventiDaFile() throws Exception 
    {
    	json= readFromFile("SampleResponse.json");
    	assertEquals("pieno",json.isEmpty()? "vuoto":"pieno");
    	
    	server.setReply(Method.GET, "/events/search/", json);
    	
    	ConcreteEBApi client = new ConcreteEBApi("http://localhost:8080");
    	Location milano = new Location(45.4654220, 9.1859240);
        List<Event> events = client.getEvents(milano, new Date());
		
        assertEquals(3, events.size());
        
	}
    
    @Test
	public void testEventiControllati() throws Exception 
    {
    	json= readFromFile("SampleResponse.json");
    	assertEquals("pieno",json.isEmpty()? "vuoto":"pieno");
    	server.setReply(Method.GET, "/events/search/", json);
    	
    	ConcreteEBApi client = new ConcreteEBApi("http://localhost:8080");
    	
    	Location milano = new Location(45.4654220, 9.1859240);
        List<Event> events = client.getEvents(milano, new Date());
		
        Event ev0= events.get(0);
        Event ev1= events.get(1);
        Event ev2= events.get(2);
        
        assertEquals("Festa di fine scuola", ev0.getName());
        assertEquals("\nfiesta in un parco\n", ev0.getDescription());
        assertEquals("http://www.eventbrite.com/e/fiestadifinescuola", ev0.getUrl());
        assertEquals(1, Integer.parseInt(ev0.getId()));
        assertEquals("2016-06-10T21:30:00", ev0.getStart());
        
        assertEquals("Dinosaurs: Unextinct", ev1.getName());
        assertEquals("Dinosaurs: Unextinct", ev1.getDescription());
        assertEquals("http://www.eventbrite.com/e/dinosaurs-unextinct", ev1.getUrl());
        assertEquals(2, Integer.parseInt(ev1.getId()));
        assertEquals("2016-04-15T10:00:00", ev1.getStart());
        
        assertEquals("Safeway Open 2016", ev2.getName());
        assertEquals("The PGA TOUR returns to Silverado Resort and Spa in Napa, California!", ev2.getDescription());
        assertEquals("http://www.eventbrite.com/e/safeway-open-2016", ev2.getUrl());
        assertEquals(3, Integer.parseInt(ev2.getId()));
        assertEquals("2016-10-12T07:00:00", ev2.getStart());
        
        
    	

    	
	}
    
    
    
    
    
    
}
