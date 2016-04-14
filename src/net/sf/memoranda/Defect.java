package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface Defect {
	
    public static final int ACTIVE = 1;

    public static final int COMPLETED = 2;
    
    public Element getContent();
    
	void setDateFound(CalendarDate date);
	
	void editDateFound(CalendarDate date);
	
    CalendarDate getDateFound();
    
    void setDefectId(String id);

    String getDefectId();
    
    void setType(String type);

    void editType(String type);
    
    String getType();
    
    //PHASE, NOT STRING
    void setInjection(String injection);
    
    void editInjection(String injection);
    
    String getInjection();

    void setRemove(String remove);
    
    void editRemove(String remove);
    
    String getRemove();
    
    void setApproximateFixTimeInMillis(long approx);
    
    void editApproximateFixTimeInMillis(long approx);
    
    long getApproximateFixTimeInMillis();
    
    void setFixTimeInMillis(long fixtime);
    
    void editFixTimeInMillis(long fixtime);
    
    long getFixTimeInMillis();
    
    void setDateRemoved(CalendarDate date);
	
    void editDateRemoved(CalendarDate date);
    
    CalendarDate getDateRemoved();
    
    void setFixReference(String reference);
    
    void editFixReference(String reference);
    
    String getFixReference();
    
    void setDescription(String description);
    
    void editDescription(String description);
    
    String getDescription();
    
    void setCompleted(boolean isCompleted);
    
    void editCompleted(boolean isCompleted);
    
    int getCompleted(CalendarDate d);
    
    boolean getIsCompleted();
}
