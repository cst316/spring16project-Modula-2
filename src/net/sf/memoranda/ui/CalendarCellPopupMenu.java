package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

public class CalendarCellPopupMenu extends JPopupMenu {
	
	public CalendarCellPopupMenu() {
		
		super();
		
		JMenuItem addEvent = new JMenuItem("Add Event");
		JMenuItem addTask  = new JMenuItem("Add Task");
		
		add(addEvent);
		add(addTask);
		
		addEvent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEventB_actionPerformed(e);
			}
		});
		
		addTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTaskB_actionPerformed(e);
			}
		});
	}
	
	public void addEventB_actionPerformed(ActionEvent e) {
		/**
		 * This dialog box is taken from the newEventB_actionPerformed() callback
		 * method in EventPanel.
		 */
		EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("New event"));
		Calendar cdate = CurrentDate.get().getCalendar();
		Dimension frmSize = App.getFrame().getSize();
    	Point loc = App.getFrame().getLocation();

		dlg.startDate.getModel().setValue(cdate.getTime());
		dlg.endDate.getModel().setValue(cdate.getTime());
		dlg.timeSpin.getModel().setValue(cdate.getTime());

    	dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
    	dlg.setEventDate(cdate.getTime());
		dlg.setVisible(true);
    	if (dlg.CANCELLED)
    		return;
    	
    	Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
    	calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));
    	int hh = calendar.get(Calendar.HOUR_OF_DAY);
    	int mm = calendar.get(Calendar.MINUTE);
    	String text = dlg.textField.getText();
		
		CalendarDate eventCalendarDate = new CalendarDate(dlg.getEventDate());
		
    	if (dlg.noRepeatRB.isSelected())
    		EventsManager.createEvent(eventCalendarDate, hh, mm, text);
    	else {
    		EventsManager.buildRecurringEvent(dlg, hh, mm, text);
    	}
    	
    	CurrentStorage.get().storeEventsManager();
        EventsScheduler.notifyChanged();
	}
	
	public void addTaskB_actionPerformed(ActionEvent e) {
		/**
		 * This dialog box is taken from the newTaskB_actionPerformed() callback
		 * method in TaskPanel.
		 */
		TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New task"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
          CalendarDate ed;
 		if(dlg.chkEndDate.isSelected())
 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
 		else
 			ed = null;
        long effort = Util.getMillisFromHours(dlg.effortField.getText());
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),null);
		newTask.setProgress(((Integer)dlg.progress.getValue()).intValue());
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        
        TaskPanel.notifyTaskListListeners();
	}
}
