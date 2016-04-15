package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class PSPPanel extends JPanel {
	
	private WorkPanel parentPanel = null;
	
	private PSPTaskbarPanel taskbar;
	
	// switch between PSPPanels
	JPanel cardPanel;
	private CardLayout cardLayout;
	
	private TimeLogPanel timeLogPanel;
	private PSPDefectPanel defectPanel;
	private TaskPlanningLogPanel taskPlanningLogPanel;
	
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
		cardLayout = new CardLayout();
		
		taskbar = new PSPTaskbarPanel(this);
		taskbar.setBorder(null);
		
		taskbar.setMaximumSize(new Dimension(500, 42));
		taskbar.setPreferredSize(new Dimension(300, 42));
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		cardPanel.add(timeLogPanel, "TIMELOG");
		cardPanel.add(defectPanel, "DEFECTLOG");
		cardPanel.add(taskPlanningLogPanel, "TASKPLANNINGLOG");
		
		setLayout(new BorderLayout());
		add(cardPanel, BorderLayout.CENTER);
		add(taskbar, BorderLayout.NORTH);
	}
	
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	public JPanel getCardPanel() {
		return cardPanel;
	}
}
