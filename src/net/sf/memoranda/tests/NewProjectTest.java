package net.sf.memoranda.tests;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.ProjectDialog;
import net.sf.memoranda.util.Local;

public class NewProjectTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ProjectDialog dlg = new ProjectDialog(null, Local.getString("New project"));
		CalendarDate startDate = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate endDate = null;
        
		CalendarDate startDateExpected = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate endDateExpected = null;
        
		Project prjInjected = ProjectManager.createProject("Test", "Something About a project", startDate, endDate);
		Project prjExpected = ProjectManager.createProject("Test", "Something About a project", startDateExpected, endDateExpected);
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testNewProject(){
		
		
	}
}
