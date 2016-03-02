package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

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
	public CalendarDate getDateFound() {
        return new CalendarDate(_element.getFirstChildElement("dateFound").getValue());
	}

	@Override
	public void setId(int id) {
		_element.addAttribute(new Attribute("id", Integer.toString(id)));
	}

	@Override
	public String getId() {
		return _element.getAttribute("id").getValue();
	}

	@Override
	public void setType(String type) {
		Element ty = new Element("type");
		ty.appendChild(type);
		_element.appendChild(ty);
	}

	@Override
	public String getType() {
		return _element.getFirstChildElement("type").getValue();
	}

	@Override
	public void setInjection(String injection) {
		Element inj = new Element("injection");
		inj.appendChild(injection);
		_element.appendChild(inj);
	}

	@Override
	public String getInjection() {
		return _element.getFirstChildElement("injection").getValue();
	}

	@Override
	public void setRemove(String remove) {
		Element rem = new Element("remove");
		rem.appendChild(remove);
		_element.appendChild(rem);
	}

	@Override
	public String getRemove() {
		return _element.getFirstChildElement("remove").getValue();
	}

	@Override
	public void setApproximateFixTimeInMinutes(long approx) {
		Element apft = new Element("approximateFixTime");
		apft.appendChild(Long.toString(approx));
		_element.appendChild(apft);
	}

	@Override
	public long getApproximateFixTimeInMinutes() {
		return Long.parseLong(_element.getFirstChildElement("approximateFixTime").getValue());
	}

	@Override
	public void setFixTimeInMinutes(long fixtime) {
		Element ft = new Element("fixTime");
		ft.appendChild(Long.toString(fixtime));
		_element.appendChild(ft);
	}

	@Override
	public long getFixTimeInMinutes() {
		return Long.parseLong(_element.getFirstChildElement("fixTime").getValue());
	}

	@Override
	public void setDateRemoved(CalendarDate date) {
		Element eldate = new Element("dateFixed");
		eldate.appendChild(date.toString());
		_element.appendChild(eldate);
	}

	@Override
	public CalendarDate getDateRemoved() {
		return new CalendarDate(_element.getFirstChildElement("dateFixed").getValue());
	}

	@Override
	public void setFixReference(String reference) {
		Element fr = new Element("fixReference");
		fr.appendChild(reference);
		_element.appendChild(fr);
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
	public boolean getCompleted() {
		return Boolean.parseBoolean(_element.getFirstChildElement("isCompleted").getValue());
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
