package net.sf.memoranda.tests;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import net.sf.memoranda.date.CalendarDate;

public class EventTest {
//event dates
	private CalendarDate sd1;
	private CalendarDate sd2;
	private CalendarDate sd3;

	
//descriptions
	private String text1;
	private String text2;	
	private String text3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Before
	public void setUp() throws Exception {
		
//start date declarations
		sd1 = new CalendarDate(10,2,2016);
		sd2 = new CalendarDate(11,2,2016);
		sd3 = new CalendarDate(10,2,2016);
		
		
//text declarations
		text1 = "This is text 1!";
		text2 = "This text 2!";
		text3 = "This text 3!";
	}
	public void testEventCreation() {
		//todo
	}
	public void testEventRemoval(){
		//todo
	}
	public void testEventEdit(){
		//todo
	}
	public void testIsRepeatable(){
		//todo
	}


}
