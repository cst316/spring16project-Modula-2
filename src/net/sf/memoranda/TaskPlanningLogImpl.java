package net.sf.memoranda;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class TaskPlanningLogImpl implements TaskPlanningLog {

	private static Vector<TaskPlanningLogListener> _listeners;
	
	private Vector<TaskPlanningEntry> _vector;
	private Project _project = null;
	private Document _doc = null;
	private Element _root = null;
	
	private HashMap<TaskPlanningEntry, Integer> pvMap = new HashMap<>();
	private HashMap<TaskPlanningEntry, Integer> evMap = new HashMap<>();
	private HashMap<TaskPlanningEntry, Double> chMap = new HashMap<>();
	
	public TaskPlanningLogImpl(Document doc, Project prj) {
		_doc = doc;
		_root = _doc.getRootElement();
		_vector = new Vector<>();
		_listeners = new Vector<TaskPlanningLogListener>();
		
		// populate _vector with TimeEntry objects
		Elements elements = _root.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			_vector.add(new TaskPlanningEntryImpl(elements.get(i)));
		}
		
		computeDerivedValues();
	}
	
	public TaskPlanningLogImpl(Project prj) {
		_root = new Element("taskplanninglog");
		_doc = new Document(_root);
		_vector = new Vector<TaskPlanningEntry>();
		_listeners = new Vector<TaskPlanningLogListener>();
		_project = prj;
	}
	
	@Override
	public double getCumulativeHours(TaskPlanningEntry entry) {
		return chMap.get(entry);
	}
	
	@Override
	public int getCumulativePV(TaskPlanningEntry entry) {
		return pvMap.get(entry);
	}
	
	@Override
	public int getCumulativeEV(TaskPlanningEntry entry) {
		return evMap.get(entry);
	}
	
	@Override
	public TaskPlanningEntry addTaskPlanningEntry(int taskNumber, 
			                                      String taskName, 
			                                      double plannedHours, 
			                                      int plannedValue, 
			                                      int earnedValue,
			                                      CalendarDate plannedDateWeek, 
			                                      CalendarDate actualDateWeek,
			                                      boolean isComplete) {

		Element element = new Element("taskPlanningEntry");
		
		Element tnu = new Element("taskNumber");
		tnu.appendChild(Integer.toString(taskNumber));
		element.appendChild(tnu);
		
		Element tna = new Element("taskName");
		tna.appendChild(taskName);
		element.appendChild(tna);
		
		Element ph = new Element("plannedHours");
		ph.appendChild(Double.toString(plannedHours));
		element.appendChild(ph);
		
		Element pv = new Element("plannedValue");
		pv.appendChild(Integer.toString(plannedValue));
		element.appendChild(pv);
		
		Element ev = new Element("earnedValue");
		ev.appendChild(Integer.toString(earnedValue));
		element.appendChild(ev);
		
		Element pdw = new Element("plannedDateWeek");
		pdw.appendChild(plannedDateWeek.toString());
		element.appendChild(pdw);
		
		Element adw = new Element("actualDateWeek");
		adw.appendChild(actualDateWeek.toString());
		element.appendChild(adw);
		
		Element ic = new Element("isComplete");
		if(isComplete)
			ic.appendChild("true");
		else
			ic.appendChild("false");
		element.appendChild(ic);
		
		_root.appendChild(element);
		
		TaskPlanningEntry entry = new TaskPlanningEntryImpl(element);
		_vector.add(entry);
		
		computeDerivedValues();
		notifyListeners();
		
		return entry;
	}

	@Override
	public void removeTaskPlanningEntry(TaskPlanningEntry entry) {
		_root.removeChild(entry.getContent());
		_vector.remove(entry);
		
		computeDerivedValues();
		notifyListeners();
	}

	@Override
	public List<TaskPlanningEntry> getLog() {
		sort(_vector);
		return _vector;
	}

	@Override
	public int size() {
		return _root.getChildCount();
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
	
	public static void addTaskPlanningLogListener(TaskPlanningLogListener tpll) {
        _listeners.add(tpll);
    }

	private static void notifyListeners() {
		for (int i = 0; i < _listeners.size(); i++) {
			_listeners.get(i).taskPlanningLogModified();
		}
	}
	
	private void sort(Vector<TaskPlanningEntry> entries) {
		if(entries == null) return;
		
		Collections.sort(_vector, new Comparator<TaskPlanningEntry>() {
			@Override
			public int compare(TaskPlanningEntry e1, TaskPlanningEntry e2) {
				if (e1.getActualDate() != null && e2.getActualDate() != null)
					if (!e1.getActualDate().equals(e2.getActualDate()))
						return e1.getActualDate().before(e2.getActualDate()) ? -1 : 1;
					else
						return e1.getEV() - e2.getEV();
				else if (!e1.getPlannedDate().equals(e2.getPlannedDate()))
					return e1.getPlannedDate().before(e2.getPlannedDate()) ? -1 : 1;
				else
					return e1.getEV() - e2.getEV();
			}
		});
	}
	
	private void computeDerivedValues() {
		
		sort(_vector);
		
		double chCounter = 0;
		int pvCounter = 0;
		int evCounter = 0;
		
		// compute derived values for each entry
		for (TaskPlanningEntry entry : _vector) {
			chCounter += entry.getPlannedHours();
			pvCounter += entry.getPV();
			evCounter += entry.getEV();
			
			chMap.put(entry, chCounter);
			pvMap.put(entry, pvCounter);
			evMap.put(entry, evCounter);
		}
	}
	
}
