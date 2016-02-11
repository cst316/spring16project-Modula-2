package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.memoranda.date.CalendarDate;

public class CalendarPanelCell extends JPanel {
	private JPanel gridCell = new JPanel(new GridBagLayout());
	private JLabel gridLabel = new JLabel();
	private CalendarNode calendarNode = new CalendarNode();
	private CalendarDate _date = new CalendarDate(0,0,0);
	private boolean isActive = false;
	
	GridBagConstraints gbc;
	
	public CalendarPanelCell() {
		gridCell.setBackground(Color.WHITE);
		gridCell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			
	    gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.NORTH;
	    gbc.gridy = 0;
		gridLabel.setText("");
		gridLabel.setFont(gridLabel.getFont().deriveFont(12.0f));
		gridCell.add(gridLabel,gbc);
	
	    gbc.anchor = GridBagConstraints.CENTER;
		gbc = new GridBagConstraints();
	    gbc.gridy = 1;
	    gbc.weighty = 1.0;
		gridCell.add(calendarNode,gbc);
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean active) {
		isActive = active;
		
		if(active) {
			gridCell.setBackground(Color.WHITE);
		} else {
			gridLabel.setText("");
			gridCell.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	public JPanel getCell() {
		return gridCell;
	}
	
	public JLabel getLabel() {
		return gridLabel;
	}
	
	public CalendarNode getCalendarNode() {
		return calendarNode;
	}
	
	public CalendarDate getCalendarDate() {
		return _date;
	}
	
	public void setCalendarDate(CalendarDate date) {
		_date = date;
	}
}
