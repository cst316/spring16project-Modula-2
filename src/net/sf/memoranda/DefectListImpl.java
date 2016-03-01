package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class DefectListImpl implements DefectList {
	
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Defect getDefect(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNextDefectNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Defect createDefect(Date datefound, int number, String type, String injection,
			long approximatefixtime, long fixtime, Date datefixed, String fixremoval, String fixreference, 
			String description, boolean isCompleted) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDefect(Defect defect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDefect(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shiftDefectNumbers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection getAllDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getActiveDefects() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
