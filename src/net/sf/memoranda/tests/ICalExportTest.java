package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Vector;

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
		assertTrue(iCal.getComponents().size() == recurringCount+nonrecurringCount+exceptionDateCount);
	}
}
