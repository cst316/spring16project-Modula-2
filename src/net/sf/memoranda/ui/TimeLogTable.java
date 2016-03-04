package net.sf.memoranda.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Phase;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeLogImpl;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class TimeLogTable extends JTable {

	TimeLogPanel parent;
	List<TimeEntry> entries;
	
	public TimeLogTable(TimeLogPanel parent) {
		super();
		
		CalendarDate today = new CalendarDate();
		
		Calendar startTime = new GregorianCalendar();
		startTime.set(Calendar.HOUR, 4);
		startTime.set(Calendar.MINUTE, 30);
		
		Calendar endTime = new GregorianCalendar();
		endTime.set(Calendar.HOUR, 5);
		endTime.set(Calendar.MINUTE, 0);
		
		int interruptionTime = 5;
		Phase phase = Phase.CODE;
		String comments = "testAddRemoveTimeEntry() #1";
		
		CurrentProject.getTimeLog().addTimeEntry(today, startTime, endTime, interruptionTime, phase, comments);
		
		initTable();
		setModel(new TimeLogTableModel());
		this.parent = parent;
		
		ListSelectionModel selectionModel = getSelectionModel();
		selectionModel.addListSelectionListener(e -> {
			listSelection_actionPerformed(e);
		});
		
		TimeLogImpl.addTimeLogListener(() -> {
			tableChanged();
		});
	}
	
	public void initTable() {
		entries = CurrentProject.getTimeLog().getLog();
	}
	
	public void tableChanged() {
		initTable();
		updateUI();
	}
	
	class TimeLogTableModel extends AbstractTableModel {
		
		final String[] columnNames = {
                Local.getString("Date"),
                Local.getString("Start"),
                Local.getString("Stop"),
                Local.getString("Interruption Time"),
                Local.getString("Delta Time"),
                Local.getString("Phase"),
                Local.getString("Comments")};

		public String getColumnName(int i) {
            return columnNames[i];
        }
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return entries.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			TimeEntry e = entries.get(row);
			switch (col) {
				case 0: return e.getDate().getLongDateString();
				case 1: return getCalendarTime(e.getStartTime());
				case 2: return getCalendarTime(e.getEndTime());
				case 3: return e.getInterruptionTime();
				case 4: return e.getDeltaTime();
				case 5: return e.getPhase().toString();
				case 6: return e.getComments();
			}
			
			return null;
		}
	}
	
	private String getCalendarTime(Calendar calendar) {
		
		StringBuilder sb = new StringBuilder();
		
		String hour = new Integer(calendar.get(Calendar.HOUR_OF_DAY)).toString();
		String min = new Integer(calendar.get(Calendar.MINUTE)).toString();
		
		sb.append(hour);
		sb.append(":");
		sb.append(min.length() == 1 ? "0" : "");
		sb.append(min);
		
		return sb.toString();
	}
	
	private void listSelection_actionPerformed(ListSelectionEvent e) {
		parent.removeEntry.setEnabled(true);
	}
	
}

