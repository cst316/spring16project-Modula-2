package net.sf.memoranda;

import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface TimeEntry {

	CalendarDate getDate();
	
	Calendar getStartTime();
	
	Calendar getEndTime();
	
	int getStartHour();
	
	int getStartMinute();
	
	int getEndHour();
	
	int getEndMinute();
	
	int getInterruptionTime();
	
	int getDeltaTime();
	
	Phase getPhase();
	
	String getComments();
	
	Element getContent();
	
}
