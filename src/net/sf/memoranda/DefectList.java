package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;

import net.sf.memoranda.date.CalendarDate;

public interface DefectList {

	Project getProject();
	
	Defect getDefect(int number);
	
	int getNextDefectNumber();

    Defect createDefect(Date datefound, int number, String type, String injection, String removal, int approximatefixtime, int fixtime,
			String fixreference, String description);

    void removeDefect(Defect defect);
    
    void removeDefect(int number);
    
    void shiftDefectNumbers();
    
	public Collection getAllDefects();
	
    public Collection getActiveDefects();
    
    public long calculateTotalCompletionTimeFromDefects();
    public CalendarDate getEarliestDateFromDefects();
    public CalendarDate getEarliestDateFromActiveDefects();
    public CalendarDate getLatestDateFromDefects();
    public CalendarDate getLatestDateFromActiveDefects();
    
    nu.xom.Document getXMLContent();
}
