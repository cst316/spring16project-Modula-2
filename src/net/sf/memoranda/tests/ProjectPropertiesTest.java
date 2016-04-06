package net.sf.memoranda.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.Contact;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;


public class ProjectPropertiesTest {
	
	String nameTextfield = "bob";
	String emailTextfield = "bob@place.com" ;
	String phoneNumberTextfield = "1800-888-8888";
	
	List<String> expected = Arrays.asList("bob","bob@place.com","1800-888-8888" );
	List<Contact> contact;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		CurrentProject.getContactList().addContact(
        		nameTextfield,
        		emailTextfield,
        		phoneNumberTextfield);
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void equalsTest() {
		  assertThat(CurrentProject.getContactList().getLog(), is(expected));
		
	}
}
