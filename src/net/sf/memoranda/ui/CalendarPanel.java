package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	WorkPanel parentPanel = null;

	JPanel mainPanel = new JPanel(new GridBagLayout());
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
    	
		String[] namesOfDays = new String[] {
			    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
		};
	
    	for(int weekday = 0; weekday < 7; weekday++) {
    		for(int row = 0; row < 6; row++) {
    			JPanel gridCell = new JPanel();
    			gridCell.setBackground(Color.WHITE);
    	        
    			if(row != 0) {
	    			gridCell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        	        gbc = new GridBagConstraints();
        	        gbc.gridy = 0;       	        
    				JLabel gridLabel = new JLabel();
    				gridLabel.setText(Integer.toString(weekday+7*(row-1)));
    				gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
    				gridCell.add(gridLabel,gbc);

    				gbc = new GridBagConstraints();
        	        gbc.gridy = 1;
	    			CalendarNode calendarNode = new CalendarNode();
	    			gridCell.add(calendarNode,gbc);

        	        gbc = new GridBagConstraints();
        	        gbc.gridx = weekday; gbc.gridy = row;
        	        gbc.anchor = GridBagConstraints.CENTER;
	    			gbc.fill = GridBagConstraints.BOTH;
	    	        gbc.weightx = 1.0;
	    	        gbc.weighty = 1.0;
	    	        gridPanel.add(gridCell, gbc);
    			} else {
        	        gbc = new GridBagConstraints();
    				JLabel gridLabel = new JLabel();
    				gridLabel.setText(namesOfDays[weekday]);
    				gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
    				gridCell.add(gridLabel,gbc);
    				
        	        gbc = new GridBagConstraints();
        	        gbc.gridx = weekday; gbc.gridy = row;
        	        gbc.anchor = GridBagConstraints.CENTER;
    				gbc.fill = GridBagConstraints.HORIZONTAL;
    				gbc.weightx = 1.0;
    				
	    	        gridPanel.add(gridCell, gbc);
    			}
    		}
    	}

    	// TaskBar
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
    	mainPanel.add(taskPanel,gbc);

    	// GridPanel
        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    	mainPanel.add(gridPanel,gbc);

    	// CalendarPanel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        this.setLayout(new GridBagLayout());
    	this.add(mainPanel,gbc);
    }
}
