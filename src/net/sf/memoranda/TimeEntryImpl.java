package net.sf.memoranda;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

public class TimeEntryImpl implements TimeEntry, Comparable<TimeEntry> {

	private Element _element = null;
	
	public TimeEntryImpl (Element elem) {
		_element = elem;
	}
	
	@Override
	public int compareTo(TimeEntry entry) {
		if (!getDate().equals(entry.getDate()))
			return getDate().before(entry.getDate()) ? -1 : 1;
		else
			return (getStartHour() * 60 + getStartMinute()) - 
			       (entry.getStartHour() * 60 + entry.getStartMinute());
	}

	@Override
	public CalendarDate getDate() {
		Attribute a = _element.getAttribute("date");
		if (a != null)
			return new CalendarDate(a.getValue());
		else
			return null;
	}

	@Override
	public Calendar getStartTime() {
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
		calendar.set(Calendar.HOUR_OF_DAY, getStartHour());
		calendar.set(Calendar.MINUTE, getStartMinute());
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	@Override
	public Calendar getEndTime() {
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
		calendar.set(Calendar.HOUR_OF_DAY, getEndHour());
		calendar.set(Calendar.MINUTE, getEndMinute());
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	@Override
	public int getInterruptionTime() {
		return new Integer(_element.getAttribute("interruptTime").getValue()).intValue();
	}

	@Override
	public int getDeltaTime() {
		return new Integer(_element.getAttribute("deltaTime").getValue()).intValue();
	}

	@Override
	public Phase getPhase() {
		String phaseName = _element.getAttribute("phase").getValue();
		for (Phase phase : Phase.values()) {
			if (phaseName == phase.toString()) {
				return phase;
			}
		}
		return null;
	}

	@Override
	public String getComments() {
		return _element.getAttribute("comments").getValue();
	}

	@Override
	public int getStartHour() {
		return new Integer(_element.getAttribute("startHour").getValue()).intValue();
	}

	@Override
	public int getStartMinute() {
		return new Integer(_element.getAttribute("startMinute").getValue()).intValue();
	}

	@Override
	public int getEndHour() {
		return new Integer(_element.getAttribute("endHour").getValue()).intValue();
	}

	@Override
	public int getEndMinute() {
		return new Integer(_element.getAttribute("endMinute").getValue()).intValue();
	}

	@Override
	public Element getContent() {
		return _element;
	}
	
}
