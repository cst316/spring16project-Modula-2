package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	WorkPanel parentPanel = null;
	JPanel mainPanel = new JPanel(new BorderLayout());
	CalendarTaskbarPanel taskPanel = new CalendarTaskbarPanel(this);
	JPanel gridPanel = new JPanel(new GridBagLayout());
	
	GridBagConstraints gbc;
	
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
    	for(int weekday = 0; weekday < 7; weekday++) {
    		for(int row = 0; row < 5; row++) {
    			JPanel gridCell = new JPanel();
    			
    			CalendarNode calendarNode = new CalendarNode();
    			gridCell.add(calendarNode);
    			gridCell.setBackground(Color.BLACK);
    			
    	        gbc = new GridBagConstraints();
    	        gbc.gridx = weekday; gbc.gridy = row;
    	        gbc.insets = new Insets(0, 0, 0, 0);
    	        gbc.anchor = GridBagConstraints.CENTER;
    	        gridPanel.add(gridCell, gbc);
    		}
    	}
    	
    	mainPanel.add(taskPanel,BorderLayout.NORTH);
    	mainPanel.add(gridPanel,BorderLayout.SOUTH);
    	this.add(mainPanel);
    }
}
