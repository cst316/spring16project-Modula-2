package net.sf.memoranda;

import java.util.Date;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface Defect {
	
    public static final int ACTIVE = 1;

    public static final int COMPLETED = 2;
    
    public Element getContent();
    
	void setDateFound(CalendarDate date);
	
    CalendarDate getDateFound();
    
    void setDefectId(String id);
  
    String getDefectId();
    
    void setType(String type);
    
    String getType();
    
    //PHASE, NOT STRING
    void setInjection(String injection);
    
    String getInjection();

    void setRemove(String remove);
    
    String getRemove();
    
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
    
    int getCompleted(CalendarDate d);
    
    boolean getIsCompleted();
}
