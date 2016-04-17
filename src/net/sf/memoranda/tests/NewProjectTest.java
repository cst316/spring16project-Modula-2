package net.sf.memoranda.tests;

import static org.junit.Assert.assertEquals;

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
	ProjectDialog dlg = new ProjectDialog(null, Local.getString("New project"));
	CalendarDate startDate = new CalendarDate((Date) dlg.startDate.getModel().getValue());
    CalendarDate endDate = null;
    
    ProjectDialog dlg1 = new ProjectDialog(null, Local.getString("New project"));
	CalendarDate startDateExpected = new CalendarDate((Date) dlg1.startDate.getModel().getValue());
    CalendarDate endDateExpected = null;
    
	Project prjInjected = ProjectManager.createProject("Test", "Something About a project", startDate, endDate);
	Project prjExpected = ProjectManager.createProject("Test", "Something About a project", startDateExpected, endDateExpected);
	
	@BeforeClass
	public static void setUp() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testNewProject(){
		assertEquals(prjExpected.getTitle(), prjInjected.getTitle());
		assertEquals(prjExpected.getDescription(), prjInjected.getDescription());
//		System.out.println(prjInjected.getStartDate().toString());
//		System.out.print(prjExpected.getStartDate().toString());
//		assertEquals(prjExpected.getStartDate().toString(), prjInjected.getStartDate().toString());
//		assertEquals(prjExpected.getEndDate().toString(), prjInjected.getEndDate().toString());
	}
	public void deleteProject(){
		//prjInjected.removeProject(prjInjected.getID());
	}
}
