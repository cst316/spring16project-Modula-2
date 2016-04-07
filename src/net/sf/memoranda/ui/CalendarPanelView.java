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
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.ColorScheme;

public class CalendarPanelView extends JPanel {
	public static final int VIEW_INVALID = -1;
	public static final int VIEW_MONTH = 0;
	public static final int VIEW_WEEK = 1;
	public static final int VIEW_DAY = 2;
	
	GridBagConstraints gbc;
	
	private int _currentHour = -1;
	
	private CalendarPanel _parent;
	private int _type = VIEW_INVALID;
	private JPanel _view = new JPanel(new GridBagLayout());
	private int _rows = -1;
	private int _columns = -1;
	private CalendarPanelCell[] _cells;
	
	public CalendarPanelView(int inType, CalendarPanel inParent) {
		_parent = inParent;
		
		this.changeViewType(inType);
		
		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate d) {
				updateView();
			}
		});
	}
	
	public void changeViewType(int inType) {
		if(inType == _type) return;
		
		if(inType == VIEW_MONTH || inType == VIEW_WEEK || inType == VIEW_DAY) {
			_type = inType;
			this.createView();
		} else {
			_type = VIEW_INVALID;
		}
	}
	
	public void createView() {
		if(_type == VIEW_INVALID) return;

		_view.removeAll();
		_view.revalidate();
		_view.repaint();
		
		_view.setBorder(null);
    	_view.setBackground(ColorScheme.getColor("frame_secondary"));
		
		// Header creation
		if(_type == VIEW_MONTH || _type == VIEW_WEEK) {
			_currentHour = -1;
			
			String[] namesOfDays = new String[] {
				    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
			};
			
			for(int weekday = 0; weekday < 7; weekday++) {
    			JPanel gridCell = new JPanel(new GridBagLayout());
    			gridCell.setBackground(ColorScheme.getColor("frame_secondary"));
    			gridCell.setBorder(null);
    			
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
			if(_type == VIEW_MONTH) {
				_rows = 6;
				_columns = 7;
			}	
			else if(_type == VIEW_WEEK) {
				_rows = 1;
				_columns = 7;
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
		}
		else if(_type == VIEW_DAY) {
			_currentHour = 0;
			
			// Text
			for(int hour = 1; hour <= 24; hour++) {
    			JPanel gridCell = new JPanel(new GridBagLayout());
    			gridCell.setBackground(ColorScheme.getColor("frame_secondary"));
    			
    	        gbc = new GridBagConstraints();
				JLabel gridLabel = new JLabel();
				gridLabel.setText(hour-1 + ":00");
				gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
				gridCell.add(gridLabel,gbc);
				
    	        gbc = new GridBagConstraints();
    	        gbc.gridx = 0; gbc.gridy = hour-1;
    	        gbc.anchor = GridBagConstraints.CENTER;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 0.1;
				gbc.weighty = 1.0;
				
    	        _view.add(gridCell, gbc);
			}
			
			// Cells
			_cells = new CalendarPanelCell[24];
			for(int hour = 0; hour <= 23; hour++) {
				CalendarPanelCell panelCell = new CalendarPanelCell();
				_cells[hour] = panelCell;

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
    	        gbc.gridx = 1; gbc.gridy = hour;
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
	        Collection<Task> tasks = (Collection<Task>) CurrentProject.getTaskList().getTopLevelTasks();

	        Calendar gc = new GregorianCalendar();
	        gc.set(Calendar.MONTH, CurrentDate.get().getMonth());
	        gc.set(Calendar.YEAR, CurrentDate.get().getYear());
	        gc.set(Calendar.DAY_OF_MONTH, 1);
	        int firstMonthWeekday = gc.get(Calendar.DAY_OF_WEEK);
	        
	        gc.add(Calendar.MONTH, 1);
	        gc.add(Calendar.DAY_OF_MONTH, -1);
	        int lastMonthDay = gc.get(Calendar.DAY_OF_MONTH);
	        int cellOffset = -99999;
	        
	    	for(int i = 0; i < 42; i++) {
	    		if(i == firstMonthWeekday-1)
	    			cellOffset = i-1;
	    		
	    		CalendarPanelCell panelCell = _cells[i];
	    		panelCell.getCalendarNode().clear();
	    		
	    		if(cellOffset != -99999 && i-cellOffset <= lastMonthDay) {
	    			// Set the label and date
	    			CalendarDate date = new CalendarDate(i-cellOffset,gc.get(Calendar.MONTH),gc.get(Calendar.YEAR));
	    			generateDay(panelCell,date.getCalendar(),tasks);
	    		} else {
	    			panelCell.getCell().setBorder(null);
	    			panelCell.setActive(false);
	    		}
	    	}
		}
		
		else if(_type == VIEW_WEEK) {
	        Collection<Task> tasks = (Collection<Task>) CurrentProject.getTaskList().getTopLevelTasks();
			
	        Calendar gc = new GregorianCalendar();
	        gc.set(Calendar.MONTH, CurrentDate.get().getMonth());
	        gc.set(Calendar.YEAR, CurrentDate.get().getYear());
	        gc.set(Calendar.DAY_OF_MONTH, CurrentDate.get().getDay());
	        
	        for(int i = 7; i > 0; i--) {
	        	if(gc.get(Calendar.DAY_OF_WEEK) == 1) break;
	        	gc.add(Calendar.DATE, -1);
	        }
	        
	        for(int i = 0; i < 7; i++) {
	        	CalendarPanelCell panelCell = _cells[i];
	        	panelCell.getCalendarNode().clear();

    			CalendarDate date = new CalendarDate(gc);
    			generateDay(panelCell,date.getCalendar(),tasks);
        		panelCell.setActive(true);

        		/*
	        	if(gc.get(Calendar.MONTH) == currentMonth) {
	    			CalendarDate date = new CalendarDate(gc);
	    			generateDay(panelCell,date,tasks);
	        		panelCell.setActive(true);
	        	} else {
	    			panelCell.getCell().setBorder(null);
	    			panelCell.setActive(false);
	        	}
	        	*/
	        	
	        	gc.add(Calendar.DATE, 1);
	        }
		}
		
		else if(_type == VIEW_DAY) {
	        Collection<Task> tasks = (Collection<Task>) CurrentProject.getTaskList().getTopLevelTasks();
	        
	        Calendar dayCal = CurrentDate.get().getCalendar();
	        dayCal.set(Calendar.MINUTE, 0);
	        
	        for(int i = 0; i <= 23; i++) {
	    		CalendarPanelCell panelCell = _cells[i];
	    		panelCell.getCalendarNode().clear();
	    		dayCal.set(Calendar.HOUR_OF_DAY, i);
		        generateDay(panelCell,(Calendar) dayCal.clone(),tasks);
	        }
		}
	}
	
	private void generateDay(CalendarPanelCell panelCell, Calendar calendar, Collection<Task> tasks) {
		CalendarDate date = new CalendarDate(calendar);
		
		if(_type == CalendarPanelView.VIEW_WEEK || _type == CalendarPanelView.VIEW_MONTH) {
			int maxDisplay;
			int curDisplay = 0;
			int overEventDisplay = 0;
			int overTaskDisplay = 0;
			
			if(_type == CalendarPanelView.VIEW_WEEK) {
				panelCell.getLabel().setText(Integer.toString(date.getMonth()+1) + "/" + Integer.toString(date.getDay()));
				maxDisplay = 8;
			} else {
				panelCell.getLabel().setText(Integer.toString(date.getDay()));
				maxDisplay = 3;				
			}
	
			panelCell.setCalendarDate(date);
			
			// Add events
	        if(_parent.taskPanel.isShowEvents()) {
	            Collection<Event> events = (Collection<Event>) EventsManager.getEventsForDate(date);
	            for (Event event : events) {
	            	if(curDisplay >= maxDisplay) {
	            		if(overEventDisplay == 0) {
	            			int type = panelCell.getCalendarNode().queueRemoveLast();
	            			if(type == CalendarNode.TYPE_EVENT)
	            				overEventDisplay++;
	            		}
	            		overEventDisplay++;
	            	} else {
		            	curDisplay++;
		                panelCell.getCalendarNode().queueAdd(event);
	            	}
	            }
	        }
	        
	        // Add tasks
	        if(_parent.taskPanel.isShowTasks()) {
	            for (Task task : tasks) {
	                if(task.getStartDate().equals(date)) {
	                	if(curDisplay >= maxDisplay) {
		            		if(overTaskDisplay == 0 && overEventDisplay == 0) {
		            			// Remove the last element and fix the overdisplay to reflect
		            			int type = panelCell.getCalendarNode().queueRemoveLast();
		            			if(type == CalendarNode.TYPE_TASK)
		            				overTaskDisplay++;
		            			else if(type == CalendarNode.TYPE_EVENT)
		            				overEventDisplay++;
		            		}
		            		overTaskDisplay++;
	                	} else {
	                		curDisplay++;
	                		panelCell.getCalendarNode().queueAdd(task);
	                	}
	                }
	            }
	        }
	        
	        panelCell.getCalendarNode().queueProcess();
	        
	        // If events/tasks are trimmed add label saying how many
	        if((overEventDisplay+overTaskDisplay) > 0)
	        	panelCell.getCalendarNode().addNotShownLabel(overEventDisplay, overTaskDisplay);
	
			// Highlight if date is the current date
			if(date.equals(CurrentDate.get())) {
				panelCell.getCell().setBorder(BorderFactory.createLineBorder(ColorScheme.getColor("frame_highlight"), 2));
			} else {
				// Why do we need this redundant red border? I dunno, but it fixes it
				panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				panelCell.getCell().setBorder(BorderFactory.createLineBorder(ColorScheme.getColor("frame_secondary"), 1));
			}
			
			panelCell.setActive(true);
		}
		else if(_type == CalendarPanelView.VIEW_DAY) {
			int maxDisplay = 4;
			int curDisplay = 0;
			int overEventDisplay = 0;
			int overTaskDisplay = 0;
			
			panelCell.setCalendar(calendar);
			
			Calendar start = (Calendar) calendar.clone();

			Calendar end = (Calendar) calendar.clone();
			end.add(Calendar.HOUR_OF_DAY, 1);
			
			// Add events
	        if(_parent.taskPanel.isShowEvents()) {
	            Collection<Event> events = (Collection<Event>) EventsManager.getEventsForDate(date);
	            for (Event event : events) {
	            	if(inTimespan(event, start, end)) {
	                    if(curDisplay >= maxDisplay) {
	                        if(overEventDisplay == 0) {
	                            int type = panelCell.getCalendarNode().queueRemoveLast();
	                            if(type == CalendarNode.TYPE_EVENT)
	                                overEventDisplay++;
	                        }
	                        overEventDisplay++;
	                    } else {
	                        curDisplay++;
	                    	panelCell.getCalendarNode().queueAdd(event);
	                    }
	                }
	            }
	        }
	        
	        // Add tasks
	        // Tasks do not have a time associated with them, so do not display in hourly (daily) view
	        /*if(_parent.taskPanel.isShowTasks()) {
	            for (Task task : tasks) {
	                if(task.getStartDate().equals(date) && inTimespan(task)) {
	                	if(curDisplay >= maxDisplay) {
		            		if(overTaskDisplay == 0 && overEventDisplay == 0) {
		            			// Remove the last element and fix the overdisplay to reflect
		            			int type = panelCell.getCalendarNode().queueRemoveLast();
		            			if(type == CalendarNode.TYPE_TASK)
		            				overTaskDisplay++;
		            			else if(type == CalendarNode.TYPE_EVENT)
		            				overEventDisplay++;
		            		}
		            		overTaskDisplay++;
	                	} else {
	                		curDisplay++;
	                		panelCell.getCalendarNode().queueAdd(task);
	                	}
	                }
	            }
	        }
	        */

	        panelCell.getCalendarNode().queueProcess();

	        // If events/tasks are trimmed add label saying how many
	        if((overEventDisplay+overTaskDisplay) > 0)
	        	panelCell.getCalendarNode().addNotShownLabel(overEventDisplay, overTaskDisplay);
	        
			if(_currentHour == calendar.get(Calendar.HOUR_OF_DAY)) {
				panelCell.getCell().setBorder(BorderFactory.createLineBorder(ColorScheme.getColor("frame_highlight"), 2));
			} else {
				// Why do we need this redundant red border? I dunno, but it fixes it
				// But now like four weeks later it seems to not need it anymore? WHY?!
				// panelCell.getCell().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				panelCell.getCell().setBorder(BorderFactory.createLineBorder(ColorScheme.getColor("frame_secondary"), 1));
			}
			
	        panelCell.setActive(true);
		}
	}
	
	private boolean inTimespan(Event event, Calendar startCal, Calendar endCal) {
		Calendar eventCal = CurrentDate.get().getCalendar();
		eventCal.set(Calendar.HOUR_OF_DAY, event.getHour());
		eventCal.set(Calendar.MINUTE, event.getMinute());
		
		return (startCal.equals(eventCal) || (startCal.before(eventCal) && endCal.after(eventCal)));
	}
	
	private boolean inTimespan(Task task) {
		// Tasks do not have a time associated with them
		return false;
	}

	public void stepSmallForward() {
		if(_type == VIEW_MONTH) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_WEEK) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_DAY) {
			_currentHour++;
			if(_currentHour >= 24) {
				_currentHour = 0;
				Calendar cal = CurrentDate.get().getCalendar();
				cal.add(Calendar.DATE, 1);
				CurrentDate.set(new CalendarDate(cal));
				updateView();
			}
		}
	}
	public void stepLargeForward() {
		if(_type == VIEW_MONTH) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_WEEK) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, 7);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_DAY) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DATE, 1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
	}
	public void stepSmallBackward() {
		if(_type == VIEW_MONTH) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_WEEK) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_DAY) {
			_currentHour--;
			if(_currentHour < 0) {
				_currentHour = 23;
				Calendar cal = CurrentDate.get().getCalendar();
				cal.add(Calendar.DATE, 1);
				CurrentDate.set(new CalendarDate(cal));
				updateView();
			}
		}
	}
	public void stepLargeBackward() {
		if(_type == VIEW_MONTH) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_WEEK) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
		else if(_type == VIEW_DAY) {
			Calendar cal = CurrentDate.get().getCalendar();
			cal.add(Calendar.DATE, -1);
			CurrentDate.set(new CalendarDate(cal));
			updateView();
		}
	}
	
	public int getCurrentHour() {
		return _currentHour;
	}
	
	public void setCurrentHour(int currentHour) {
		_currentHour = currentHour;
	}
	
	public JPanel getView() {
		return _view;
	}
	
	public int getViewType() {
		return _type;
	}
}