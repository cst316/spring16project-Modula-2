package net.sf.memoranda.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.SocketException;
import java.util.Vector;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import net.sf.memoranda.EventExpanded;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Project;
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
        
        Vector<EventExpanded> events = EventsManager.getFutureNonrecurringEvents();
        
        Calendar icalCalendar = new net.fortuna.ical4j.model.Calendar();
        icalCalendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        icalCalendar.getProperties().add(Version.VERSION_2_0);
        icalCalendar.getProperties().add(CalScale.GREGORIAN);
        
        
        for(int e = 0; e < events.size(); e++) {
        	EventExpanded event = events.get(e);
        	java.util.Calendar date = java.util.Calendar.getInstance();
        	date.set(java.util.Calendar.DAY_OF_MONTH, event.getDate().getDay());
        	date.set(java.util.Calendar.MONTH, event.getDate().getMonth());
        	date.set(java.util.Calendar.YEAR, event.getDate().getYear());
        	date.set(java.util.Calendar.HOUR_OF_DAY, event.getHour());
        	date.set(java.util.Calendar.MINUTE, event.getMinute());
        	
        	VEvent temp = new VEvent(new net.fortuna.ical4j.model.Date(date.getTime()), event.getText());

        	try {
				UidGenerator uidGenerator = new UidGenerator("1");
	        	temp.getProperties().add(uidGenerator.generateUid());
	        	icalCalendar.getComponents().add(temp);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
        }
       
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
