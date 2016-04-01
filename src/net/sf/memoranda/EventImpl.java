/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Node;

/**
 * 
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements Event, Comparable {
    
    private Element _elem = null;

    /**
     * Constructor for EventImpl.
     */
    public EventImpl(Element elem) {
        _elem = elem;
    }

    /**
     * @see net.sf.memoranda.Event#getHour()
     */
    public int getHour() {
        return new Integer(_elem.getAttribute("hour").getValue()).intValue();
    }

    /**
     * @see net.sf.memoranda.Event#getMinute()
     */
    public int getMinute() {
        return new Integer(_elem.getAttribute("min").getValue()).intValue();
    }
    
    public String getTimeString() {
        return Local.getTimeString(getHour(), getMinute());
    }
        
  
    /**
     * @see net.sf.memoranda.Event#getText()
     */
    public String getText() {
        return _elem.getValue();
    }

    /**
     * @see net.sf.memoranda.Event#getContent()
     */
    public Element getContent() {
        return _elem;
    }
    /**
     * @see net.sf.memoranda.Event#isRepeatable()
     */
    public boolean isRepeatable() {
        return getStartDate() != null;
    }
    /**
     * @see net.sf.memoranda.Event#getStartDate()
     */
    public CalendarDate getStartDate() {
        Attribute a = _elem.getAttribute("startDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getEndDate()
     */
    public CalendarDate getEndDate() {
        Attribute a = _elem.getAttribute("endDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getPeriod()
     */
    public int getPeriod() {
        Attribute a = _elem.getAttribute("period");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getId()
     */
    public String getId() {
        Attribute a = _elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getRepeat()
     */
    public int getRepeat() {
        Attribute a = _elem.getAttribute("repeat-type");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getTime()
     */
    public Date getTime() {
		Date d = new Date(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.setTime(d); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.HOUR_OF_DAY, getHour()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.MINUTE, getMinute()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.SECOND, 0); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		d = calendar.getTime(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
        return d;
    }
	
	/**
     * @see net.sf.memoranda.Event#getWorkinDays()
     */
	public boolean getWorkingDays() {
        Attribute a = _elem.getAttribute("workingDays");
        if (a != null && a.getValue().equals("true")) return true;
        return false;
	}
	
	public int compareTo(Object o) {
		Event event = (Event) o;
		return (getHour() * 60 + getMinute()) - (event.getHour() * 60 + event.getMinute());
	}
	
	public boolean addExceptionDate(CalendarDate date) {
		Element elemDates = _elem.getFirstChildElement("exceptionDates");
		Node elemOriginal = null;
		
		// Null check
		if(elemDates == null) {
			elemDates = new Element("exceptionDates");
		} else {
			elemOriginal = elemDates.copy();
		}

		// Check if it already exists
		for(int i = 0; i < elemDates.getAttributeCount(); i++) {
			Attribute a = elemDates.getAttribute(i);
			CalendarDate elemDate = new CalendarDate(a.getValue());

			if(elemDate.equals(date))
				return false;
		}

		Attribute a = new Attribute("exception"+Integer.toString(elemDates.getAttributeCount()+1),Util.getDateStamp(date));
		elemDates.addAttribute(a);
		
		// dchende2 So many returns...
		if(elemOriginal != null) {
			for(int i = 0; i < _elem.getChildCount(); i++) {
				if(_elem.getChild(i) == _elem.getFirstChildElement("exceptionDates")) {
					_elem.replaceChild(elemOriginal,elemDates); 
					return true;
				}
				return false;
			}
		} else {
			_elem.appendChild(elemDates);
			return true;
		}
		
		return false;
	}

	public boolean addExceptionDates(Vector<CalendarDate> dates) {
		int success = 0;
		
		for(int i = 0; i < dates.size(); i++) {
			boolean flag = addExceptionDate(dates.get(i));
			if(flag) success++;
		}
		
		return (success == dates.size());
	}
	
	public boolean removeExceptionDate(CalendarDate date) {
		Element elemDates = _elem.getFirstChildElement("exceptionDates");
		
		// Null check
		if(elemDates == null) return true;
		
		for(int i = 0; i < elemDates.getAttributeCount(); i++) {
			Attribute a = elemDates.getAttribute(i);
			CalendarDate elemDate = new CalendarDate(a.getValue());

			if(elemDate.equals(date)) {
				elemDates.removeAttribute(a);
				return true;
			}
		}
		
		return false;
	}
	
    /**
     * @see net.sf.memoranda.Event#getExceptionDates()
     */
	public Vector<CalendarDate> getExceptionDates() {
		Element elemDates = _elem.getFirstChildElement("exceptionDates");
		Vector<CalendarDate> v = new Vector<CalendarDate>();

		if(elemDates == null) {return v;}
		
		for(int i = 0; i < elemDates.getAttributeCount(); i++) {
			v.add( new CalendarDate(elemDates.getAttribute(i).getValue()) );
		}
		
		return v;
	}
	
	/**
     * @see net.sf.memoranda.Event#hasExceptionDate()
     */
	public boolean hasExceptionDate(CalendarDate date) {
		Vector<CalendarDate> v = this.getExceptionDates();
		
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i).equals(date))
				return true;
		}

		return false;
	}

}
