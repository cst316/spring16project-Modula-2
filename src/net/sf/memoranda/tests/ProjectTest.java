package net.sf.memoranda.tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.Contact;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.ProjectDialog;
import net.sf.memoranda.ui.ProjectNewTeamMemberDialog;
import net.sf.memoranda.util.Local;

public class ProjectTest {
	
	CalendarDate startDate = CalendarDate.today();
    CalendarDate endDate = null;
    
	static CalendarDate startDateExpected = null;
    CalendarDate endDateExpected = null;
    
	Project prjInjected;
	Project prjExpected;
	
	static CurrentProject teamInjected = null;
	static CurrentProject teamExpected = null;
	
	static String nameInjected = "bob";
	static String phoneNumberInjected = "18008888888";
	static String emailInjected = "bob@guy.com";
	
	static String nameExpected = "bob";
	static String phoneNumberExpected = "18008888888";
	static String emailExpected = "bob@guy.com";
	
	@BeforeClass
	public static void setUp() throws Exception {
		

		
		teamInjected.getContactList().addContact(
				nameInjected,
        		emailInjected,
        		phoneNumberInjected);
		
		teamExpected.getContactList().addContact(
				nameExpected,
        		emailExpected,
        		phoneNumberExpected);
	}
	
	List<Contact> contactsInjected = teamInjected.getContactList().getLog();
	List<Contact> contactsExpected = teamExpected.getContactList().getLog();
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUpBefore() throws Exception {
		
		prjInjected = ProjectManager.createProject("Test", "Something About a project", startDate, endDate);
		prjExpected = ProjectManager.createProject("Test", "Something About a project", startDateExpected, endDateExpected);
		teamInjected = new CurrentProject();
		teamExpected = new CurrentProject();
	}
	
	@Test
	public void testNewProject() {
		
		assertEquals(prjExpected.getTitle(), prjInjected.getTitle());
		assertEquals(prjExpected.getDescription(), prjInjected.getDescription());
		//assertEquals(prjExpected.getStartDate().toString(), prjInjected.getStartDate().toString());
	}
	
	@Test
	public void deleteProject() {
	}
	
	@Test
	public void teamMember() {
		for(Contact contactsExpected : contactsInjected ){
			contactsExpected.getName();
			contactsExpected.getEmail();
			contactsExpected.getPhone();
			contactsExpected.getContent();
		}
	}
}

