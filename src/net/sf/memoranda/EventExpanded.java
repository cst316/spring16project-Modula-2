package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public class EventExpanded extends EventImpl {
	CalendarDate _date;
	
	public EventExpanded(Element elem, CalendarDate date) {
		super(elem);
		_date = date;
	}
	
	public CalendarDate getDate() {
		return _date;
	}
}
