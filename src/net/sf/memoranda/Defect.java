package net.sf.memoranda;

import java.util.Date;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

public interface Defect {
	
	void setDateFound(CalendarDate date);
	
    CalendarDate getDateFound();
    
    void setNumber(int number);
  
    int getNumber();
    
    void setType(String type);
    
    String getType();
    
    void setInjection(String injection);
    
    String getInjection();

    void setRemoval(String removal);
    
    String getRemoval();
    
    void setApproximateFixTimeInMinutes(long approx);
    
    long getApproximateFixTimeInMinutes();
    
    void setFixTimeInMinutes(long fixtime);
    
    long getFixTimeInMinutes();
    
    void setDateRemoved(CalendarDate date);
	
    CalendarDate getDateRemoved();
    
    void setFixReference(String reference);
    
    String getFixReference();
    
    void setDescription(String description);
    
    String getDescription();
    
    void setCompleted(boolean isCompleted);
    
    boolean getCompleted();
}
