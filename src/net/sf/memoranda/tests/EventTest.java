package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.date.CalendarDate;

public class EventTest {
	
	//event dates
	private CalendarDate d1;
	private CalendarDate d2;
	private CalendarDate d3;
	
	//hours
	int hh1;
	int hh2;
	int hh3;
	
	//minutes
	int mm1;
	int mm2;
	int mm3;
	
	//descriptions
	private String text1;
	private String text2;
	private String text3;
	
	//events
	Event e1;
	Event e2;
	Event e3;
	
	@Before
	public void setUpBefore() throws Exception {
		
		//date declarations
		d1 = new CalendarDate(10,2,2016);
		d2 = new CalendarDate(11,2,2016);
		d3 = new CalendarDate(10,2,2016);
		
		//hour declarations
		hh1 = 2;
		hh2 = 4;
		hh3 = 16;
		
		//minute declarations
		mm1 = 23;
		mm2 = 32;
		mm3 = 20;
		
		//text declarations
		text1 = "This is text 1!";
		text2 = "This is text 2!";
		text3 = "This is text 3!";
		
		//event declarations
		e1 = EventsManager.createEvent(d1, hh1, mm1, text1);
		e2 = EventsManager.createEvent(d2, hh2, mm2, text2);
		e3 = EventsManager.createEvent(d3, hh3, mm3, text3);
	}
	
	@After
	public void setUpAfter() throws Exception {
		
		//date declarations
		d1 = null;
		d2 = null;
		d3 = null;
		
		//hour declarations
		hh1 = 0;
		hh2 = 0;
		hh3 = 0;
		
		//minute declarations
		mm1 = 0;
		mm2 = 0;
		mm3 = 0;
		
		//text declarations
		text1 = "";
		text2 = "";
		text3 = "";
		
		//event declarations
		e1 = null;
		e2 = null;
		e3 = null;
	}
	
	@Test
	public void testEventCreation() {
		
		//event 1
		assert(EventsManager.getEvent(d1, hh1, mm1) == e1);
		assert(EventsManager.getEvent(d1, hh1, mm1).getText().equals(e1.getText()));
		
		//event 2
		assert(EventsManager.getEvent(d2, hh2, mm2) == e2);
		assert(EventsManager.getEvent(d2, hh2, mm2).getText().equals(e2.getText()));
		
		//event 3
		assert(EventsManager.getEvent(d3, hh3, mm3) == e3);
		assert(EventsManager.getEvent(d3, hh3, mm3).getText().equals(e3.getText()));
	}
	
	@Test
	public void testEventRemoval() {
		
		//event 1
		EventsManager.removeEvent(e1);
		
		//event 2
		EventsManager.removeEvent(e2);
		
		//event 3
		EventsManager.removeEvent(e3);
	}
	
	@Test
	public void testEventEdit() {
		
		//event 1
		EventsManager.removeEvent(e1);
		
		//event 2
		EventsManager.removeEvent(e2);
		
		//event 3
		EventsManager.removeEvent(e3);
		
		//date declarations
		d1 = new CalendarDate(9,2,2016);
		d2 = new CalendarDate(4,3,2016);
		d3 = new CalendarDate(1,5,2016);
		
		//hour declarations
		hh1 = 5;
		hh2 = 12;
		hh3 = 9;
		
		//minute declarations
		mm1 = 59;
		mm2 = 22;
		mm3 = 17;
		
		//text declarations
		text1 = "This is text 1 edited!";
		text2 = "This is text 2 edited!";
		text3 = "This is text 3 edited!";
		
		//event declarations
		e1 = EventsManager.createEvent(d1, hh1, mm1, text1);
		e2 = EventsManager.createEvent(d2, hh2, mm2, text2);
		e3 = EventsManager.createEvent(d3, hh3, mm3, text3);
		
		//event 1
		assert(EventsManager.getEvent(d1, hh1, mm1) == e1);
		assert(EventsManager.getEvent(d1, hh1, mm1).getText().equals(e1.getText()));
		
		//event 2
		assert(EventsManager.getEvent(d2, hh2, mm2) == e2);
		assert(EventsManager.getEvent(d2, hh2, mm2).getText().equals(e2.getText()));
		
		//event 3
		assert(EventsManager.getEvent(d3, hh3, mm3) == e3);
		assert(EventsManager.getEvent(d3, hh3, mm3).getText().equals(e3.getText()));
	}
	
	@Test
	public void testIsRepeatable() {
		
	}
}
