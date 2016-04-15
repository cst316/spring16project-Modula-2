package net.sf.memoranda.ui;

import java.util.Calendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.TaskPlanningEntry;
import net.sf.memoranda.TaskPlanningLogImpl;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeLogImpl;
import net.sf.memoranda.ui.TimeLogTable.TimeLogTableModel;
import net.sf.memoranda.util.Local;

public class TaskPlanningLogTable extends JTable {

	TaskPlanningLogPanel parent;
	List<TaskPlanningEntry> entries;
	
	public TaskPlanningLogTable(TaskPlanningLogPanel parent) {
		super();
		
		initTable();
		setModel(new TaskPlanningTableModel());
		this.parent = parent;
		
		ListSelectionModel selectionModel = getSelectionModel();
		selectionModel.addListSelectionListener(e -> {
			listSelection_actionPerformed(e);
		});
		
		TaskPlanningLogImpl.addTaskPlanningLogListener(() -> {
			tableChanged();
		});
	}
	
	public void initTable() {
		entries = CurrentProject.getTaskPlanningLog().getLog();
	}
	
	public void tableChanged() {
		initTable();
		updateUI();
	}
	
	class TaskPlanningTableModel extends AbstractTableModel {
		
		final String[] columnNames = {
                Local.getString("Task #"),
                Local.getString("Task Name"),
                Local.getString("Planned Hours"),
                Local.getString("Planned Value"),
                Local.getString("Cumulative Hours"),
                Local.getString("Cumulative PV"),
                Local.getString("Planned Date"),
                Local.getString("Actual Date"),
                Local.getString("Earned Value"),
                Local.getString("Cumulative EV")
        };

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
			TaskPlanningEntry e = entries.get(row);
			switch (col) {
				case 0: return e.getTaskNumber();
				case 1: return e.getTaskName();
				case 2: return e.getPlannedHours();
				case 3: return e.getPV();
				case 4: return CurrentProject.getTaskPlanningLog().getCumulativeHours(e);
				case 5: return CurrentProject.getTaskPlanningLog().getCumulativePV(e);
				case 6: return e.getPlannedDateWeek();
				case 7: return e.getActualDateWeek();
				case 8: return e.getEV();
				case 9: return CurrentProject.getTaskPlanningLog().getCumulativeEV(e);
			}
			
			return null;
		}
	}
	
	private void listSelection_actionPerformed(ListSelectionEvent e) {
		parent.removeEntry.setEnabled(true);
	}
	
}
