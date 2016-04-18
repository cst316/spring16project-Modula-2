package net.sf.memoranda;

import java.util.List;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public interface TaskPlanningLog {

	public TaskPlanningEntry addTaskPlanningEntry(int taskNumber,
	                                              String taskName,
	                                              double hours,
	                                              int PV,
	                                              int EV,
	                                              CalendarDate calendarDate,
	                                              CalendarDate calendarDate2,
	                                              boolean isComplete);
	
	public void removeTaskPlanningEntry(TaskPlanningEntry entry);
	
	public List<TaskPlanningEntry> getLog();
	
	public double getCumulativeHours(TaskPlanningEntry entry);
	
	public int getCumulativePV(TaskPlanningEntry entry);
	
	public int getCumulativeEV(TaskPlanningEntry entry);
	
	public int size();
	
	public Project getProject();
	
	public Document getXMLContent();
	
}
