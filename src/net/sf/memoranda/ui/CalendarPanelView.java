package net.sf.memoranda.ui;

import java.awt.Color;
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
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;

public class CalendarPanelView extends JPanel {
	public static final int VIEW_INVALID = -1;
	public static final int VIEW_MONTH = 0;
	public static final int VIEW_WEEK = 1;
	public static final int VIEW_DAY = 2;
	
	GridBagConstraints gbc;
	
	private CalendarPanel _parent;
	private int _type = VIEW_INVALID;
	private JPanel _view = new JPanel(new GridBagLayout());
	private int _rows = -1;
	private int _columns = -1;
	private CalendarPanelCell[] _cells;
	
	public CalendarPanelView(int inType, CalendarPanel inParent) {
		_parent = inParent;
		this.changeViewType(inType);
	}
	
	public void changeViewType(int inType) {
		if(inType == _type) return;
		
		if(inType == VIEW_MONTH | inType == VIEW_WEEK | inType == VIEW_DAY) {
			_type = inType;
			this.createView();
		} else {
			_type = VIEW_INVALID;
		}
	}
	
	public void createView() {
		if(_type == VIEW_INVALID) return;
		
		_view.removeAll();
		
		// Header creation
		if(_type == VIEW_MONTH | _type == VIEW_DAY) {
			String[] namesOfDays = new String[] {
				    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
			};
			
			for(int weekday = 0; weekday < 7; weekday++) {
    			JPanel gridCell = new JPanel(new GridBagLayout());
    			gridCell.setBackground(Color.WHITE);
    			
    	        gbc = new GridBagConstraints();
				JLabel gridLabel = new JLabel();
				gridLabel.setText(namesOfDays[weekday]);
				gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
				gridCell.add(gridLabel,gbc);
				
    	        gbc = new GridBagConstraints();
    	        gbc.gridx = weekday; gbc.gridy = 0;
    	        gbc.anchor = GridBagConstraints.CENTER;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				
    	        _view.add(gridCell, gbc);
			}
		}
		else if(_type == VIEW_DAY) {
			JPanel gridCell = new JPanel(new GridBagLayout());
			gridCell.setBackground(Color.WHITE);
			
	        gbc = new GridBagConstraints();
			JLabel gridLabel = new JLabel();
			gridLabel.setText("");
			gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
			gridCell.add(gridLabel,gbc);
			
	        gbc = new GridBagConstraints();
	        gbc.gridx = 0; gbc.gridy = 0;
	        gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			
	        _view.add(gridCell, gbc);
		}
		
		// Rows/columns for main view
		if(_type == VIEW_MONTH) {
			_rows = 6;
			_columns = 7;
		}
		
		else if(_type == VIEW_WEEK) {
			_rows = 1;
			_columns = 7;
		}
		
		else if(_type == VIEW_DAY) {
			_rows = 1;
			_columns = 1;
		}
	
		_cells = new CalendarPanelCell[_rows*_columns];
		
		// Main panel creation
    	for(int col = 0; col < _columns; col++) {
    		for(int row = 0; row < _rows; row++) {    			
				CalendarPanelCell panelCell = new CalendarPanelCell();
				_cells[col+7*row] = panelCell;

				panelCell.getCell().addMouseListener(new MouseListener() {
					@Override
                    public void mouseClicked(MouseEvent e) {
                        _parent.calendarPanelCellClick(e, panelCell);
                    }
					
					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							_parent.cellPopupMenu.show(e.getComponent(), e.getX(), e.getY());
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
                });
				
    	        gbc = new GridBagConstraints();
    	        gbc.gridx = col; gbc.gridy = row+1;
    	        gbc.anchor = GridBagConstraints.CENTER;
    			gbc.fill = GridBagConstraints.BOTH;
    	        gbc.weightx = 1.0;
    	        gbc.weighty = 1.0;
    	        _view.add(panelCell.getCell(), gbc);
    		}
    	}
    	
    	updateView();
	}
	
	public void updateView() {
		if(_type == VIEW_MONTH) { 
			Calendar gc = new GregorianCalendar();
	        gc.set(Calendar.MONTH, CurrentDate.get().getMonth());
	        gc.set(Calendar.YEAR, CurrentDate.get().getYear());
	        gc.set(Calendar.DAY_OF_MONTH, 1);
	        int firstMonthWeekday = gc.get(Calendar.DAY_OF_WEEK);
	        
	        gc.add(Calendar.MONTH, 1);
	        gc.add(Calendar.DAY_OF_MONTH, -1);
	        int lastMonthDay = gc.get(Calendar.DAY_OF_MONTH);
	        int cellOffset = -99999;
	        
	        Collection<Task> tasks = (Collection<Task>) CurrentProject.getTaskList().getTopLevelTasks();
	        
	    	for(int i = 0; i < 42; i++) {
	    		if(i == firstMonthWeekday-1)
	    			cellOffset = i-1;
	    		
	    		CalendarPanelCell panelCell = _cells[i];
	    		panelCell.getCalendarNode().clear();
	    		
	    		if(cellOffset != -99999 & i-cellOffset <= lastMonthDay) {
	    			// Set the label and date
	    			CalendarDate date = new CalendarDate(i-cellOffset,gc.get(Calendar.MONTH),gc.get(Calendar.YEAR));
	    			panelCell.getLabel().setText(Integer.toString(i-cellOffset));
	    			panelCell.setCalendarDate(date);
	    			
	    			// Add events
	                if(_parent.taskPanel.isShowEvents()) {
	                    Collection<Event> events = (Collection<Event>) EventsManager.getEventsForDate(date);
	                    for (Event event : events) {
	                        panelCell.getCalendarNode().addEvent(event);
	                    }
	                }
	                
	                // Add tasks
	                if(_parent.taskPanel.isShowTasks()) {
	                    for (Task task : tasks) {
	                        if(task.getStartDate().equals(date))
	                            panelCell.getCalendarNode().addTask(task);
	                    }
	                }

	    			// Highlight if date is the current date
	    			if(date.equals(CurrentDate.get())) {
	    				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	    			} else {
	    				// Why do we need this redundant red border? I dunno, but it fixes it
	    				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	    				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	    			}
	    			
	    			panelCell.setActive(true);
	    			
	    		} else {
	    			panelCell.getCell().setBorder(null);
	    			panelCell.setActive(false);
	    		}
	    	}
		}
		
		else if(_type == VIEW_WEEK) {
			
		}
		
		else if(_type == VIEW_DAY) {
			
		}
	}
	
	public JPanel getView() {
		return _view;
	}
	
	public int getViewType() {
		return _type;
	}
}
