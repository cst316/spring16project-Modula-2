package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.Phase;
import net.sf.memoranda.TaskPlanningEntry;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeLogImpl;
import net.sf.memoranda.util.ColorScheme;

public class SummaryPanel extends JPanel {

	PSPPanel parentPanel = null;
	
	private HashMap<Integer, Integer> plannedValue;
	private HashMap<Integer, Integer> actualValue;
	private HashMap<Phase, Integer> timeInPhase;
	private HashMap<Phase, Integer> defectsInjected;
	private HashMap<Phase, Integer> defectsRemoved;
	
	GridBagConstraints gbc;
	private JScrollPane mainScroll;
	private JPanel mainLayout = new JPanel(new GridBagLayout());
	
	private JPanel planningLayout = new JPanel(new GridBagLayout());
	private JScrollPane planningScroll;
	private JPanel planningScrollPanel = new JPanel(new GridBagLayout());
	private JTable planningTotalTable;
	
	private JPanel phaseLayout = new JPanel(new GridBagLayout());
	private JTable phaseTable;
	
	private JPanel injectedLayout = new JPanel(new GridBagLayout());
	private JTable injectedTable;

	private JPanel removedLayout = new JPanel(new GridBagLayout());
	private JTable removedTable;
	
	public SummaryPanel(PSPPanel parent) {
		parentPanel = parent;
		
		plannedValue = new HashMap<>();
		actualValue = new HashMap<>();
		timeInPhase = new HashMap<>();
		defectsInjected = new HashMap<>();
		defectsRemoved = new HashMap<>();
		
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
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        
		this.setBackground(ColorScheme.getColor("taskbar_primary"));
		
		// Planning layout (top)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
		mainLayout.add(planningLayout,gbc);
		
		planningScroll = new JScrollPane(planningScrollPanel);
		
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
		planningLayout.add(planningScroll,gbc);
		
		Object planningTotalRowData[][] = { 
								{ "Total", "Plan", "Actual", "Plan/Actual"},
								{ "Schedule hours", "[double]", "[double]", "[double]"},
								{ "Earned value", "[double]", "[double]", "[double]"} 
							};
		Object planningTotalColumnNames[] = { "", "", "", ""};
		planningTotalTable = new JTable(planningTotalRowData, planningTotalColumnNames);
		planningTotalTable.setEnabled(false);
		planningTotalTable.setBorder(null);
		
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
		planningLayout.add(planningTotalTable,gbc);
		
		// Phase layout (mid)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        mainLayout.add(phaseLayout,gbc);
        
		Object phaseRowData[][] = { 
				{ "Time in Phase (In Minutes)", "Plan", "Actual", "To Date", "To Date %"},
				{ "Planning", "[int]", "[int]", "[int]", "[String]"},
				{ "Design", "[int]", "[int]", "[int]", "[String]"},
				{ "Code", "[int]", "[int]", "[int]", "[String]"},
				{ "Compile", "[int]", "[int]", "[int]", "[String]"},
				{ "Test", "[int]", "[int]", "[int]", "[String]"},
				{ "Postmortem", "[int]", "[int]", "[int]", "[String]"},
				{ "Total", "[int]", "[int]", "[int]", "[String]"}
			};
		Object phaseColumnNames[] = { "", "", "", "", ""};
		phaseTable = new JTable(phaseRowData, phaseColumnNames);
		phaseTable.setEnabled(false);
		phaseTable.setBorder(null);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
		phaseLayout.add(phaseTable,gbc);

        // Injected layout (mid)
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        mainLayout.add(injectedLayout,gbc);
        
		Object injectRowData[][] = { 
				{ "Time in Phase (In Minutes)", "", "Actual", "To Date", "To Date %"},
				{ "Planning", "", "[int]", "[int]", "[String]"},
				{ "Design", "", "[int]", "[int]", "[String]"},
				{ "Code", "", "[int]", "[int]", "[String]"},
				{ "Compile", "", "[int]", "[int]", "[String]"},
				{ "Test", "", "[int]", "[int]", "[String]"},
				{ "Postmortem", "", "[int]", "[int]", "[String]"},
				{ "Total", "", "[int]", "[int]", "[String]"}
			};
		Object injectColumnNames[] = {"","","","",""};
		injectedTable = new JTable(injectRowData, injectColumnNames);
		injectedTable.setEnabled(false);
		injectedTable.setBorder(null);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        injectedLayout.add(injectedTable,gbc);

        // Removed layout (bottom)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        mainLayout.add(removedLayout,gbc);

		Object removedRowData[][] = { 
				{ "Time in Phase (In Minutes)", "", "Actual", "To Date", "To Date %"},
				{ "Planning", "", "[int]", "[int]", "[String]"},
				{ "Design", "", "[int]", "[int]", "[String]"},
				{ "Code", "", "[int]", "[int]", "[String]"},
				{ "Compile", "", "[int]", "[int]", "[String]"},
				{ "Test", "", "[int]", "[int]", "[String]"},
				{ "Postmortem", "", "[int]", "[int]", "[String]"},
				{ "Total", "", "[int]", "[int]", "[String]"}
			};
		Object removedColumnNames[] = { "","","","",""};
		removedTable = new JTable(removedRowData, removedColumnNames);
		removedTable.setEnabled(false);
		removedTable.setBorder(null);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 0; gbc.gridheight = 0; gbc.insets = new Insets(0,0,0,0);
        removedLayout.add(removedTable,gbc);
        
		// Add main scroll to SummaryPanel
		mainScroll = new JScrollPane(mainLayout);

		add(mainScroll);
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
		/*
		List<TaskPlanningEntry> tasks = CurrentProject.getTaskPlanningLog().getLog();
		for (TaskPlanningEntry task : tasks) {
			int plannedWeek = task.getPlannedDate().getWeek();
			int actualWeek = task.getActualDate().getWeek();
			
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
		}
		*/
		
		// Time Log
		List<TimeEntry> entries = CurrentProject.getTimeLog().getLog();
		for (TimeEntry entry : entries) {
			Phase phase = entry.getPhase();
			
			if (phase != null)
				timeInPhase.put(phase, timeInPhase.get(phase) + entry.getDeltaTime());
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
	}
	
}
