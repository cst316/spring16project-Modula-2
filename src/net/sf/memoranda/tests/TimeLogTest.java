package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.Phase;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeLog;
import net.sf.memoranda.TimeLogImpl;
import net.sf.memoranda.date.CalendarDate;

public class TimeLogTest {

	private TimeLog log;
	private TimeEntry entry1;
	private TimeEntry entry2;
	
	@Before
	public void setUp() {
		
		log = new TimeLogImpl();
		
		// Time Entry
		//     Date         = current date
		//     Start Time   = 4:30
		//     End Time     = 5:00
		//     Interruption = 5 minutes
		//     Delta Time   = 25 minutes
		//     Phase        = Code
		//     Comments     = "testAddRemoveTimeEntry() #1"
		
		CalendarDate today = new CalendarDate();
		
		Calendar startTime = new GregorianCalendar();
		startTime.set(Calendar.HOUR, 4);
		startTime.set(Calendar.MINUTE, 30);
		
		Calendar endTime = new GregorianCalendar();
		endTime.set(Calendar.HOUR, 5);
		endTime.set(Calendar.MINUTE, 0);
		
		int interruptionTime = 5;
		Phase phase = Phase.CODE;
		String comments = "testAddRemoveTimeEntry() #1";
		
		entry1 = log.addTimeEntry(today, startTime, endTime, interruptionTime, phase, comments);
		
		// Time Entry
		//     Date         = current date
		//     Start Time   = 2:30
		//     End Time     = 4:00
		//     Interruption = 30 minutes
		//     Delta Time   = 60 minutes
		//     Phase        = Design
		//     Comments     = "testAddRemoveTimeEntry() #2"
		
		today = new CalendarDate();
		
		startTime = new GregorianCalendar();
		startTime.set(Calendar.HOUR, 2);
		startTime.set(Calendar.MINUTE, 30);
		
		endTime = new GregorianCalendar();
		endTime.set(Calendar.HOUR, 4);
		endTime.set(Calendar.MINUTE, 0);
		
		interruptionTime = 30;
		phase = Phase.DESIGN;
		comments = "testAddRemoveTimeEntry() #2";
		
		entry2 = log.addTimeEntry(today, startTime, endTime, interruptionTime, phase, comments);
	}
	
	@Test
	public void testRemoveTimeEntry() {
		
		// Remove entry #1
		int originalSize = log.size();
		log.removeTimeEntry(entry1);
		assertEquals(originalSize - 1, log.size());
		
		// Remove entry #2
		log.removeTimeEntry(entry2);
		assertEquals(originalSize - 2, log.size());
	}
	
	@Test
	public void testComputeDeltaTime() {
		assertEquals(25, entry1.getDeltaTime());
		assertEquals(60, entry2.getDeltaTime());
	}
	
	@Test
	public void testGetLog() {
		// getLog() should return a sorted list
		// Entry #2 happened before entry #1
		List<TimeEntry> list = log.getLog();
		assertEquals(list.get(0), entry2);
		assertEquals(list.get(1), entry1);
	}
	
}
