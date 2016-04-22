package net.sf.memoranda.tests;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

public class EventTest {
	
	//start event dates
	private CalendarDate sd1;
	private CalendarDate sd2;
	private CalendarDate sd3;
	
	//end event dates
	private CalendarDate ed1;
	private CalendarDate ed2;
	private CalendarDate ed3;
	
	//descriptions
	private String text1;
	private String text2;	
	private String text3;
	
	@Before
	public void setUpBefore() throws Exception {
		
		//start date declarations
		sd1 = new CalendarDate(10,2,2016);
		sd2 = new CalendarDate(11,2,2016);
		sd3 = new CalendarDate(10,2,2016);
		
		//end date declarations
		ed1 = new CalendarDate(10,2,2016);
		ed2 = new CalendarDate(11,2,2016);
		ed3 = new CalendarDate(10,2,2016);
		
		//text declarations
		text1 = "This is text 1!";
		text2 = "This is text 2!";
		text3 = "This is text 3!";
	}
	
	@Test
	public void testEventCreation() {
		//todo
	}
	
	@Test
	public void testEventRemoval() {
		//todo
	}
	
	@Test
	public void testEventEdit() {
		//todo
	}
	
	@Test
	public void testIsRepeatable() {
		//todo
	}
}
