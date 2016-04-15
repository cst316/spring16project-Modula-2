package net.sf.memoranda;

import nu.xom.Element;

public interface TaskPlanningEntry {

	public int getTaskNumber();
	
	public String getTaskName();
	
	public int getPlannedHours();
	
	public String getPlannedDateWeek();
	
	public String getActualDateWeek();
	
	public int getPV();
	
	public int getEV();
	
	public Element getContent();
	
}
