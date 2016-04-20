package net.sf.memoranda.tests;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import net.sf.memoranda.date.CalendarDate;

public class TasksTest {
//start dates
	private CalendarDate sd1;
	private CalendarDate sd2;
	private CalendarDate sd3;
//end dates
	
	private CalendarDate ed1;
	private CalendarDate ed2;
	private CalendarDate ed3;
	
//descriptions
	private String des1;
	private String des2;	
	private String des3;
	
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
		
//end date declarations
		sd1 = new CalendarDate(10,2,2016);
		sd2 = new CalendarDate(11,2,2016);
		sd3 = new CalendarDate(10,2,2016);
	}
	public void testTaskCreation() {
		//todo
	}
	public void testTaskDeletion(){
		//todo
	}
	public void testTaskEdit(){
		//todo
	}


}
