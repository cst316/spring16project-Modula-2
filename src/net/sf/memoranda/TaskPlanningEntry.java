package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface TaskPlanningEntry {

	public int getTaskNumber();
	
	public String getTaskName();
	
	public int getPlannedHours();
	
	public CalendarDate getPlannedDateWeek();
	
	public CalendarDate getActualDateWeek();
	
	public int getPV();
	
	public int getEV();
	
	public Element getContent();
	
}
