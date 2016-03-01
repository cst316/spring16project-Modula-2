package net.sf.memoranda;

import java.util.Date;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

public class DefectImpl implements Defect, Comparable {
	
	private Element _element = null;
    private DefectList _dl = null;

    /**
     * Constructor for DefaultDefect.
     */
    public DefectImpl(Element defectElement, DefectList dl) {
        _element = defectElement;
        _dl = dl;
    }

    public Element getContent() {
        return _element;
    }

	@Override
	public void setDateFound(CalendarDate date) {
		setAttr("startDate", date.toString());
	}

	@Override
	public CalendarDate getDateFound() {
		return new CalendarDate(_element.getAttribute("dateFound").getValue());
	}


	@Override
	public void setNumber() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setType() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setInjection() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getInjection() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setRemoval() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getRemoval() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setApproximateFixTimeInMinutes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getApproximateFixTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setFixTimeInMinutes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getFixTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setDateRemoved(CalendarDate date) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public CalendarDate getDateRemoved() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFixReference() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getFixReference() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDescription() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCompleted() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean getCompleted() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null)
           _element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }
}
