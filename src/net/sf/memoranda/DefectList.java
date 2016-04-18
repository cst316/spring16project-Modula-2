package net.sf.memoranda;

import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;

public interface DefectList {

	Project getProject();
	
	Defect getDefect(String thisDefectId);
	
	String getLastDefectId();
	
    Defect createDefect(CalendarDate datefound, String type, Phase injection, long approximatefixtime, long fixtime,
			CalendarDate datefixed, Phase fixremoval, String fixreference, String description, boolean isCompleted);

    boolean removeDefect(Defect defect);
    
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
