package net.sf.memoranda;

import java.util.Date;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Node;

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
	}

	@Override
	public CalendarDate getDateFound() {
        return new CalendarDate(_element.getFirstChildElement("dateFound").getValue());
	}

	@Override
	public void setNumber(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInjection(String injection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInjection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRemoval(String removal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRemoval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApproximateFixTimeInMinutes(long approx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getApproximateFixTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFixTimeInMinutes(long fixtime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getFixTimeInMinutes() {
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
	public void setFixReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFixReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCompleted(boolean isCompleted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCompleted() {
		// TODO Auto-generated method stub
		return false;
	}
}
