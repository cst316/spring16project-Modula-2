package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;

import net.sf.memoranda.date.CalendarDate;

public interface DefectList {

	Project getProject();
	
	Defect getDefect(int number);
	
	int getNextDefectNumber();

    Defect createDefect(CalendarDate datefound, int number, String type, String injection, long approximatefixtime, long fixtime,
			CalendarDate datefixed, String fixremoval, String fixreference, String description, boolean isCompleted);

    void removeDefect(Defect defect);
    
    void removeDefect(int number);
    
	public Collection getAllDefects();
	
    public Collection getActiveDefects();
    
    public long calculateTotalCompletionTimeFromDefects();
    public CalendarDate getEarliestDateFromDefects();
    public CalendarDate getEarliestDateFromActiveDefects();
    public CalendarDate getLatestDateFromDefects();
    public CalendarDate getLatestDateFromActiveDefects();
    
    nu.xom.Document getXMLContent();
}
