package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class DefectListImpl implements DefectList {
	
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    private int _pspdefectcount = 0;
    
    /*
	 * Hastable of "task" XOM elements for quick searching them by ID's
	 * (ID => element) 
	 */
	private Hashtable elements = new Hashtable();
	
	public DefectListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
		buildElements(_root);
		_pspdefectcount = _root.getChildCount();
    }
	
	public DefectListImpl(Project prj) {
        _root = new Element("defectlist");
        _doc = new Document(_root);
        _project = prj;
}
	
	
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("defect");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
		}
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public Defect getDefect(int number) {
		return new DefectImpl(getDefectElement(Integer.toString(number)), this); 
	}

	@Override
	public int getNextDefectNumber() {
		return _pspdefectcount+1;
	}

	//PHASE for injection and remove, not Strings
	@Override
	public Defect createDefect(CalendarDate datefound, int id, String type, String injection,
			long approximatefixtime, long fixtime, CalendarDate datefixed, String remove, String fixreference, 
			String description, boolean isCompleted) {
		
		Element el = new Element("defect");
		
        el.addAttribute(new Attribute("id", Integer.toString(id)));
        
        el.addAttribute(new Attribute("isCompleted", Boolean.toString(isCompleted)));
        
        Element inj = new Element("injection");
        inj.appendChild(injection);
        el.appendChild(inj);
                
        Element dfou = new Element("dateFound");
        dfou.appendChild(datefound.toString());
        el.appendChild(dfou);
        
        Element tp = new Element("type");
        tp.appendChild(type);
        el.appendChild(tp);
        
        Element apft = new Element("approximateFixTime");
        apft.appendChild(Long.toString(approximatefixtime));
        el.appendChild(apft);
        
        Element ft = new Element("fixTime");
        ft.appendChild(fixtime != 0? Long.toString(fixtime):"0");
        el.appendChild(ft);
        
        Element dfix = new Element("dateFixed");
        dfix.appendChild(datefixed != null? datefixed.toString():"");
        el.appendChild(dfix);
        
        Element fixrem = new Element("remove");
        fixrem.appendChild(remove != null? remove:"");
        el.appendChild(fixrem);
        
        Element fixref = new Element("fixReference");
        fixref.appendChild(fixreference != null? fixreference:"");
        el.appendChild(fixref);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);
        
		elements.put(Integer.toString(id), el);
		_root.appendChild(el);
		
		_pspdefectcount += 1;
        
        return new DefectImpl(el, this);
	}

	@Override
	public void removeDefect(Defect defect) {
		elements.remove(defect.getDefectId());
	}

	@Override
	public void removeDefect(int number) {
		elements.remove(Integer.toString(number));
	}

	@Override
	public Collection getAllDefects() {
		Elements defects = _root.getChildElements("defects");
        return convertToDefectObjects(defects);
	}

	
	@Override
	public Collection getActiveDefects() {
		
		/*
		Elements defects = _root.getChildElements();
		Elements toreturn = null;
		for (int i = 0; i < defects.size(); i++) {
			Element temp = defects.get(i);
			if (temp.getAttribute("isCompleted").equals("true")) {
				
			}
		}
        return convertToDefectObjects(defects);
        */
		
		return null;
	}
	

	@Override
	public long calculateTotalCompletionTimeFromDefects() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CalendarDate getEarliestDateFromDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getEarliestDateFromActiveDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getLatestDateFromDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getLatestDateFromActiveDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
	
	/*
     * private methods below this line
     */
    private Element getDefectElement(String id) {
		Element el = (Element)elements.get(id);
		if (el == null) {
			Util.debug("Defect " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
    }

    private Collection convertToDefectObjects(Elements defects) {
        Vector v = new Vector();

        for (int i = 0; i < defects.size(); i++) {
            Defect d = new DefectImpl(defects.get(i), this);
            v.add(d);
        }
        return v;
    }
}
