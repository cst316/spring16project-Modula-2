package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

public class DefectImpl implements Defect {
	
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
		Element eldate = new Element("dateFound");
		eldate.appendChild(date.toString());
		_element.appendChild(eldate);
	}
	
	@Override
	public void editDateFound(CalendarDate date) {
		Element eldate = new Element("dateFound");
		eldate.appendChild(date.toString());
		Elements old = _element.getChildElements("dateFound");
		Element old2 = old.get(0);
		_element.replaceChild(old2, eldate);
	}

	@Override
	public CalendarDate getDateFound() {
        return new CalendarDate(_element.getFirstChildElement("dateFound").getValue());
	}

	@Override
	public void setDefectId(String id) {
		_element.addAttribute(new Attribute("id", id));
	}
	
	@Override
	public String getDefectId() {
		return _element.getAttribute("id").getValue();
	}

	@Override
	public void setType(String type) {
		Element ty = new Element("type");
		ty.appendChild(type);
		_element.appendChild(ty);
	}
	
	@Override
	public void editType(String type) {
		Element ty = new Element("type");
		ty.appendChild(type);
		Elements old = _element.getChildElements("type");
		Element old2 = old.get(0);
		_element.replaceChild(old2, ty);
	}
	
	@Override
	public String getType() {
		return _element.getFirstChildElement("type").getValue();
	}

	@Override
	public void setInjection(Phase injection) {
		String injectionString;
		if(injection == null) 
			injectionString = Phase.INVALID.toString();
		else 
			injectionString = injection.toString();
		
		Element inj = new Element("injection");
		inj.appendChild(injectionString);
		_element.appendChild(inj);
	}
	
	@Override
	public void editInjection(Phase injection) {
		String injectionString;
		if(injection == null) 
			injectionString = Phase.INVALID.toString();
		else 
			injectionString = injection.toString();
		
		Element inj = new Element("injection");
		inj.appendChild(injectionString);
		Elements old = _element.getChildElements("injection");
		Element old2 = old.get(0);
		_element.replaceChild(old2, inj);
	}

	@Override
	public Phase getInjection() {
		String injphase = _element.getFirstChildElement("injection").getValue();
		
		for (Phase phase : Phase.values()) {
			if (injphase.equals(phase.toString())) {
				return phase;
			}
		}
		return Phase.INVALID;
	}

	@Override
	public void setRemove(Phase remove) {
		String removeString;
		if(remove == null) 
			removeString = Phase.INVALID.toString();
		else 
			removeString = remove.toString();
		
		Element rem = new Element("remove");
		rem.appendChild(removeString);
		_element.appendChild(rem);
	}
	
	@Override
	public void editRemove(Phase remove) {
		String removeString;
		if(remove == null) 
			removeString = Phase.INVALID.toString();
		else 
			removeString = remove.toString();
		
		Element rem = new Element("remove");
		rem.appendChild(removeString);
		Elements old = _element.getChildElements("remove");
		Element old2 = old.get(0);
		_element.replaceChild(old2, rem);
	}

	@Override
	public Phase getRemove() {
		String remphase = _element.getFirstChildElement("remove").getValue();
		
		for (Phase phase : Phase.values()) {
			if (remphase.equals(phase.toString())) {
				return phase;
			}
		}
		return Phase.INVALID;
	}

	@Override
	public void setApproximateFixTimeInMillis(long approx) {
		Element apft = new Element("approximateFixTime");
		apft.appendChild(Long.toString(approx));
		_element.appendChild(apft);
	}
	
	@Override
	public void editApproximateFixTimeInMillis(long approx) {
		Element apft = new Element("approximateFixTime");
		apft.appendChild(Long.toString(approx));
		Elements old = _element.getChildElements("approximateFixTime");
		Element old2 = old.get(0);
		_element.replaceChild(old2, apft);
	}

	@Override
	public long getApproximateFixTimeInMillis() {
		return Long.parseLong(_element.getFirstChildElement("approximateFixTime").getValue());
	}

	@Override
	public void setFixTimeInMillis(long fixtime) {
		Element ft = new Element("fixTime");
		ft.appendChild(Long.toString(fixtime));
		_element.appendChild(ft);
	}
	
	@Override
	public void editFixTimeInMillis(long fixtime) {
		Element ft = new Element("fixTime");
		ft.appendChild(Long.toString(fixtime));
		Elements old = _element.getChildElements("fixTime");
		Element old2 = old.get(0);
		_element.replaceChild(old2, ft);
	}

	@Override
	public long getFixTimeInMillis() {
		return Long.parseLong(_element.getFirstChildElement("fixTime").getValue());
	}

	@Override
	public void setDateRemoved(CalendarDate date) {
		Element eldate = new Element("dateFixed");
		eldate.appendChild(date.toString());
		_element.appendChild(eldate);
	}
	
	@Override
	public void editDateRemoved(CalendarDate date) {
		
		Element eldate = new Element("dateFixed");
		
		if (date == null) {
			eldate.appendChild(new CalendarDate().toString());
		}
		else {
			eldate.appendChild(date.toString());
		}
		
		Elements old = _element.getChildElements("dateFixed");
		Element old2 = old.get(0);
		_element.replaceChild(old2, eldate);
	}

	@Override
	public CalendarDate getDateRemoved() {
		String d = _element.getFirstChildElement("dateFixed").getValue();
		if (d.equals("")) {
			return new CalendarDate();
		}
		else {
			return new CalendarDate(d);
		}
	}

	@Override
	public void setFixReference(String reference) {
		Element fr = new Element("fixReference");
		fr.appendChild(reference);
		_element.appendChild(fr);
	}
	
	@Override
	public void editFixReference(String reference) {
		Element fr = new Element("fixReference");
		fr.appendChild(reference);
		Elements old = _element.getChildElements("fixReference");
		Element old2 = old.get(0);
		_element.replaceChild(old2, fr);
	}

	@Override
	public String getFixReference() {
		return _element.getFirstChildElement("fixReference").getValue();
	}

	@Override
	public void setDescription(String description) {
		Element d = new Element("description");
		d.appendChild(description);
		_element.appendChild(d);
	}
	
	@Override
	public void editDescription(String description) {
		Element d = new Element("description");
		d.appendChild(description);
		Elements old = _element.getChildElements("description");
		Element old2 = old.get(0);
		_element.replaceChild(old2, d);	
	}

	@Override
	public String getDescription() {
		return _element.getFirstChildElement("description").getValue();
	}

	@Override
	public void setCompleted(boolean isCompleted) {
		Element comp = new Element("isCompleted");
		comp.appendChild(Boolean.toString(isCompleted));
		_element.appendChild(comp);
	}
	
	@Override
	public void editCompleted(boolean isCompleted) {
		_element.removeAttribute(_element.getAttribute("isCompleted"));
		_element.addAttribute(new Attribute("isCompleted", Boolean.toString(isCompleted)));
	}

	@Override
	public int getCompleted(CalendarDate d) {
		if (getIsCompleted()) {
            return Defect.COMPLETED;
        }
		else {
            return Defect.ACTIVE;
		}
	}
	
	@Override
	public boolean getIsCompleted() {
		String comp = _element.getAttribute("isCompleted").getValue();
		return Boolean.parseBoolean(comp);
	}
	
	
	private Collection convertToDefectObjects(Elements defects) {
        Vector v = new Vector();

        for (int i = 0; i < defects.size(); i++) {
            Defect d = new DefectImpl(defects.get(i), _dl);
            v.add(d);
        }
        return v;
    }
}
