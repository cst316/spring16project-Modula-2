package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	WorkPanel parentPanel = null;
	JPanel mainPanel = new JPanel(new BorderLayout());
	CalendarTaskbarPanel taskPanel = new CalendarTaskbarPanel();
	JPanel periodPanel = new JPanel(new GridBagLayout());
	
    public CalendarPanel(WorkPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
    	mainPanel.add(taskPanel,BorderLayout.NORTH);
    	mainPanel.add(periodPanel,BorderLayout.SOUTH);
    	this.add(mainPanel);
    }
}
