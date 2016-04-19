package net.sf.memoranda.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.Phase;
import net.sf.memoranda.TaskPlanningEntry;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeLogImpl;

public class SummaryPanel extends JPanel {

	PSPPanel parentPanel = null;
	
	private HashMap<Integer, Double> plannedHours;
	private HashMap<Integer, Double> actualHours;
	private HashMap<Integer, Integer> plannedValue;
	private HashMap<Integer, Integer> actualValue;
	private HashMap<Phase, Integer> timeInPhase;
	private HashMap<Phase, Integer> defectsInjected;
	private HashMap<Phase, Integer> defectsRemoved;
	private HashMap<Phase, Integer> defectsInjectedToDate;
	private HashMap<Phase, Integer> defectsInjectedToDatePercentage;
	private HashMap<Phase, Integer> defectsRemovedToDate;
	private HashMap<Phase, Integer> defectsRemovedToDatePercentage;
	
	private double totalPlannedHours;
	private double totalActualHours;
	private int totalDefectsInjected;
	private int totalDefectsRemoved;
	private int totalPlannedValue;
	private int totalEarnedValue;
	
	private double totalHoursRatio;
	private double totalValueRatio;
	
	public SummaryPanel(PSPPanel parent) {
		parentPanel = parent;
		
		plannedHours = new HashMap<>();
		actualHours = new HashMap<>();
		plannedValue = new HashMap<>();
		actualValue = new HashMap<>();
		timeInPhase = new HashMap<>();
		defectsInjected = new HashMap<>();
		defectsRemoved = new HashMap<>();
		defectsInjectedToDate = new HashMap<>();
		defectsInjectedToDatePercentage = new HashMap<>();
		defectsRemovedToDate = new HashMap<>();
		defectsRemovedToDatePercentage = new HashMap<>();
		
        try {
        	aggregatePSPData();
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        
        // Refresh summary panel whenever PSP data gets modified
        TimeLogImpl.addTimeLogListener(() -> {
			jbInit();
		});
        
        PSPDefectPanel.addDefectListListener(() -> {
        	jbInit();
        });
        
        TimeLogImpl.addTimeLogListener(() -> {
        	jbInit();
        });
	}
	
	void jbInit() {
		
	}
	
	@SuppressWarnings("unchecked")
	private void aggregatePSPData() {
		
		// initialize maps
		for (Phase phase : Phase.values()) {
			timeInPhase.put(phase, 0);
			defectsInjected.put(phase, 0);
			defectsRemoved.put(phase, 0);
		}
		
		// Planning Log
		List<TaskPlanningEntry> tasks = CurrentProject.getTaskPlanningLog().getLog();
		for (TaskPlanningEntry task : tasks) {
			int plannedWeek = task.getPlannedDate().getWeek();
			int actualWeek = task.getActualDate().getWeek();
			
			// PV/EV
			if (plannedValue.containsKey(plannedWeek))
				plannedValue.put(plannedWeek, plannedValue.get(plannedWeek) + task.getPV());
			else
				plannedValue.put(plannedWeek, task.getPV());
			
			if (task.isComplete()) {
				if (actualValue.containsKey(actualWeek))
					actualValue.put(actualWeek, actualValue.get(actualWeek) + task.getEV());
				else
					actualValue.put(actualWeek, task.getPV());
			}
			
			// Planned Hours
			if (plannedHours.containsKey(plannedWeek))
				plannedHours.put(plannedWeek, plannedHours.get(plannedWeek) + task.getPlannedHours());
			else
				plannedHours.put(plannedWeek, task.getPlannedHours());
		}
		
		// Time Log
		List<TimeEntry> entries = CurrentProject.getTimeLog().getLog();
		for (TimeEntry entry : entries) {
			int week = entry.getCalendarDate().getWeek();
			Phase phase = entry.getPhase();
			
			// Time Spent in Phases
			if (phase != null)
				timeInPhase.put(phase, timeInPhase.get(phase) + entry.getDeltaTime());
			
			// Time Spent Per Week
			if (actualHours.containsKey(week))
				actualHours.put(week, actualHours.get(week) + entry.getDeltaTime());
			else
				actualHours.put(week, (double) entry.getDeltaTime());
		}
		
		// Defect Log
		Collection<Defect> defects = CurrentProject.getDefectList().getAllDefects();
		for (Defect defect : defects) {
			Phase injectionPhase = Phase.valueOf(defect.getInjection());
			Phase removalPhase = Phase.valueOf(defect.getRemove());
			
			if (injectionPhase != null)
				defectsInjected.put(injectionPhase, defectsInjected.get(injectionPhase) + 1);
			if (removalPhase != null)
				defectsRemoved.put(removalPhase, defectsInjected.get(removalPhase) + 1);
		}
		
		// Totals
		totalActualHours = 0;
		totalPlannedHours = 0;
		totalDefectsInjected = 0;
		totalDefectsRemoved = 0;
		totalPlannedValue = 0;
		totalEarnedValue = 0;
		
		for (int key : plannedHours.keySet())
			totalPlannedHours += plannedHours.get(key);
		for (int key : actualHours.keySet())
			totalActualHours += actualHours.get(key);
		for (Phase key : defectsInjected.keySet())
			totalDefectsInjected += defectsInjected.get(key);
		for (Phase key : defectsRemoved.keySet())
			totalDefectsRemoved += defectsRemoved.get(key);
		for (int key : plannedValue.keySet())
			totalPlannedValue += plannedValue.get(key);
		for (int key : actualValue.keySet())
			totalEarnedValue += actualValue.get(key);
		
		totalHoursRatio = (double) totalPlannedHours / totalActualHours;
		totalValueRatio = (double) totalPlannedValue / totalEarnedValue;
		
		
	}
	
}
