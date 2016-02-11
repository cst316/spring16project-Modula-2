package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.memoranda.Event;
import net.sf.memoranda.Task;

public class CalendarNode extends JPanel {	
	
	public CalendarNode() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void addTask(Task task) {
		add(new CalendarNodeItem(task));
		//add(Box.createRigidArea(new Dimension(12, 12)));
	}
	
	public void addEvent(Event event) {
		add(new CalendarNodeItem(event));
		//add(Box.createRigidArea(new Dimension(12, 12)));
	}
	
	public void clear() {
		this.removeAll();
	}
	
	private class CalendarNodeItem extends JPanel {
		private GridBagConstraints c;
		private Event event;
		private Task task;
		
		private CalendarNodeItem() {
			this.setLayout(new GridBagLayout());
			this.setOpaque(true);
			this.setBackground(Color.WHITE);
			
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
		}
		
		private CalendarNodeItem(Task task) {
			this();
			this.task = task;
			
			c.gridx = 0;
			c.gridy = 0;
						
			if(task.getStartDate() != null && task.getEndDate() != null && !task.getStartDate().equals(task.getEndDate())) {
				c.gridwidth = 2;
				JLabel taskName = new JLabel(task.getText());
				taskName.setFont(new Font(taskName.getFont().getName(), Font.BOLD, 14));
				add(taskName, c);

				c.gridwidth = 1;
				c.gridy = 1;
				c.gridx = 0;
				add(new JLabel("Start Date: " + task.getStartDate().getShortDateString()), c);
				c.gridx = 1;
				add(new JLabel("End Date: " + task.getEndDate().getShortDateString()), c);
				
				c.gridwidth = 2;
				c.gridy = 2;
				add(new JLabel(task.getProgress() + "% complete"), c);
			} else {
				JLabel taskName = new JLabel(task.getText());
				taskName.setFont(new Font(taskName.getFont().getName(), Font.BOLD, 14));
				add(taskName, c);

				c.gridy = 1;
				add(new JLabel(task.getProgress() + "% complete"), c);
			}
		}
		
		private CalendarNodeItem(Event event) {
			this();
			this.event = event;
			
			c.gridx = 0;
			c.gridy = 0;
						
			if (event.getStartDate() != null && event.getEndDate() != null && !event.getStartDate().equals(event.getEndDate())) {
				c.gridwidth = 2;
				JLabel eventName = new JLabel(event.getText());
				eventName.setFont(new Font(eventName.getFont().getName(), Font.BOLD, 14));
				add(eventName, c);

				c.gridwidth = 1;
				c.gridy = 1;
				c.gridx = 0;
				add(new JLabel("Start Date: " + event.getStartDate().getShortDateString()), c);
				c.gridx = 1;
				add(new JLabel("End Date: " + event.getEndDate().getShortDateString()), c);
				
				c.gridy = 2;
				c.gridx = 0; 
				add(new JLabel(event.getTimeString()), c);
				c.gridx = 1;
				add(new JLabel(event.getTimeString()), c);
			} else {
				JLabel eventName = new JLabel(event.getText());
				eventName.setFont(new Font(eventName.getFont().getName(), Font.BOLD, 14));
				add(eventName, c);

				c.gridy = 1;
				add(new JLabel(event.getTimeString()), c);
			}
		}
	}
}
