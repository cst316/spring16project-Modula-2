package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	
	private HashMap<Integer, Double> plannedHours;
	private HashMap<Integer, Double> actualHours;
	private HashMap<Integer, Integer> plannedValue;
	private HashMap<Integer, Integer> actualValue;
	private HashMap<Phase, Integer> timeInPhase;
	private HashMap<Phase, Integer> defectsInjected;
	private HashMap<Phase, Integer> defectsRemoved;
	private HashMap<Phase, Double> defectsInjectedPercentage;
	private HashMap<Phase, Double> defectsRemovedPercentage;
	private HashMap<Phase, Double> timeInPhasePercentage;
	
	private double totalPlannedHours;
	private double totalActualHours;
	private int totalDefectsInjected;
	private int totalDefectsRemoved;
	private int totalPlannedValue;
	private int totalEarnedValue;
	
	private double totalHoursRatio;
	private double totalValueRatio;
	
	private SummaryPanelCellRenderer cellRenderer = new SummaryPanelCellRenderer();
	
	private JPanel mainLayout = new JPanel(new BorderLayout());
	private JScrollPane mainScroll;
	private JPanel scrollLayout = new JPanel();
	
	private JLabel planningLabel = new JLabel();
	private JPanel planningLayout = new JPanel();
	private JScrollPane planningWeekScrollPane;
	private JPanel planningWeekScrollPanePanel;
	private JTable planningTotalTable;
	
	private JLabel phaseLabel = new JLabel();
	private JPanel phaseLayout = new JPanel(new BorderLayout());
	private JTable phaseTable;
	
	private JLabel injectedLabel = new JLabel();
	private JPanel injectedLayout = new JPanel(new BorderLayout());
	private JTable injectedTable;

	private JLabel removedLabel = new JLabel();
	private JPanel removedLayout = new JPanel(new BorderLayout());
	private JTable removedTable;
	
	public SummaryPanel(PSPPanel parent) {
		parentPanel = parent;
		
		plannedHours = new HashMap<>();
		actualHours = new HashMap<>();
		plannedValue = new HashMap<>();
		actualValue = new HashMap<>();
		timeInPhase = new HashMap<>();
		defectsInjected = new HashMap<>();
		defectsRemoved = new HashMap<>();
		defectsInjectedPercentage = new HashMap<>();
		defectsRemovedPercentage = new HashMap<>();
		timeInPhasePercentage = new HashMap<>();
		
        try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        
        // Refresh summary panel whenever PSP data gets modified
        TimeLogImpl.addTimeLogListener(() -> {
			jbInit();
		});
        
        DefectPanel.addDefectListListener(() -> {
        	jbInit();
        });
        
        TimeLogImpl.addTimeLogListener(() -> {
        	jbInit();
        });
	}
	
	void jbInit() {
		this.removeAll();
		this.revalidate();
		
		this.aggregatePSPData();
		
        scrollLayout.setLayout(new BoxLayout(scrollLayout, BoxLayout.Y_AXIS));
        
		// Planning layout (top)
        planningLabel.setText("Planning Week Summary");
        planningLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        planningLabel.setForeground(ColorScheme.getColor("taskbar_text"));
        scrollLayout.add(planningLabel);
		scrollLayout.add(planningLayout, BorderLayout.WEST);
		
		planningLayout.setLayout(new BoxLayout(planningLayout, BoxLayout.Y_AXIS));
		
		planningWeekScrollPanePanel = new JPanel();
		planningWeekScrollPanePanel.setLayout(new BoxLayout(planningWeekScrollPanePanel, BoxLayout.Y_AXIS));
		planningWeekScrollPanePanel.setBackground(ColorScheme.getColor("taskbar_primary"));
		planningWeekScrollPanePanel.setBorder(null);
		
		// planning layout weeks
		Set<Integer> weeks = new HashSet<Integer>();
		weeks.addAll(plannedValue.keySet());
		weeks.addAll(actualValue.keySet());

		if(weeks.size() > 0) {
			JTable tempTable;
			
			List<Integer> sortedWeeks = new ArrayList<>(weeks);
			Collections.sort(sortedWeeks);
			
			for(int i : sortedWeeks) {
				Object tempRowData[][] = new String[3][4];
				Object tempColumnNames[] = {"","","",""};
				
				tempRowData[0][0] = "Week #" + Integer.toString(i);
				tempRowData[0][1] = "Plan";
				tempRowData[0][2] = "Actual";
				tempRowData[0][3] = "Plan / Actual";
				
				tempRowData[1][0] = "Schedule hours for this week";
				tempRowData[2][0] = "Earned value for this week";
				
				if(plannedHours.containsKey(i)) {
					tempRowData[1][1] = Double.toString(plannedHours.get(i));
				} else {
					tempRowData[1][1] = "0";
				}

				if(plannedValue.containsKey(i)) {
					tempRowData[2][1] = Double.toString(plannedValue.get(i));
				} else {
					tempRowData[2][1] = "0";
				}
				
				if(actualHours.containsKey(i)) {
					tempRowData[1][2] = Double.toString(actualHours.get(i));
				} else {
					tempRowData[1][2] = "0";
				}

				if(actualValue.containsKey(i)) {
					tempRowData[2][2] = Double.toString(actualValue.get(i));
				} else {
					tempRowData[2][2] = "0";
				}

				if(actualHours.containsKey(i)) {
					double temp = (plannedHours.containsKey(i) ? plannedHours.get(i) : 0);
					tempRowData[1][3] = Double.toString(Math.round(temp/actualHours.get(i)*100.0)/100.0);
				} else {
					tempRowData[1][3] = "Not applicable";
				}

				if(actualValue.containsKey(i)) {
					double temp = (plannedValue.containsKey(i) ? plannedValue.get(i) : 0);
					tempRowData[2][3] = Double.toString(Math.round(temp/actualValue.get(i)*100.0)/100.0);
				} else {
					tempRowData[2][3] = "Not applicable";
				}

				tempTable = new JTable(tempRowData, tempColumnNames);
				tempTable.setEnabled(false);
				tempTable.setBorder(null);
				
				tempTable.getColumnModel().getColumn(0).setMaxWidth(384);
				for(int j = 1; j < 4; j++) {
					tempTable.getColumnModel().getColumn(j).setMaxWidth(128);
					tempTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
				tempTable.setDefaultRenderer(Object.class, cellRenderer);
				tempTable.setBackground(ColorScheme.getColor("taskbar_primary"));
				
				planningWeekScrollPanePanel.add(tempTable, BorderLayout.WEST);
				planningWeekScrollPanePanel.add(Box.createRigidArea(new Dimension(8,8)));
			}
		}

		planningWeekScrollPane = new JScrollPane(planningWeekScrollPanePanel);
		planningWeekScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE,128));
		planningWeekScrollPane.setPreferredSize(new Dimension(1152,128));
		planningWeekScrollPane.setBackground(ColorScheme.getColor("taskbar_primary"));
		planningWeekScrollPane.setBorder(null);
		
		planningLayout.setMaximumSize(new Dimension(1152,Integer.MAX_VALUE));
		planningLayout.add(planningWeekScrollPane, BorderLayout.WEST);
		
		planningLayout.add(Box.createRigidArea(new Dimension(16,16)));
		
		// planning layout total
		Object planningTotalRowData[][] = { 
								{ "Total", "Plan", "Actual", "Plan/Actual"},
								{ "Schedule hours", "[double]", "[double]", "[double]"},
								{ "Earned value", "[double]", "[double]", "[double]"} 
							};
		Object planningTotalColumnNames[] = { "", "", "", ""};
		
		planningTotalRowData[1][1] = totalPlannedHours;
		planningTotalRowData[1][2] = totalActualHours;
		planningTotalRowData[1][3] = Double.toString(Math.round(totalHoursRatio*100.0)/100.0);

		planningTotalRowData[2][1] = totalPlannedValue;
		planningTotalRowData[2][2] = totalEarnedValue;
		planningTotalRowData[2][3] = Double.toString(Math.round(totalValueRatio*100.0)/100.0);
		
		planningTotalTable = new JTable(planningTotalRowData, planningTotalColumnNames);
		planningTotalTable.setEnabled(false);
		planningTotalTable.setBorder(null);
		planningTotalTable.getColumnModel().getColumn(0).setMaxWidth(384);
		for(int j = 1; j < 4; j++) {
			planningTotalTable.getColumnModel().getColumn(j).setMaxWidth(128);
			planningTotalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		planningTotalTable.setDefaultRenderer(Object.class, cellRenderer);
		planningTotalTable.setBackground(ColorScheme.getColor("taskbar_primary"));
		
		planningLayout.setBackground(ColorScheme.getColor("taskbar_primary"));
		planningLayout.add(planningTotalTable, BorderLayout.WEST);
		
		// Phase layout (mid)
		Phase [] phases = Phase.values();
		
		phaseLabel.setText("Phase Summary");
        phaseLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        phaseLabel.setForeground(ColorScheme.getColor("taskbar_text"));
        scrollLayout.add(phaseLabel);
        scrollLayout.add(phaseLayout);
        
		Object phaseRowData[][] = { 
				{ "Time in Phase (In Minutes)", "", "Actual", "To Date %"},
				{ "Planning", "", "[int]", "[String]"},
				{ "Design", "", "[int]", "[String]"},
				{ "Code", "", "[int]", "[String]"},
				{ "Code Review", "", "[int]", "[String]"},
				{ "Compile", "", "[int]", "[String]"},
				{ "Test", "", "[int]", "[String]"},
				{ "Postmortem", "", "[int]", "[String]"},
				{ "Total", "", "[int]", "[String]"}
			};
		
		// populate table
		for (int i = 0; i < phases.length; i++) {
			phaseRowData[i+1][2] = timeInPhase.get(phases[i]);
			if (timeInPhasePercentage.get(phases[i]) != null)
				phaseRowData[i+1][3] =  Math.round(timeInPhasePercentage.get(phases[i])*100d) + "%";
			else
				phaseRowData[i+1][3] = "0%";
		}
		
		phaseRowData[8][2] = totalActualHours;
		phaseRowData[8][3] = "100%";
		
		Object phaseColumnNames[] = { "", "", "", ""};
		phaseTable = new JTable(phaseRowData, phaseColumnNames);
		phaseTable.setEnabled(false);
		phaseTable.setBorder(null);
		phaseTable.getColumnModel().getColumn(0).setMaxWidth(384);
		for(int j = 1; j < 4; j++) {
			phaseTable.getColumnModel().getColumn(j).setMaxWidth(128);
			phaseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		phaseTable.setDefaultRenderer(Object.class, cellRenderer);
		phaseTable.setBackground(ColorScheme.getColor("taskbar_primary"));
        
		phaseLayout.setMaximumSize(new Dimension(1152,Integer.MAX_VALUE));
		phaseLayout.add(phaseTable, BorderLayout.CENTER);

        // Injected layout (mid)
        injectedLabel.setText("Injected Bugs");
        injectedLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        injectedLabel.setForeground(ColorScheme.getColor("taskbar_text"));
        scrollLayout.add(injectedLabel);
        scrollLayout.add(injectedLayout);
        
		Object injectRowData[][] = { 
				{ "Defects Injected", "", "Actual", "To Date %"},
				{ "Planning", "", "[int]", "[String]"},
				{ "Design", "", "[int]", "[String]"},
				{ "Code", "", "[int]", "[String]"},
				{ "Code Review", "", "[int]", "[String]"},
				{ "Compile", "", "[int]", "[String]"},
				{ "Test", "", "[int]", "[String]"},
				{ "Postmortem", "", "[int]", "[String]"},
				{ "Total", "", "[int]", "[String]"}
			};
		
		// populate table
		for (int i = 0; i < phases.length; i++) {
			injectRowData[i+1][2] = defectsInjected.get(phases[i]);
			if (defectsInjectedPercentage.get(phases[i]) != null)
				injectRowData[i+1][3] =  Math.round(defectsInjectedPercentage.get(phases[i])*100d) + "%";
			else
				injectRowData[i+1][3] = "0%";
		}
		
		injectRowData[8][2] = totalDefectsInjected;
		injectRowData[8][3] = "100%";
		
		Object injectColumnNames[] = {"","","",""};
		injectedTable = new JTable(injectRowData, injectColumnNames);
		injectedTable.setEnabled(false);
		injectedTable.setBorder(null);
		injectedTable.getColumnModel().getColumn(0).setMaxWidth(384);
		for(int j = 1; j < 4; j++) {
			injectedTable.getColumnModel().getColumn(j).setMaxWidth(128);
			injectedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		injectedTable.setDefaultRenderer(Object.class, cellRenderer);
		injectedTable.setBackground(ColorScheme.getColor("taskbar_primary"));
		
		injectedLayout.setMaximumSize(new Dimension(1152,Integer.MAX_VALUE));
        injectedLayout.add(injectedTable, BorderLayout.CENTER);

        // Removed layout (bottom)
        removedLabel.setText("Removed Bugs");
        removedLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        removedLabel.setForeground(ColorScheme.getColor("taskbar_text"));
        scrollLayout.add(removedLabel);
        scrollLayout.add(removedLayout);

		Object removedRowData[][] = { 
				{ "Defects Removed", "", "Actual", "To Date %"},
				{ "Planning", "", "[int]", "[String]"},
				{ "Design", "", "[int]", "[String]"},
				{ "Code", "", "[int]", "[String]"},
				{ "Code Review", "", "[int]", "[String]"},
				{ "Compile", "", "[int]", "[String]"},
				{ "Test", "", "[int]", "[String]"},
				{ "Postmortem", "", "[int]", "[String]"},
				{ "Total", "", "[int]", "[String]"}
			};
		
		// populate table
		for (int i = 0; i < phases.length; i++) {
			removedRowData[i+1][2] = defectsRemoved.get(phases[i]);
			if (defectsRemovedPercentage.get(phases[i]) != null)
				removedRowData[i+1][3] =  Math.round(defectsRemovedPercentage.get(phases[i])*100d) + "%";
			else
				removedRowData[i+1][3] = "0%";
		}
		
		removedRowData[8][2] = totalDefectsRemoved;
		removedRowData[8][3] = "100%";
		
		Object removedColumnNames[] = {"","","",""};
		removedTable = new JTable(removedRowData, removedColumnNames);
		removedTable.setEnabled(false);
		removedTable.setBorder(null);
		removedTable.getColumnModel().getColumn(0).setMaxWidth(384);
		for(int j = 1; j < 4; j++) {
			removedTable.getColumnModel().getColumn(j).setMaxWidth(128);
			removedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		removedTable.setDefaultRenderer(Object.class, cellRenderer);
		removedTable.setBackground(ColorScheme.getColor("taskbar_primary"));
		
		removedLayout.setMaximumSize(new Dimension(1152,Integer.MAX_VALUE));
        removedLayout.add(removedTable, BorderLayout.CENTER);
        
        // scrollLayout
		scrollLayout.setBackground(ColorScheme.getColor("taskbar_primary"));
		scrollLayout.setBorder(null);
        
		// mainScroll
		mainScroll = new JScrollPane(scrollLayout, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainScroll.setBackground(ColorScheme.getColor("taskbar_primary"));
		mainScroll.setBorder(null);
	
		// SummaryPanel
		this.setBackground(ColorScheme.getColor("taskbar_primary"));
		this.setLayout(new BorderLayout());
		this.add(mainScroll,BorderLayout.CENTER);
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
			Phase injectionPhase = defect.getInjection();
			Phase removalPhase = defect.getRemove();
			
			if (injectionPhase != null)
				defectsInjected.put(injectionPhase, defectsInjected.get(injectionPhase) + 1);
			if (removalPhase != null) {
				defectsRemoved.put(removalPhase, defectsRemoved.get(removalPhase) + 1);
			}
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
		
		// To Date (%)
		for (Phase phase : Phase.values()) {
			timeInPhasePercentage.put(phase, (double) timeInPhase.get(phase) / totalActualHours);
			defectsInjectedPercentage.put(phase, (double) defectsInjected.get(phase) / totalDefectsInjected);
			defectsRemovedPercentage.put(phase, (double) defectsRemoved.get(phase) / totalDefectsRemoved);
		}
	}
	
}
