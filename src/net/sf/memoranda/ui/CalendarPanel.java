package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TaskListListener;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;

public class CalendarPanel extends JPanel {
	WorkPanel parentPanel = null;

	JPanel mainPanel = new JPanel(new GridBagLayout());
	CalendarTaskbarPanel taskPanel = new CalendarTaskbarPanel(this);
	JPanel gridPanel = new JPanel(new GridBagLayout());
	
	CalendarPanelCell[] panelCells = new CalendarPanelCell[42];
	CalendarDate startDate;
	CalendarDate endDate;
	
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
    	
		String[] namesOfDays = new String[] {
			    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
		};
        
    	for(int weekday = 0; weekday < 7; weekday++) {
    		for(int row = 0; row < 7; row++) {    			
    			if(row != 0) {
    				CalendarPanelCell panelCell = new CalendarPanelCell();
    				panelCells[weekday+7*(row-1)] = panelCell;

    				panelCell.getCell().addMouseListener(new MouseListener() {
    					@Override
                        public void mouseClicked(MouseEvent e) {
                            click(e, panelCell);
                        }

						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
                    });
    				
        	        gbc = new GridBagConstraints();
        	        gbc.gridx = weekday; gbc.gridy = row;
        	        gbc.anchor = GridBagConstraints.CENTER;
	    			gbc.fill = GridBagConstraints.BOTH;
	    	        gbc.weightx = 1.0;
	    	        gbc.weighty = 1.0;
	    	        gridPanel.add(panelCell.getCell(), gbc);
    			} else {
        			JPanel gridCell = new JPanel(new GridBagLayout());
        			gridCell.setBackground(Color.WHITE);
        			
        	        gbc = new GridBagConstraints();
    				JLabel gridLabel = new JLabel();
    				gridLabel.setText(namesOfDays[weekday]);
    				gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
    				gridCell.add(gridLabel,gbc);
    				
        	        gbc = new GridBagConstraints();
        	        gbc.gridx = weekday; gbc.gridy = row;
        	        gbc.anchor = GridBagConstraints.CENTER;
    				gbc.fill = GridBagConstraints.HORIZONTAL;
    				gbc.weightx = 1.0;
    				
	    	        gridPanel.add(gridCell, gbc);
    			}
    		}
    	}
    	
    	populateDays();

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
    	mainPanel.add(gridPanel,gbc);

    	// CalendarPanel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        this.setLayout(new GridBagLayout());
    	this.add(mainPanel,gbc);
    	
    	// Event listeners
    	EventsScheduler.addListener(new EventNotificationListener() {
			public void eventIsOccured(Event ev) {
				populateDays();
			}

			public void eventsChanged() {
				populateDays();
			}
		});
		
		TaskPanel.addTaskListListener(new TaskListListener() {
			public void taskListModified() {
				populateDays();
			}
		});
	}
    
    protected void click(MouseEvent e, CalendarPanelCell panelCell) {
    	if(!panelCell.isActive()) return;
    	
    	if(panelCell.getCalendarDate().equals(CurrentDate.get())) return;
    	
		CurrentDate.set(panelCell.getCalendarDate());
		populateDays();
	}

	protected void populateDays() {
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, CurrentDate.get().getMonth());
        gc.set(Calendar.YEAR, CurrentDate.get().getYear());
        gc.set(Calendar.DAY_OF_MONTH, 1);
        int firstMonthWeekday = gc.get(Calendar.DAY_OF_WEEK);
        
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        int lastMonthDay = gc.get(Calendar.DAY_OF_MONTH);
        int cellOffset = -1;
        
        Collection<Task> tasks = (Collection<Task>) CurrentProject.getTaskList().getTopLevelTasks();
        
    	for(int i = 0; i < 42; i++) {
    		if(i == firstMonthWeekday-1)
    			cellOffset = i-1;
    		
    		CalendarPanelCell panelCell = panelCells[i];
    		if(cellOffset != -1 & i-cellOffset <= lastMonthDay) {
    			// Set the label and date
    			CalendarDate date = new CalendarDate(i-cellOffset,gc.get(Calendar.MONTH),gc.get(Calendar.YEAR));
    			panelCell.getLabel().setText(Integer.toString(i-cellOffset));
    			panelCell.setCalendarDate(date);
    			
    			panelCell.getCalendarNode().clear();
    			
    			// Add events
    	        Collection<Event> events = (Collection<Event>) EventsManager.getEventsForDate(date);
                for (Event event : events) {
                    panelCell.getCalendarNode().addEvent(event);
                }
                
                // Add tasks
                for (Task task : tasks) {
                	if(task.getStartDate().equals(date)) {
                		panelCell.getCalendarNode().addTask(task);
                	}
                }

    			// Highlight if date is the current date
    			if(date.equals(CurrentDate.get())) {
    				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    			} else {
    				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    			}
    			
    			panelCell.setActive(true);
    			
    		} else {
    			panelCell.setActive(false);
    		}
    	}
    }
}
