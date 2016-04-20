package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import net.sf.memoranda.util.ColorScheme;

public class PSPPanel extends JPanel {
	
	private WorkPanel parentPanel = null;
	
	private int _view = PSPPanel.VIEW_INVALID;
	
	public static final int VIEW_INVALID = -1;
	public static final int VIEW_SUMMARY = 0;
	public static final int VIEW_TIMELOG = 1;
	public static final int VIEW_DEFECTLOG = 2;
	public static final int VIEW_TASKPLANNINGLOG = 3;
	
	private PSPTaskbarPanel taskbar;
	
	// switch between PSPPanels
	JPanel cardPanel;
	private CardLayout cardLayout;
	
	private TimeLogPanel timeLogPanel;
	private PSPDefectPanel defectPanel;
	private TaskPlanningLogPanel taskPlanningLogPanel;
	private SummaryPanel summaryPanel;
	
	public PSPPanel(WorkPanel parentPanel) {
		
		this.parentPanel = parentPanel;
		try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
		
		setVisible(true);
	}
	
	void jbInit() {
		timeLogPanel = new TimeLogPanel(this);
		defectPanel = new PSPDefectPanel(this);
		taskPlanningLogPanel = new TaskPlanningLogPanel(this);
		summaryPanel = new SummaryPanel(this);
		
		cardLayout = new CardLayout();
		
		taskbar = new PSPTaskbarPanel(this);
		taskbar.setBorder(null);
		
		taskbar.setMaximumSize(new Dimension(500, 42));
		taskbar.setPreferredSize(new Dimension(300, 42));
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		cardPanel.add(summaryPanel, "SUMMARY");
		cardPanel.add(timeLogPanel, "TIMELOG");
		cardPanel.add(defectPanel, "DEFECTLOG");
		cardPanel.add(taskPlanningLogPanel, "TASKPLANNINGLOG");
		
		setLayout(new BorderLayout());
		add(cardPanel, BorderLayout.CENTER);
		add(taskbar, BorderLayout.NORTH);
		
		updateView(PSPPanel.VIEW_SUMMARY);
	}
	
	public void updateView(int view) {
		if(view < PSPPanel.VIEW_SUMMARY || view > PSPPanel.VIEW_TASKPLANNINGLOG)
			return;
		
		_view = view;
		
		taskbar.btnSummary.setBackground(ColorScheme.getColor("button_primary"));
		taskbar.btnTimeRecording.setBackground(ColorScheme.getColor("button_primary"));
		taskbar.btnDefect.setBackground(ColorScheme.getColor("button_primary"));
		taskbar.btnTaskPlanning.setBackground(ColorScheme.getColor("button_primary"));
		
		if(_view == PSPPanel.VIEW_SUMMARY) {
			cardLayout.show(cardPanel, "SUMMARY");
			taskbar.btnSummary.setBackground(ColorScheme.getColor("frame_secondary"));
		}
		else if(_view == PSPPanel.VIEW_TIMELOG) {
			cardLayout.show(cardPanel, "TIMELOG");
			taskbar.btnTimeRecording.setBackground(ColorScheme.getColor("frame_secondary"));
		}
		else if(_view == PSPPanel.VIEW_DEFECTLOG) {
			cardLayout.show(cardPanel, "DEFECTLOG");
			taskbar.btnDefect.setBackground(ColorScheme.getColor("frame_secondary"));
		}
		else if(_view == PSPPanel.VIEW_TASKPLANNINGLOG) {
			cardLayout.show(cardPanel, "TASKPLANNINGLOG");
			taskbar.btnTaskPlanning.setBackground(ColorScheme.getColor("frame_secondary"));
		}
	}
}
