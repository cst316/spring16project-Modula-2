package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.Task;
import net.sf.memoranda.TaskListImpl;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;

public class TaskListImplTest {
	
	static TaskListImpl tasklist;
	
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
	
	//priorities
	private int pri1;
	private int pri2;
	private int pri3;
	
	//efforts
	private long ef1;
	private long ef2;
	private long ef3;
	
	
	@Before
	public void setUpBefore() throws Exception {
		tasklist = new TaskListImpl(null);
		
		//start date declarations
		sd1 = new CalendarDate(10,2,2016);
		sd2 = new CalendarDate(11,2,2016);
		sd3 = new CalendarDate(10,2,2016);
		
		//end date declarations
		ed1 = new CalendarDate(10,2,2016);
		ed2 = new CalendarDate(11,2,2016);
		ed3 = new CalendarDate(10,2,2016);
		
		//description declarations
		des1 = "This is description 1!";
		des2 = "This is description 2!";
		des3 = "This is description 3!";
		
		//priority declarations
		pri1 = 1;
		pri2 = 3;
		pri3 = 2;
		
		//effort declarations
		ef1 = Util.getMillisFromHours("2");
		ef2 = Util.getMillisFromHours("4");
		ef3 = Util.getMillisFromHours("6");
	}
	
	@After
	public void tearDownAfter() throws Exception {
		tasklist = new TaskListImpl(null);
		
		//start date declarations
		sd1 = null;
		sd2 = null;
		sd3 = null;
		
		//end date declarations
		ed1 = null;
		ed2 = null;
		ed3 = null;
		
		//description declarations
		des1 = "";
		des2 = "";
		des3 = "";
		
		//priority declarations
		pri1 = 0;
		pri2 = 0;
		pri3 = 0;
		
		//effort declarations
		ef1 = Util.getMillisFromHours("0");
		ef2 = Util.getMillisFromHours("0");
		ef3 = Util.getMillisFromHours("0");
	}
	
	@Test
	public void testTaskCreation() {
		
		assert(tasklist.getTopLevelTasks().size() == 0);
		
		Task task1 = tasklist.createTask(sd1, ed1, "Task 1", pri1, ef1, des1, null);
		
		assert(tasklist.getTopLevelTasks().size() == 1);
		assert(tasklist.getTask("Task 1") == task1);
		
		Task task2 = tasklist.createTask(sd2, ed2, "Task 2", pri2, ef2, des2, null);
		
		assert(tasklist.getTopLevelTasks().size() == 2);
		assert(tasklist.getTask("Task 2") == task2);
		
		Task task3 = tasklist.createTask(sd3, ed3, "Task 3", pri3, ef3, des3, null);
		
		assert(tasklist.getTopLevelTasks().size() == 3);
		assert(tasklist.getTask("Task 3") == task3);
	}
	
	@Test
	public void testTaskDeletion() {
		
		assert(tasklist.getTopLevelTasks().size() == 0);
		
		Task task1 = tasklist.createTask(sd1, ed1, "Task 1", pri1, ef1, des1, null);
		
		assert(tasklist.getTopLevelTasks().size() == 1);
		assert(tasklist.getTask("Task 1") == task1);
		assert(tasklist.removeTask(task1));
		
		Task task2 = tasklist.createTask(sd2, ed2, "Task 2", pri2, ef2, des2, null);
		
		assert(tasklist.getTopLevelTasks().size() == 2);
		assert(tasklist.getTask("Task 2") == task2);
		assert(tasklist.removeTask(task2));
		
		Task task3 = tasklist.createTask(sd3, ed3, "Task 3", pri3, ef3, des3, null);
		
		assert(tasklist.getTopLevelTasks().size() == 3);
		assert(tasklist.getTask("Task 3") == task3);
		assert(tasklist.removeTask(task3));
	}
	
	@Test
	public void testTaskEdit() {
		
		assert(tasklist.getTopLevelTasks().size() == 0);
		
		Task task1 = tasklist.createTask(sd1, ed1, "Task 1", pri1, ef1, des1, null);
		
		assert(tasklist.getTopLevelTasks().size() == 1);
		assert(tasklist.getTask("Task 1") == task1);
		
		task1.setStartDate(new CalendarDate("4/13/2016"));
		task1.setDescription("This is edited description 1!");
		task1.setText("Task 1 edited");
		assert(tasklist.getTask("Task 1 edited") == task1);
		
		Task task2 = tasklist.createTask(sd2, ed2, "Task 2", pri2, ef2, des2, null);
		
		assert(tasklist.getTopLevelTasks().size() == 2);
		assert(tasklist.getTask("Task 2") == task2);
		
		task2.setStartDate(new CalendarDate("5/13/2016"));
		task2.setDescription("This is edited description 2!");
		task2.setText("Task 2 edited");
		assert(tasklist.getTask("Task 2 edited") == task2);
		
		Task task3 = tasklist.createTask(sd3, ed3, "Task 3", pri3, ef3, des3, null);
		
		assert(tasklist.getTopLevelTasks().size() == 3);
		assert(tasklist.getTask("Task 3") == task3);
		
		task3.setStartDate(new CalendarDate("6/13/2016"));
		task3.setDescription("This is edited description 3!");
		task3.setText("Task 3 edited");
		assert(tasklist.getTask("Task 3 edited") == task3);
	}
}
