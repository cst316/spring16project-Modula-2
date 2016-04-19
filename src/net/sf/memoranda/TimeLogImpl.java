package net.sf.memoranda;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class TimeLogImpl implements TimeLog {

	private static Vector<TimeLogListener> _listeners;
	
	private Vector<TimeEntry> _vector;
	private Project _project = null;
	private Document _doc = null;
	private Element _root = null;
	
	public TimeLogImpl(Document doc, Project prj) {
		_doc = doc;
		_root = _doc.getRootElement();
		_vector = new Vector<TimeEntry>();
		_listeners = new Vector<TimeLogListener>();
		
		// populate _vector with TimeEntry objects
		Elements elements = _root.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			_vector.add(new TimeEntryImpl(elements.get(i)));
		}
	}
	
	public TimeLogImpl(Project prj) {
		_root = new Element("timelog");
		_doc = new Document(_root);
		_vector = new Vector<TimeEntry>();
		_listeners = new Vector<TimeLogListener>();
		_project = prj;
	}
	
	@Override
	public TimeEntry addTimeEntry(CalendarDate date,
	                              Calendar startTime, 
	                              Calendar endTime, 
	                              int interruptionTime, 
	                              Phase phase, 
	                              String comments) {
		
		assert startTime.before(endTime) : "startTime must be before endTime";
		assert interruptionTime >= 0 : "interruptionTime must be >= 0";
		
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
		element.appendChild(eh);
		
		Element em = new Element("endMinute");
		em.appendChild(Integer.toString(endTime.get(Calendar.MINUTE)));
		element.appendChild(em);
		
		Element it = new Element("interruptTime");
		it.appendChild(Integer.toString(interruptionTime));
		element.appendChild(it);
		
		Element dt = new Element("deltaTime");
		dt.appendChild(Integer.toString(computeDeltaTime(startTime, endTime, interruptionTime)));
		element.appendChild(dt);
		
		Element p = new Element("phase");
		p.appendChild(phase.toString());
		element.appendChild(p);
		
		Element c = new Element("comments");
		c.appendChild(comments);
		element.appendChild(c);
		
		_root.appendChild(element);
		
		TimeEntry entry = new TimeEntryImpl(element);
		_vector.add(entry);
		
		notifyListeners();
		
		return entry;
	}
	
	@Override
	public void removeTimeEntry(TimeEntry entry) {
		_root.removeChild(entry.getContent());
		_vector.remove(entry);
		notifyListeners();
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
	
	@Override
	public Project getProject() {
		return _project;
	}
	
	@Override
	public int size() {
		return _root.getChildCount();
	}
	
	private int computeDeltaTime(Calendar startTime, Calendar endTime, int interruptionTime) {
		return ((endTime.get(Calendar.HOUR_OF_DAY) * 60)   + endTime.get(Calendar.MINUTE)) -
			   ((startTime.get(Calendar.HOUR_OF_DAY) * 60) + startTime.get(Calendar.MINUTE)) - 
			   interruptionTime;
	}

	@Override
	public List<TimeEntry> getLog() {
		
		Collections.sort(_vector, new Comparator<TimeEntry>() {
			@Override
			public int compare(TimeEntry e1, TimeEntry e2) {
				if (!e1.getCalendarDate().equals(e2.getCalendarDate()))
					return e1.getCalendarDate().before(e2.getCalendarDate()) ? -1 : 1;
				else
					return (e1.getStartHour() * 60 + e1.getStartMinute()) - 
					       (e2.getStartHour() * 60 + e2.getStartMinute());
			}
		});
		
		return _vector;
	}
	
	public static void addTimeLogListener(TimeLogListener tll) {
        _listeners.add(tll);
    }
	
	private static void notifyListeners() {
        for (int i = 0; i < _listeners.size(); i++) {
            _listeners.get(i).timeLogChanged();            
        }
    }
	
}
