package net.sf.memoranda.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.SocketException;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventExpanded;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Project;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.ExceptionDialog;

public class ICalExporter {
	static private File output;
	
	public static void export(Project prj, File f) {
        if (f.isDirectory())
            output = new File(f.getPath() + "/memoranda_events.ical");
        else
            output = f;

        String filePath = output.getAbsolutePath();
        if(!filePath.endsWith(".ical") && !filePath.endsWith(".ics")) 
            output = new File(filePath + ".ical");
        
        
        
        Calendar icalCalendar = new net.fortuna.ical4j.model.Calendar();
        icalCalendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        icalCalendar.getProperties().add(Version.VERSION_2_0);
        icalCalendar.getProperties().add(CalScale.GREGORIAN);
        
        // Nonrecurring events
        Vector<EventExpanded> eventsNonrecurring = EventsManager.getFutureNonrecurringEvents();
        for(int e = 0; e < eventsNonrecurring.size(); e++) {
        	
        	EventExpanded event = eventsNonrecurring.get(e);
        	java.util.Calendar date = java.util.Calendar.getInstance();
        	date.set(java.util.Calendar.DAY_OF_MONTH, event.getDate().getDay());
        	date.set(java.util.Calendar.MONTH, event.getDate().getMonth());
        	date.set(java.util.Calendar.YEAR, event.getDate().getYear());
        	date.set(java.util.Calendar.HOUR_OF_DAY, event.getHour());
        	date.set(java.util.Calendar.MINUTE, event.getMinute());

        	net.fortuna.ical4j.model.DateTime icalDate = new net.fortuna.ical4j.model.DateTime(date.getTime());
        	
        	VEvent temp = new VEvent(icalDate,new net.fortuna.ical4j.model.Dur(0,1,0,0), event.getText());

        	try {
				UidGenerator uidGenerator = new UidGenerator("1");
	        	temp.getProperties().add(uidGenerator.generateUid());
	        	icalCalendar.getComponents().add(temp);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
        }
        
        // Recurring events
        Vector<Event> eventsRecurring = EventsManager.getRepeatableEvents();
        CalendarDate today = new CalendarDate();
        
        for(int e = 0; e < eventsRecurring.size(); e++) {
        	Event event = eventsRecurring.get(e);

        	if(event.getEndDate() != null && event.getEndDate().before(today))
        		continue;

        	java.util.Calendar date = java.util.Calendar.getInstance();
        	date.set(java.util.Calendar.DAY_OF_MONTH, event.getStartDate().getDay());
        	date.set(java.util.Calendar.MONTH, event.getStartDate().getMonth());
        	date.set(java.util.Calendar.YEAR, event.getStartDate().getYear());
        	date.set(java.util.Calendar.HOUR_OF_DAY, event.getHour());
        	date.set(java.util.Calendar.MINUTE, event.getMinute());
        	
        	Recur recur;
        	        	
        	if(event.getRepeat() == EventsManager.REPEAT_YEARLY) {
       		 	recur = new Recur(Recur.YEARLY,null);
       		 	recur.setInterval(event.getPeriod());
        	}
        	else if(event.getRepeat() == EventsManager.REPEAT_MONTHLY) {
        		// iCal4j has no way to recur on day of months, so this is close :), I hate myself for this sorry
        		recur = new Recur(Recur.MONTHLY,null);
        		recur.setInterval(30);
        	}
        	else if(event.getRepeat() == EventsManager.REPEAT_WEEKLY) {
        		// Find day of the week
        		while(date.get(java.util.Calendar.DAY_OF_WEEK) != event.getPeriod())
        			date.add(java.util.Calendar.DAY_OF_MONTH, 1);
        		
        		// Repeat every day of week
        		recur = new Recur(Recur.WEEKLY,null);
        		recur.setInterval(7);
        	}
        	else {
        		recur = new Recur(Recur.DAILY,null);
        		recur.setInterval(event.getPeriod());
        	}

        	// Create event
        	net.fortuna.ical4j.model.DateTime icalDate = new net.fortuna.ical4j.model.DateTime(date.getTime());        	
        	VEvent temp = new VEvent(icalDate,new net.fortuna.ical4j.model.Dur(0,1,0,0), event.getText());

        	// If it has an ending date, set it
        	if(event.getEndDate() != null)
            	recur.setUntil((net.fortuna.ical4j.model.Date) event.getEndDate().getDate());

        	// Create and add rule to event
        	RRule rule = new RRule(recur);
        	temp.getProperties().add(rule);
        	
        	try {
				UidGenerator uidGenerator = new UidGenerator("1");
	        	temp.getProperties().add(uidGenerator.generateUid());
	        	icalCalendar.getComponents().add(temp);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
        }
       
        // Output
        try {
            CalendarOutputter outputter = new CalendarOutputter();
			FileWriter fw = new FileWriter(output.getAbsoluteFile());
            outputter.output(icalCalendar, fw);
	    }
	    catch (Exception ex) {
	        new ExceptionDialog(ex, "Failed to write to " + output, "");
	        return;
	    }
	}
}
