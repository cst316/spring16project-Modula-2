package net.sf.memoranda;

import java.util.Date;
import java.util.List;

import nu.xom.Document;

public interface TaskPlanningLog {

	public TaskPlanningEntry addTaskPlanningEntry(int taskNumber,
	                                              String taskName,
	                                              int plannedHours,
	                                              int PV,
	                                              int EV,
	                                              Date plannedDateWeek,
	                                              Date actualDateWeek);
	
	public void removeTaskPlanningEntry(TaskPlanningEntry entry);
	
	public List<TaskPlanningEntry> getLog();
	
	public int size();
	
	public Project getProject();
	
	public Document getXMLContent();
	
}
