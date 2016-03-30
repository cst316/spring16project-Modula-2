package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PSPPanel extends JPanel {
	
	private WorkPanel parentPanel = null;
	
	private JPanel panel;
	private PSPTaskbarPanel taskbar;
	
	// switch between PSPPanels
	private JPanel cardPanel;
	private CardLayout cardLayout;
	
	private PSPDefectPanel defectPanel;
	private TimeLogPanel timeLogPanel;
	private CalendarPanel calendarPanel;
	
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
		cardLayout = new CardLayout();
		
		taskbar = new PSPTaskbarPanel(this);
		taskbar.setBorder(null);
		
		taskbar.setMaximumSize(new Dimension(500, 100));
		taskbar.setPreferredSize(new Dimension(300, 100));
		taskbar.setMinimumSize(new Dimension(1, 100));
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		cardPanel.add(timeLogPanel, "TIMELOG");
		cardPanel.add(defectPanel, "DEFECTLOG");
		
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
