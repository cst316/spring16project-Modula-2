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
	JPanel cardPanel;
	private CardLayout cardLayout;
	
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
		calendarPanel = new CalendarPanel(null);
		cardLayout = new CardLayout();
		
		taskbar = new PSPTaskbarPanel(null);
		taskbar.setBorder(null);
		
		taskbar.setMaximumSize(new Dimension(500, 42));
		taskbar.setPreferredSize(new Dimension(300, 42));
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		cardPanel.add(timeLogPanel, "TIMELOG");
		cardPanel.add(calendarPanel, "CALENDAR");
		
		setLayout(new BorderLayout());
		add(cardPanel, BorderLayout.CENTER);
		add(taskbar, BorderLayout.NORTH);
	}
}
