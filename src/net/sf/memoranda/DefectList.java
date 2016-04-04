package net.sf.memoranda;

import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;

public interface DefectList {

	Project getProject();
	
	Defect getDefect(String thisDefectId);
	
	int getNextDefectNumber();

    Defect createDefect(CalendarDate datefound, String number, String type, String injection, long approximatefixtime, long fixtime,
			CalendarDate datefixed, String fixremoval, String fixreference, String description, boolean isCompleted);

    void removeDefect(Defect defect);
    
    //void removeDefect(String number);
    
	public Collection getAllDefects();
	
    public Collection getActiveDefects(String defectId, CalendarDate date);
    
    public long calculateTotalCompletionTimeFromDefects();
    public CalendarDate getEarliestDateFromDefects();
    public CalendarDate getEarliestDateFromActiveDefects();
    public CalendarDate getLatestDateFromDefects();
    public CalendarDate getLatestDateFromActiveDefects();
    
    nu.xom.Document getXMLContent();
}
