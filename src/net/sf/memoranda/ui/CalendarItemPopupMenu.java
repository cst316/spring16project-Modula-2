package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

public class CalendarItemPopupMenu extends JPopupMenu {

	Event event;
	Task task;
	
	public CalendarItemPopupMenu(Object item) {
		
		super();
		
		if (item instanceof Event)
			event = (Event) item;
		else if (item instanceof Task)
			task = (Task) item;
		
		JMenuItem edit = new JMenuItem("Edit");
		JMenuItem delete = new JMenuItem("Delete");
		
		add(edit);
		add(delete);
		
		edit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteB_actionPerformed(e);
			}
		});
		
		delete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editB_actionPerformed(e);
			}
		});
	}
	
	public void deleteB_actionPerformed(ActionEvent e) {
		if (event != null) {
			/**
			 * This dialog box is taken from the editEventB_actionPerformed() callback
			 * method in EventPanel.
			 */
			EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Event"));
	        Event ev = event;
	        
	        dlg.timeSpin.getModel().setValue(ev.getTime());
	 
	        dlg.textField.setText(ev.getText());
	        int rep = ev.getRepeat();
	        if (rep > 0) {
	            dlg.startDate.getModel().setValue(ev.getStartDate().getDate());
	            if (rep == EventsManager.REPEAT_DAILY) {
	                dlg.dailyRepeatRB.setSelected(true);
	                dlg.dailyRepeatRB_actionPerformed(null);
	                dlg.daySpin.setValue(new Integer(ev.getPeriod()));
	            }
	            else if (rep == EventsManager.REPEAT_WEEKLY) {
	                dlg.weeklyRepeatRB.setSelected(true);
	                dlg.weeklyRepeatRB_actionPerformed(null);
					int d = ev.getPeriod() - 1;
					if(Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
					    d--;
					    if(d<0) d=6;
					}
	                dlg.weekdaysCB.setSelectedIndex(d);
	            }
	            else if (rep == EventsManager.REPEAT_MONTHLY) {
	                dlg.monthlyRepeatRB.setSelected(true);
	                dlg.monthlyRepeatRB_actionPerformed(null);
	                dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
	            }
			    else if (rep == EventsManager.REPEAT_YEARLY) {
					dlg.yearlyRepeatRB.setSelected(true);
					dlg.yearlyRepeatRB_actionPerformed(null);
					dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
			    }
		        if (ev.getEndDate() != null) {
		           dlg.endDate.getModel().setValue(ev.getEndDate().getDate());
		           dlg.enableEndDateCB.setSelected(true);
		           dlg.enableEndDateCB_actionPerformed(null);
		        }
				if(ev.getWorkingDays()) {
					dlg.workingDaysOnlyCB.setSelected(true);
				}
				
				Vector<CalendarDate> exceptionDates = ev.getExceptionDates();
				if(exceptionDates.size() > 0) {
					for(int i = 0; i < exceptionDates.size(); i++ ) {
				    	SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
				    	CalendarDate exceptionDate = exceptionDates.get(i);
				    	dlg.exceptionModel.addElement( exceptionDate.getMonth() + "/" + exceptionDate.getDay() + "/" + exceptionDate.getYear() );
					}
				}
	        }
	        
	        Dimension frmSize = App.getFrame().getSize();
	        Point loc = App.getFrame().getLocation();
	        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
	        dlg.setVisible(true);
	        if (dlg.CANCELLED)
	            return;
	        EventsManager.removeEvent(ev);
	        
			Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
			calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));
			int hh = calendar.get(Calendar.HOUR_OF_DAY);
			int mm = calendar.get(Calendar.MINUTE);

	        String text = dlg.textField.getText();
	        if (dlg.noRepeatRB.isSelected()) {
	        	EventsManager.createEvent(CurrentDate.get(), hh, mm, text);
	    	}
	        
	        CurrentStorage.get().storeEventsManager();
	        EventsScheduler.notifyChanged();
		}
		else if (task != null) {
			/**
			 * This dialog box is taken from the editTaskB_actionPerformed() callback
			 * method in TaskPanel.
			 */
			Task t = task;
	        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("Edit task"));
	        Dimension frmSize = App.getFrame().getSize();
	        Point loc = App.getFrame().getLocation();
	        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
	        dlg.todoField.setText(t.getText());
	        dlg.descriptionField.setText(t.getDescription());
	        dlg.startDate.getModel().setValue(t.getStartDate().getDate());
	        dlg.endDate.getModel().setValue(t.getEndDate().getDate());
	        dlg.priorityCB.setSelectedIndex(t.getPriority());                
	        dlg.effortField.setText(Util.getHoursFromMillis(t.getEffort()));
			if((t.getStartDate().getDate()).after(t.getEndDate().getDate()))
				dlg.chkEndDate.setSelected(false);
			else
				dlg.chkEndDate.setSelected(true);
			dlg.progress.setValue(new Integer(t.getProgress()));
			dlg.chkEndDate_actionPerformed(null);	
	        dlg.setVisible(true);
	        if (dlg.CANCELLED)
	            return;
	        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
	         CalendarDate ed;
	 		if(dlg.chkEndDate.isSelected())
	 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
	 		else
	 			ed = null;
	        t.setStartDate(sd);
	        t.setEndDate(ed);
	        t.setText(dlg.todoField.getText());
	        t.setDescription(dlg.descriptionField.getText());
	        t.setPriority(dlg.priorityCB.getSelectedIndex());
	        t.setEffort(Util.getMillisFromHours(dlg.effortField.getText()));
	        t.setProgress(((Integer)dlg.progress.getValue()).intValue());

	        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
	        
	        TaskPanel.notifyTaskListListeners();
		}
	}
	
	public void editB_actionPerformed(ActionEvent e) {
		if (event != null) {
			EventsManager.removeEvent(event);
			EventsScheduler.notifyChanged();
			CurrentStorage.get().storeEventsManager();
		}
		else if (task != null) {
			CurrentProject.getTaskList().removeTask(task);
			TaskPanel.notifyTaskListListeners();
		}
	}
}
