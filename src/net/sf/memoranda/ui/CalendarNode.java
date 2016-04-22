package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.memoranda.Event;
import net.sf.memoranda.Task;

public class CalendarNode extends JPanel {	
	private Vector<CalendarNodeItem> queueNodes;

	public static final int TYPE_INVALID = 0;
	public static final int TYPE_EVENT = 1;
	public static final int TYPE_TASK = 2;
	public static final int TYPE_LABEL = 3;
	
	public CalendarNode() {
		queueNodes = new Vector<CalendarNodeItem>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	}
	
	public void queueAdd(Task task) {
		queueNodes.add(new CalendarNodeItem(task));
	}
	
	public void queueAdd(Event event) {
		queueNodes.add(new CalendarNodeItem(event));
	}
	
	private int queueRemoveLast() {
		int type = queueNodes.get(queueNodes.size()-1).getType();
		queueNodes.remove(queueNodes.size()-1);
		return type;
	}
	
	public void queueProcess(int maxDisplay) {
		int curDisplay = 0;
		int overDisplay = 0;
		int overEventDisplay = 0;
		int overTaskDisplay = 0;
			
		if(queueNodes.size() == 0)
			return;
				
		for(int i = 0; i < queueNodes.size(); i++) {
			CalendarNodeItem node = queueNodes.get(i);

			if((curDisplay+1) >= maxDisplay) {
				int queueCurType = node.getType();
				
				// If this is the first to go over, remove the last element and fix the overdisplay to reflect
				if(overDisplay == 0) {
					int queueLastType = queueRemoveLast();
				
					if(queueLastType == CalendarNode.TYPE_EVENT)
						overEventDisplay++;
					else if(queueLastType == CalendarNode.TYPE_TASK)
						overTaskDisplay++;

					overDisplay++;
				}
				
				// Now process one we're adding
				if(queueCurType == CalendarNode.TYPE_EVENT)
					overEventDisplay++;
				else if(queueCurType == CalendarNode.TYPE_TASK)
					overTaskDisplay++;
				
				overDisplay++;
			} else {
        		curDisplay++;
        		add(node);
			}
		}
		
		// Add label if some are truncated
		if(overDisplay > 0)
			addNotShownLabel(overEventDisplay, overTaskDisplay);


		queueNodes.removeAllElements();
	}
	
	private void addNotShownLabel(int eventCount, int taskCount) {
		add(new CalendarNodeItem(eventCount, taskCount));
	}
	
	public void clear() {
		this.removeAll();
	}
	
	private class CalendarNodeItem extends JPanel {
		private GridBagConstraints c;
		private Event _event;
		private Task _task;
		private int _type;
		
		private CalendarNodeItem() {
			this.setLayout(new GridBagLayout());
			this.setOpaque(false);
			this.setBackground(Color.WHITE);
			
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
			
			addMouseListener(new MouseListener() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						if (_event != null)
							new EventInformationDialog(App.getFrame(), "Event Information", _event).setVisible(true);
						else if (_task != null)
							new TaskInformationDialog(App.getFrame(), "Task Information", _task).setVisible(true);
					}
					else if (SwingUtilities.isRightMouseButton(e)) {
						if (_event != null)
							new CalendarItemPopupMenu(_event).show(e.getComponent(), e.getX(), e.getY());
						else if (_task != null)
							new CalendarItemPopupMenu(_task).show(e.getComponent(), e.getX(), e.getY());
					}
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) { }
				@Override
				public void mouseEntered(MouseEvent arg0) { }
				@Override
				public void mouseExited(MouseEvent arg0) { }
				@Override
				public void mouseReleased(MouseEvent arg0) { } 
			});
		}
		
		public Event getEvent() {
			return _event;
		}
		
		public Task getTask() {
			return _task;
		}
		
		public int getType() {
			if(_type == CalendarNode.TYPE_EVENT)
				return _type;
			else if(_type == CalendarNode.TYPE_TASK)
				return _type;
			else if(_type == CalendarNode.TYPE_LABEL)
				return _type;
			else
				return CalendarNode.TYPE_INVALID;
		}
		
		private CalendarNodeItem(int eventCount, int taskCount) {
			this();
			_type = CalendarNode.TYPE_LABEL;

			c.gridx = 0;
			c.gridy = 0;
			
			String text = "";
			
			if(eventCount > 0 && taskCount <= 0)
				text = eventCount + " " + (eventCount == 1 ? "event" : "events") + " not shown";
			
			else if(eventCount <= 0 && taskCount > 0)
				text = taskCount + " " + (taskCount == 1 ? "task" : "tasks") + " not shown";
			
			else if(eventCount > 0 & taskCount > 0)
				text = eventCount + " " + (eventCount == 1 ? "event" : "events") + " and " + taskCount + " " + (taskCount == 1 ? "task" : "tasks") + " not shown";
			
			JLabel trimmedText = new JLabel(text);
			trimmedText.setFont(new Font(trimmedText.getFont().getName(), Font.ITALIC, 14));
			add(trimmedText, c);
		}
		
		private CalendarNodeItem(Task task) {
			this();
			this._task = task;

			_type = CalendarNode.TYPE_TASK;

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
			this._event = event;
			
			_type = CalendarNode.TYPE_EVENT;

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
