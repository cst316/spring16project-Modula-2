package net.sf.memoranda.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
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

        	// Don't care about it if it's past
        	if(event.getEndDate() != null && event.getEndDate().before(today))
        		continue;

        	// Sort exception dates
        	Vector v = event.getExceptionDates();        	
        	Vector<CalendarDate> exceptionDates = new Vector<CalendarDate>();
        	if(v.size() > 0) {
        		Collections.sort(v);
        		exceptionDates.addAll(v);
        	}
        	
        	// Start with initial startDate, then create more events if necessary for exception dates
        	int exceptionNum = 0;
        	do {
        		CalendarDate startDate;
        		if(exceptionNum == 0) {
        			startDate = event.getStartDate();
        		} else {
        			startDate = exceptionDates.get(exceptionNum-1);
    				java.util.Calendar tempDate = startDate.getCalendar();
        			
        			if(event.getRepeat() == EventsManager.REPEAT_YEARLY) {
        				// Increment by one day (in case it is the same day as event)
        				tempDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
        				
        				// Now find when it should next occur
        				while(!tempDate.equals(event.getStartDate().getCalendar())) {
        					tempDate.add(java.util.Calendar.DAY_OF_YEAR,1);
        				}
        				
        				// Continue now creating this as the beginning new date
        				startDate = new CalendarDate(tempDate);
        			}
                	else if(event.getRepeat() == EventsManager.REPEAT_MONTHLY) {
        				// Increment by one day (in case it is the same day as event)
        				tempDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
        				
        				// Now find when it should next occur
        				while(tempDate.get(java.util.Calendar.DAY_OF_MONTH) != event.getStartDate().getCalendar().get(java.util.Calendar.DAY_OF_MONTH)) {
        					tempDate.add(java.util.Calendar.DAY_OF_YEAR,1);
        				}
        				
        				// Continue now creating this as the beginning new date
        				startDate = new CalendarDate(tempDate);
        			}
                	else if(event.getRepeat() == EventsManager.REPEAT_WEEKLY){ 
        				// Increment by one day (in case it is the same day as event)
        				tempDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
                		
        				while(tempDate.get(java.util.Calendar.DAY_OF_WEEK) != event.getStartDate().getCalendar().get(java.util.Calendar.DAY_OF_WEEK)) {
        					tempDate.add(java.util.Calendar.DAY_OF_YEAR,1);
        				}
        				
        				// Continue now creating this as the beginning new date
        				startDate = new CalendarDate(tempDate);
                	}
                	else {
                		// Get the last exception date we're processing
                		java.util.Calendar exceptionDate = (java.util.Calendar) tempDate.clone();
                		
                		// Start from the beginning
                		tempDate = event.getStartDate().getCalendar();
                		
                		// Keep iterating forward the period until we're after the exception date
                		while(exceptionDate.before(tempDate) || exceptionDate.equals(tempDate))
            				tempDate.add(java.util.Calendar.DAY_OF_YEAR, event.getPeriod());
        				
                		startDate = new CalendarDate(tempDate);
                	}
        		}
        		
            	java.util.Calendar date = java.util.Calendar.getInstance();
            	date.set(java.util.Calendar.DAY_OF_MONTH, startDate.getDay());
            	date.set(java.util.Calendar.MONTH, startDate.getMonth());
            	date.set(java.util.Calendar.YEAR, startDate.getYear());
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
            		recur = new Recur(Recur.DAILY,null);
            		recur.setInterval(7);
            	}
            	else {
            		recur = new Recur(Recur.DAILY,null);
            		recur.setInterval(event.getPeriod());
            	}

            	// Create event
            	net.fortuna.ical4j.model.DateTime icalDate = new net.fortuna.ical4j.model.DateTime(date.getTime());        	
            	VEvent tempEvent = new VEvent(icalDate,new net.fortuna.ical4j.model.Dur(0,1,0,0), event.getText());
            	
            	// Stop repeating if we're going to add another after this
            	if(exceptionNum+1 <= exceptionDates.size()) {
            		recur.setUntil(new net.fortuna.ical4j.model.Date(exceptionDates.get(exceptionNum).getDate()));
            	}
            	// If it has an ending date, set it
            	else if(event.getEndDate() != null) {
                	recur.setUntil(new net.fortuna.ical4j.model.Date(event.getEndDate().getDate()));
        		}
        	
            	// Create and add rule to event
            	RRule rule = new RRule(recur);
            	tempEvent.getProperties().add(rule);
            	
            	// Generate event ID and add to calendar
            	try {
    				UidGenerator uidGenerator = new UidGenerator("1");
    	        	tempEvent.getProperties().add(uidGenerator.generateUid());
    	        	icalCalendar.getComponents().add(tempEvent);
    			} catch (SocketException e1) {
    				e1.printStackTrace();
    			}
        		
            	// Increment exception counter, for do{} while() if there are exception dates
        		exceptionNum++;
        	}
        	while(exceptionNum <= exceptionDates.size());        	
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
