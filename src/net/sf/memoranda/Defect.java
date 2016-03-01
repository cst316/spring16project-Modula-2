package net.sf.memoranda;

import java.util.Date;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

public interface Defect {
	
	void setDateFound(CalendarDate date);
	
    CalendarDate getDateFound();
    
    void setNumber();
  
    int getNumber();
    
    void setType();
    
    String getType();
    
    void setInjection();
    
    String getInjection();

    void setRemoval();
    
    String getRemoval();
    
    void setApproximateFixTimeInMinutes();
    
    int getApproximateFixTimeInMinutes();
    
    void setFixTimeInMinutes();
    
    int getFixTimeInMinutes();
    
    void setDateRemoved(CalendarDate date);
	
    CalendarDate getDateRemoved();
    
    void setFixReference();
    
    String getFixReference();
    
    void setDescription();
    
    String getDescription();
    
    void setCompleted();
    
    boolean getCompleted();
}
