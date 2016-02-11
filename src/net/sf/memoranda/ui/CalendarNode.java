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

public class CalendarNode extends JPanel
{	
	public CalendarNode() { }
	
	public CalendarNode(String day)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel(day));
	}
	
	public void addTask(Task task)
	{
		add(new CalendarNodeItem(task));
		add(Box.createRigidArea(new Dimension(12, 12)));
	}
	
	public void addEvent(Event event)
	{
		add(new CalendarNodeItem(event));
		add(Box.createRigidArea(new Dimension(12, 12)));
	}
	
	private class CalendarNodeItem extends JPanel
	{
		private GridBagConstraints c;
		private Event event;
		private Task task;
		
		private CalendarNodeItem()
		{
			this.setLayout(new GridBagLayout());
			this.setOpaque(true);
			this.setBackground(Color.WHITE);
			
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
		}
		
		private CalendarNodeItem(Task task)
		{
			this();
			this.task = task;
			
			c.gridx = 0;
			c.gridy = 0;
			
			JLabel taskName = new JLabel(task.getText());
			taskName.setFont(new Font(taskName.getFont().getName(), Font.BOLD, 14));
			
			add(taskName, c);
			
			c.gridy = 1;
			
			if (task.getStartDate() != null)
			{
				add(new JLabel("Start Date: " + task.getStartDate().getShortDateString()), c);
			}
			
			c.gridy = 2;
			
			if (task.getEndDate() != null)
			{
				add(new JLabel("End Date: " + task.getEndDate().getShortDateString()), c);
			}
		}
		
		private CalendarNodeItem(Event event)
		{
			this();
			this.event = event;
			
			c.gridx = 0;
			c.gridy = 0;
			
			JLabel eventName = new JLabel(event.getText());
			eventName.setFont(new Font(eventName.getFont().getName(), Font.BOLD, 14));
			
			add(eventName, c);
			
			c.gridy = 1;
			
			if (event.getStartDate() != null)
			{
				add(new JLabel("Start Date: " + event.getStartDate().getShortDateString()), c);
			}
			
			c.gridy = 2;
			
			if (event.getEndDate() != null)
			{
				add(new JLabel("End Date: " + event.getEndDate().getShortDateString()), c);
			}
		}
	}
}
