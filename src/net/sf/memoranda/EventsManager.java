/**
 * EventsManager.java Created on 08.03.2003, 12:35:19 Alex Package:
 * net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.EventDialog;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
//import nu.xom.Comment;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParentNode;

/**
 *  
 */
/*$Id: EventsManager.java,v 1.11 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventsManager {
/*	public static final String NS_JNEVENTS =
		"http://www.openmechanics.org/2003/jnotes-events-file";
*/
	public static final int NO_REPEAT = 0;
	public static final int REPEAT_DAILY = 1;
	public static final int REPEAT_WEEKLY = 2;
	public static final int REPEAT_MONTHLY = 3;
	public static final int REPEAT_YEARLY = 4;

	public static Document _doc = null;
	static Element _root = null;

	static {
		CurrentStorage.get().openEventsManager();
		if (_doc == null) {
			_root = new Element("eventslist");
/*			_root.addNamespaceDeclaration("jnevents", NS_JNEVENTS);
			_root.appendChild(
				new Comment("This is JNotes 2 data file. Do not modify.")); */
			_doc = new Document(_root);
		} else
			_root = _doc.getRootElement();
	}
	
	public static Vector<EventExpanded> getFutureNonrecurringEvents() {
		Vector<EventExpanded> nonrecurringEvents = new Vector<EventExpanded>();
		
		Elements yearElements = _root.getChildElements();
		
		for(int years = 0; years < yearElements.size(); years++) {
			Element yearElement = yearElements.get(years);
			//System.out.println("getUpcomingEvents(): yearElement = " + yearElement.getLocalName() + "(Size " + yearElement.getChildCount() + ")");
			if(yearElement.getLocalName() == "repeatable") {
				// Repeatable events
				// Eat this, not processing repeatable here
			} 
			else if(yearElement.getLocalName() == "sticker") {
				// What is this
				// Eat this, whatever the heck this is
			} else {
				// Year
				Elements monthElements = yearElement.getChildElements();
				for(int months = 0; months < monthElements.size(); months++) {
					// Month
					Element monthElement = monthElements.get(months);
					//System.out.println("getUpcomingEvents(): monthElement = " + monthElement.getLocalName() + "(Size " + monthElement.getChildCount() + ")");
					Elements dayElements = monthElement.getChildElements();
					for(int days = 0; days < dayElements.size(); days++) {
						// Day
						Element dayElement = dayElements.get(days);
						//System.out.println("getUpcomingEvents(): dayElement = " + dayElement.getLocalName() + "(Size " + dayElement.getChildCount() + ")");
					
						int dateYear = Integer.parseInt(yearElement.getAttributeValue("year"));
						int dateMonth = Integer.parseInt(monthElement.getAttributeValue("month"));
						int dateDay = Integer.parseInt(dayElement.getAttributeValue("day"));

						CalendarDate date = new CalendarDate(dateDay,dateMonth,dateYear);

						Elements eventElements = dayElement.getChildElements();
						for(int e = 0; e < eventElements.size(); e++) {
							// Event
							Element eventElement = eventElements.get(e);
							//System.out.println("getUpcomingEvents(): Added event (" + singletonEvents.size() + " total)");
							
							EventExpanded event = new EventExpanded(eventElement, date);
							CalendarDate today = new CalendarDate();
							
							// Only get events today or in the future
							if(date.before(today) && !date.equals(today))
								continue;
							
							nonrecurringEvents.add(event);
						}
					}
				}
			}
		}
		
		//System.out.println("getUpcomingEvents() returning " + singletonEvents.size());
		return nonrecurringEvents;
	}

	public static void createSticker(String text, int prior) {
		Element el = new Element("sticker");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("priority", prior+""));
		el.appendChild(text);
		_root.appendChild(el);
	}

	@SuppressWarnings("unchecked")
	public static Map getStickers() {
		Map m = new HashMap();
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			m.put(se.getAttribute("id").getValue(), se);
		}
		return m;
	}

	public static void removeSticker(String stickerId) {
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			if (se.getAttribute("id").getValue().equals(stickerId)) {
				_root.removeChild(se);
				break;
			}
		}
	}

	public static boolean isNREventsForDate(CalendarDate date) {
		Day d = getDay(date);
		if (d == null)
			return false;
		if (d.getElement().getChildElements("event").size() > 0)
			return true;
		return false;
	}

	public static Collection getEventsForDate(CalendarDate date) {
		Vector v = new Vector();
		Day d = getDay(date);
		
		if (d != null) {			
			Elements els = d.getElement().getChildElements("event");
			for (int i = 0; i < els.size(); i++)
				v.add(new EventImpl(els.get(i)));
		}
		Collection r = getRepeatableEventsForDate(date);
		if (r.size() > 0)
			v.addAll(r);
		//EventsVectorSorter.sort(v);
		Collections.sort(v);
		return v;
	}

	public static Event createEvent(
		CalendarDate date,
		int hh,
		int mm,
		String text) {
		Element el = new Element("event");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.appendChild(text);
		Day d = getDay(date);
		if (d == null)
			d = createDay(date);
		d.getElement().appendChild(el);
		
		EventImpl event = new EventImpl(el);
		return event;
	}

	public static Event createRepeatableEvent(
		int type,
		CalendarDate startDate,
		CalendarDate endDate,
		int period,
		int hh,
		int mm,
		String text,
		boolean workDays,
		CalendarDate[] exceptionDates
		)
	{
		Element el = new Element("event");
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null) {
			rep = new Element("repeatable");
			_root.appendChild(rep);
		}
		el.addAttribute(new Attribute("repeat-type", String.valueOf(type)));
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("startDate", startDate.toString()));
		if (endDate != null)
			el.addAttribute(new Attribute("endDate", endDate.toString()));
		el.addAttribute(new Attribute("period", String.valueOf(period)));
		// new attribute for wrkin days - ivanrise
		el.addAttribute(new Attribute("workingDays",String.valueOf(workDays)));
		el.appendChild(text);
		rep.appendChild(el);

		EventImpl event = new EventImpl(el);
		
		if(exceptionDates != null && exceptionDates.length > 0) {
			for(int i = 0; i < exceptionDates.length; i++) {
				event.addExceptionDate(exceptionDates[i]);
			}
		}
		
		return event;
	}

	public static Vector<Event> getRepeatableEvents() {
		Vector<Event> v = new Vector<Event>();
		Element rep = _root.getFirstChildElement("repeatable");

		if (rep == null)
			return v;
		
		Elements els = rep.getChildElements("event");
		for (int i = 0; i < els.size(); i++)
			v.add(new EventImpl(els.get(i)));
		return v;
	}

	public static Collection getRepeatableEventsForDate(CalendarDate date) {
		Vector reps = (Vector) getRepeatableEvents();
		Vector v = new Vector();
		for (int i = 0; i < reps.size(); i++) {
			Event ev = (Event) reps.get(i);
			
			// ignore this event if it's a 'only working days' event and today is weekend.
			if(ev.getWorkingDays() && (date.getCalendar().get(Calendar.DAY_OF_WEEK) == 1 ||
				date.getCalendar().get(Calendar.DAY_OF_WEEK) == 7)) continue;
			
			// -- dchende2
			if(ev.hasExceptionDate( date )) continue;

			if (date.inPeriod(ev.getStartDate(), ev.getEndDate())) {
				if (ev.getRepeat() == REPEAT_DAILY) {
					int n = date.getCalendar().get(Calendar.DAY_OF_YEAR);
					int ns =
						ev.getStartDate().getCalendar().get(
							Calendar.DAY_OF_YEAR);
					//System.out.println((n - ns) % ev.getPeriod());
					if ((n - ns) % ev.getPeriod() == 0)
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_WEEKLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_WEEK)
						== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_MONTHLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_MONTH)
						== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_YEARLY) {
					int period = ev.getPeriod();
					//System.out.println(date.getCalendar().get(Calendar.DAY_OF_YEAR));
					if (Util.isLeapYear(date.getYear())
						&& date.getCalendar().get(Calendar.DAY_OF_YEAR) > 60)
						period++;

					if (date.getCalendar().get(Calendar.DAY_OF_YEAR) == period)
						v.add(ev);
				}
			}
		}
		return v;
	}

	public static Collection getActiveEvents() {
		return getEventsForDate(CalendarDate.today());
	}

	public static Event getEvent(CalendarDate date, int hh, int mm) {
		Day d = getDay(date);
		if (d == null)
			return null;
		Elements els = d.getElement().getChildElements("event");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			if ((new Integer(el.getAttribute("hour").getValue()).intValue()
				== hh)
				&& (new Integer(el.getAttribute("min").getValue()).intValue()
					== mm))
				return new EventImpl(el);
		}
		return null;
	}

	public static void removeEvent(CalendarDate date, int hh, int mm) {
		Day d = getDay(date);
		if (d == null)
			d.getElement().removeChild(getEvent(date, hh, mm).getContent());
	}

	public static void removeEvent(Event ev) {
		ParentNode parent = ev.getContent().getParent();
		parent.removeChild(ev.getContent());
	}

	private static Day createDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			y = createYear(date.getYear());
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			m = y.createMonth(date.getMonth());
		Day d = m.getDay(date.getDay());
		if (d == null)
			d = m.createDay(date.getDay());
		return d;
	}

	private static Year createYear(int y) {
		Element el = new Element("year");
		el.addAttribute(new Attribute("year", new Integer(y).toString()));
		_root.appendChild(el);
		return new Year(el);
	}

	private static Year getYear(int y) {
		Elements yrs = _root.getChildElements("year");
		String yy = new Integer(y).toString();
		for (int i = 0; i < yrs.size(); i++)
			if (yrs.get(i).getAttribute("year").getValue().equals(yy))
				return new Year(yrs.get(i));
		//return createYear(y);
		return null;
	}

	private static Day getDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			return null;
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			return null;
		return m.getDay(date.getDay());
	}

	static class Year {
		Element yearElement = null;

		public Year(Element el) {
			yearElement = el;
		}

		public int getValue() {
			return new Integer(yearElement.getAttribute("year").getValue())
				.intValue();
		}

		public Month getMonth(int m) {
			Elements ms = yearElement.getChildElements("month");
			String mm = new Integer(m).toString();
			for (int i = 0; i < ms.size(); i++)
				if (ms.get(i).getAttribute("month").getValue().equals(mm))
					return new Month(ms.get(i));
			//return createMonth(m);
			return null;
		}

		private Month createMonth(int m) {
			Element el = new Element("month");
			el.addAttribute(new Attribute("month", new Integer(m).toString()));
			yearElement.appendChild(el);
			return new Month(el);
		}

		public Vector getMonths() {
			Vector v = new Vector();
			Elements ms = yearElement.getChildElements("month");
			for (int i = 0; i < ms.size(); i++)
				v.add(new Month(ms.get(i)));
			return v;
		}

		public Element getElement() {
			return yearElement;
		}

	}

	static class Month {
		Element mElement = null;

		public Month(Element el) {
			mElement = el;
		}

		public int getValue() {
			return new Integer(mElement.getAttribute("month").getValue())
				.intValue();
		}

		public Day getDay(int d) {
			if (mElement == null)
				return null;
			Elements ds = mElement.getChildElements("day");
			String dd = new Integer(d).toString();
			for (int i = 0; i < ds.size(); i++)
				if (ds.get(i).getAttribute("day").getValue().equals(dd))
					return new Day(ds.get(i));
			//return createDay(d);
			return null;
		}

		private Day createDay(int d) {
			Element el = new Element("day");
			el.addAttribute(new Attribute("day", new Integer(d).toString()));
			el.addAttribute(
				new Attribute(
					"date",
					new CalendarDate(
						d,
						getValue(),
						new Integer(
							((Element) mElement.getParent())
								.getAttribute("year")
								.getValue())
							.intValue())
						.toString()));

			mElement.appendChild(el);
			return new Day(el);
		}

		public Vector getDays() {
			if (mElement == null)
				return null;
			Vector v = new Vector();
			Elements ds = mElement.getChildElements("day");
			for (int i = 0; i < ds.size(); i++)
				v.add(new Day(ds.get(i)));
			return v;
		}

		public Element getElement() {
			return mElement;
		}

	}

	static class Day {
		Element dEl = null;

		public Day(Element el) {
			dEl = el;
		}

		public int getValue() {
			return new Integer(dEl.getAttribute("day").getValue()).intValue();
		}

		/*
		 * public Note getNote() { return new NoteImpl(dEl);
		 */

		public Element getElement() {
			return dEl;
		}
	}
	
    public static void buildRepeatableEvent(EventDialog dlg, int hh, int mm, String text) {
		int rtype;
        int period;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate ed = null;
        if (dlg.enableEndDateCB.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        if (dlg.dailyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_DAILY;
            period = ((Integer) dlg.daySpin.getModel().getValue()).intValue();
        }
        else if (dlg.weeklyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_WEEKLY;
            period = dlg.weekdaysCB.getSelectedIndex() + 1;
		    if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
				if(period==7) period=1;
				else period++;
		    }
        }
		else if (dlg.yearlyRepeatRB.isSelected()) {
		    rtype = EventsManager.REPEAT_YEARLY;
		    period = sd.getCalendar().get(Calendar.DAY_OF_YEAR);
		    if(Util.isLeapYear(sd.getYear()) && sd.getCalendar().get(Calendar.DAY_OF_YEAR) > 60) period--;
		}
	    else {
	        rtype = EventsManager.REPEAT_MONTHLY;
	        period = ((Integer) dlg.dayOfMonthSpin.getModel().getValue()).intValue();
	    }
        EventsManager.createRepeatableEvent(rtype, sd, ed, period, hh, mm, text, dlg.workingDaysOnlyCB.isSelected(),dlg.getExceptionDates());
    }
}
