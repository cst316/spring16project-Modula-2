package net.sf.memoranda;

import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public interface TimeLog {

	TimeEntry createTimeEntry(CalendarDate date,
	                          Calendar startTime,
	                          Calendar endTime,
	                          int interruptionTime,
	                          int deltaTime,
	                          Phase phase,
	                          String comments);
	
	void removeTimeEntry(TimeEntry entry);
	
	Document getXMLContent();
	
}
