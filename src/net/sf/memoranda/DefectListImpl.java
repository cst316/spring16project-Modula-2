package net.sf.memoranda;

import java.util.Collection;
import java.util.Hashtable;
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
    private String _lastDefectId = null;
    
    /*
	 * Hastable of "task" XOM elements for quick searching them by ID's
	 * (ID => element) 
	 */
	private Hashtable elements = new Hashtable();
	
	
	//Used to loads current defect list from doc file
	public DefectListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _lastDefectId = _root.getAttributeValue("lastdefectid");
        
        // Legacy support, if user does not have an existing defect ID
        if(_lastDefectId == null)
        	_lastDefectId = "1";
        
        _project = prj;
		buildElements(_root);
    }
	
	
	//Used to create initial defect list when none exists
	public DefectListImpl(Project prj) {
        _root = new Element("defectlist");
        
        // Legacy support, if user does not have an existing defect ID
        if(_lastDefectId == null)
        	_lastDefectId = "1";
        
		_root.addAttribute(new Attribute("lastdefectid","1"));
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
	public Defect getDefect(String number) {
		return new DefectImpl(getDefectElement(number), this); 
	}

	@Override
	public String getLastDefectId() {
		return _lastDefectId;
	}

	//PHASE for injection and remove, not Strings
	@Override
	public Defect createDefect(CalendarDate datefound, String type, Phase injection,
			long approximatefixtime, long fixtime, CalendarDate datefixed, Phase remove, String fixreference,
			String description, boolean isCompleted) {
		
		Element el = new Element("defect");
		
        el.addAttribute(new Attribute("id", _lastDefectId));
        
        el.addAttribute(new Attribute("isCompleted", Boolean.toString(isCompleted)));
        
        Element inj = new Element("injection");
        inj.appendChild(injection.toString());
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
        
        if(remove == null) {
        	fixrem.appendChild("");
        }
        else {
        	fixrem.appendChild(remove.toString());
        }
        el.appendChild(fixrem);
        
        Element fixref = new Element("fixReference");
        fixref.appendChild(fixreference != null? fixreference:"");
        el.appendChild(fixref);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);
        
		elements.put(_lastDefectId, el);
		_root.appendChild(el);
		
		_lastDefectId = Integer.toString(Integer.parseInt(_lastDefectId) + 1);
		
		// Legacy support, if they don't have a previous defect ID
		if(_root.getAttribute("lastdefectid") != null)
			_root.removeAttribute(_root.getAttribute("lastdefectid"));

		_root.addAttribute(new Attribute("lastdefectid", _lastDefectId));
		
        return new DefectImpl(el, this);
	}

	@Override
	public void removeDefect(Defect defect) {
        _root.removeChild(defect.getContent());
		elements.remove(defect.getDefectId());
	}

	@Override
	public Collection getAllDefects() {
		Elements defects = _root.getChildElements("defect");
        return convertToDefectObjects(defects);
	}

	
	@Override
	public Collection getActiveDefects(String defectId, CalendarDate date) {
		
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
