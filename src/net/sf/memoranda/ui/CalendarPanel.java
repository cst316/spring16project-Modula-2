package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskListListener;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;

public class CalendarPanel extends JPanel {
	WorkPanel parentPanel = null;

	JPanel mainPanel = new JPanel(new GridBagLayout());
	CalendarTaskbarPanel taskPanel = new CalendarTaskbarPanel(this);
	CalendarPanelView viewPanel = new CalendarPanelView(CalendarPanelView.VIEW_INVALID,this);
	
	CalendarPanelCell[] panelCells = new CalendarPanelCell[42];
	CalendarDate startDate;
	CalendarDate endDate;
	
	CalendarCellPopupMenu cellPopupMenu = new CalendarCellPopupMenu();
	
	GridBagConstraints gbc;
	
    public CalendarPanel(WorkPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
		viewPanel.changeViewType(CalendarPanelView.VIEW_MONTH);

    	// TaskBar
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
    	mainPanel.add(taskPanel,gbc);

    	// GridPanel
        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    	mainPanel.add(viewPanel.getView(),gbc);

    	// CalendarPanel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        this.setLayout(new GridBagLayout());
    	this.add(mainPanel,gbc);
    	
    	this.setBorder(null);
    	mainPanel.setBorder(null);
    	
    	// Event listeners
    	EventsScheduler.addListener(new EventNotificationListener() {
			public void eventIsOccured(Event ev) {
				updateCalendarPanelView();
			}

			public void eventsChanged() {
				updateCalendarPanelView();
			}
		});
		
		TaskPanel.addTaskListListener(new TaskListListener() {
			public void taskListModified() {
				updateCalendarPanelView();
			}
		});
	}
    
    public void updateCalendarPanelView() {
    	viewPanel.updateView();
    }
    
    public void changeCalendarPanelView(int inViewType) {
    	viewPanel.changeViewType(inViewType);
    }
    
    protected void calendarPanelCellClick(MouseEvent e, CalendarPanelCell panelCell) {
    	if(!panelCell.isActive()) return;
    	
    	if(panelCell.getCalendarDate().equals(CurrentDate.get())) return;
    	
		CurrentDate.set(panelCell.getCalendarDate());
		taskPanel.updateCalendar();
		updateCalendarPanelView();
	}
}
