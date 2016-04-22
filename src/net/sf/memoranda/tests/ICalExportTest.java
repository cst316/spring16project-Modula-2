package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Vector;

import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.util.ICalExporter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ICalExportTest {
	int recurringCount;
	int nonrecurringCount;
	int exceptionDateCount;
	
	net.fortuna.ical4j.model.Calendar iCal;
	
	@Before
	public void setUp() throws Exception {
		Vector<Event> recurringEvents = EventsManager.getRepeatableEvents();
		recurringCount = recurringEvents.size();
		nonrecurringCount = EventsManager.getNonrecurringEvents().size();
		iCal = ICalExporter.getICalCalendar();
		
		exceptionDateCount = 0;
		for(int i = 0; i < recurringEvents.size(); i++) {
			if(recurringEvents.get(i).getExceptionDates().size() > 0)
				exceptionDateCount++;
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void iCalEventCountTest() {
		// Checks if the iCal object has the correct number of VEvents.
		// It should be the nonrecurring events + the recurring events + number of exception dates
		// (Because an exception date splits a recurring event in two)
		assertTrue(iCal.getComponents().size() == recurringCount+nonrecurringCount+exceptionDateCount);
	}
	
	@Test
	public void iCalEventNameTest() {
		ComponentList<CalendarComponent> iCalComponents = iCal.getComponents();
		
		// Not testing the count
		if(iCalComponents.size() == 0)
			return;
		
		// Get all the events (recurring + nonrecurring)
		Vector<Event> allEvents = new Vector<Event>();
		allEvents.addAll(EventsManager.getRepeatableEvents());
		allEvents.addAll(EventsManager.getNonrecurringEvents());
		
		// Iterate through each iCal VEvent and check if it has a corresponding Memoranda Event
		for(int i = 0; i < iCalComponents.size(); i++) {
			boolean found = false;
			String name = iCalComponents.get(i).getProperties().getProperty(Property.SUMMARY).getValue();
			
			for(int j = 0; j < allEvents.size(); j++) {
				if(allEvents.get(j).getText().equals(name)) {
					found = true;
					break;
				}
			}
			
			// Fail if it's not found
			assertTrue(found);
		}
	}
}
