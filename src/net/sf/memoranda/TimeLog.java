package net.sf.memoranda;

import java.util.Calendar;
import java.util.List;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public interface TimeLog {

	TimeEntry addTimeEntry(CalendarDate date,
	                       Calendar startTime,
	                       Calendar endTime,
	                       int interruptionTime,
	                       Phase phase,
	                       String comments);
	
	void removeTimeEntry(TimeEntry entry);
	
	List<TimeEntry> getLog();
	
	int size();
	
	Document getXMLContent();
	
}
