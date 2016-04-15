package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface TaskPlanningEntry {

	public int getTaskNumber();
	
	public String getTaskName();
	
	public double getPlannedHours();
	
	public CalendarDate getPlannedDate();
	
	public CalendarDate getActualDate();
	
	public int getPV();
	
	public int getEV();
	
	public Element getContent();

	boolean isComplete();
}
