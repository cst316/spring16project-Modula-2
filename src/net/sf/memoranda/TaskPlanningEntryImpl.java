package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public class TaskPlanningEntryImpl implements TaskPlanningEntry {

	private Element _element = null;
	
	public TaskPlanningEntryImpl(Element elem) {
		_element = elem;
	}
	
	@Override
	public int getTaskNumber() {
		return Integer.parseInt(_element.getFirstChildElement("taskNumber").getValue());
	}

	@Override
	public String getTaskName() {
		return _element.getFirstChildElement("taskName").getValue();
	}

	@Override
	public double getPlannedHours() {
		return Double.parseDouble(_element.getFirstChildElement("plannedHours").getValue());
	}

	@Override
	public CalendarDate getPlannedDate() {
		return new CalendarDate(_element.getFirstChildElement("plannedDateWeek").getValue());
	}

	@Override
	public CalendarDate getActualDate() {
		return new CalendarDate(_element.getFirstChildElement("actualDateWeek").getValue());
	}

	@Override
	public int getPV() {
		return Integer.parseInt(_element.getFirstChildElement("plannedValue").getValue());
	}

	@Override
	public int getEV() {
		return Integer.parseInt(_element.getFirstChildElement("earnedValue").getValue());
	}
	
	@Override
	public boolean isComplete() {
		if(_element.getFirstChildElement("isComplete").getValue().equals("true"))
			return true;
		else
			return false;
	}

	@Override
	public Element getContent() {
		return _element;
	}

}
