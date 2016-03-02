package net.sf.memoranda;

import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

public class TimeLogImpl implements TimeLog {

	private Document _doc = null;
	private Element _root = null;
	
	public TimeLogImpl(Document doc, Project prj) {
		_doc = doc;
		_root = new Element("timelog");
	}
	
	public TimeLogImpl() {
		_root = new Element("timelog");
		_doc = new Document(_root);
	}
	
	@Override
	public TimeEntry createTimeEntry(CalendarDate date,
	                                 Calendar startTime, 
	                                 Calendar endTime, 
	                                 int interruptionTime, 
	                                 int deltaTime, 
	                                 Phase phase, 
	                                 String comments) {
		
		Element element = new Element("timeEntry");
		String id = Util.generateId();
		element.addAttribute(new Attribute("id", id));
		
		Element d = new Element("date");
		d.appendChild(date.toString());
		element.appendChild(d);
		
		Element sh = new Element("startHour");
		sh.appendChild(Integer.toString(startTime.get(Calendar.HOUR)));
		element.appendChild(sh);
		
		Element sm = new Element("startMinute");
		sm.appendChild(Integer.toString(startTime.get(Calendar.MINUTE)));
		element.appendChild(sm);
		
		Element eh = new Element("endHour");
		eh.appendChild(Integer.toString(endTime.get(Calendar.HOUR)));
		element.appendChild(sh);
		
		Element em = new Element("endMinute");
		em.appendChild(Integer.toString(endTime.get(Calendar.MINUTE)));
		element.appendChild(em);
		
		Element it = new Element("interruptTime");
		it.appendChild(Integer.toString(interruptionTime));
		element.appendChild(it);
		
		Element dt = new Element("deltaTime");
		dt.appendChild(Integer.toString(deltaTime));
		element.appendChild(dt);
		
		Element p = new Element("phase");
		p.appendChild(phase.toString());
		element.appendChild(p);
		
		Element c = new Element("comments");
		c.appendChild(comments);
		element.appendChild(c);
		
		return new TimeEntryImpl(element);
	}
	
	@Override
	public void removeTimeEntry(TimeEntry entry) {
		_root.removeChild(entry.getContent());
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
	
}
