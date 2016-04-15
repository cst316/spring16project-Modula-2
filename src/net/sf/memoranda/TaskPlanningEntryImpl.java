package net.sf.memoranda;

import java.util.Date;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public class TaskPlanningEntryImpl implements TaskPlanningEntry {

	private Element _element = null;
	
	public TaskPlanningEntryImpl(Element elem) {
		_element = elem;
	}
	
	@Override
	public int getTaskNumber() {
		return Integer.getInteger(_element.getFirstChildElement("taskNumber").getValue());
	}

	@Override
	public String getTaskName() {
		return _element.getFirstChildElement("taskName").getValue();
	}

	@Override
	public int getPlannedHours() {
		return Integer.getInteger(_element.getFirstChildElement("plannedHours").getValue());
	}

	@Override
	public CalendarDate getPlannedDateWeek() {
		return new CalendarDate(_element.getFirstChildElement("plannedDateWeek").getValue());
	}

	@Override
	public CalendarDate getActualDateWeek() {
		return new CalendarDate(_element.getFirstChildElement("actualDateWeek").getValue());
	}

	@Override
	public int getPV() {
		return Integer.getInteger(_element.getFirstChildElement("plannedValue").getValue());
	}

	@Override
	public int getEV() {
		return Integer.getInteger(_element.getFirstChildElement("earnedValue").getValue());
	}

	@Override
	public Element getContent() {
		return _element;
	}

}
