package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.memoranda.Task;

public class TaskInformationDialog extends JDialog {

public TaskInformationDialog(Frame owner, String title, Task task) {
		
		super(owner, title, true);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel gridPanel = new JPanel();
		
		gridPanel.add(new JLabel("Task Name: "));
		gridPanel.add(new JLabel(task.getText()));

		if (task.getStartDate() != null) {
			gridPanel.add(new JLabel("Start Date: "));
			gridPanel.add(new JLabel(task.getStartDate().getShortDateString()));
		}
		
		if (task.getEndDate() != null) {
			gridPanel.add(new JLabel("End Date: "));
			gridPanel.add(new JLabel(task.getEndDate().getShortDateString()));
		}
		
		gridPanel.add(new JLabel("Progress: "));
		gridPanel.add(new JLabel(task.getProgress() + "%"));
		
		gridPanel.add(new JLabel("Priority: "));
		gridPanel.add(new JLabel(Integer.toString(task.getPriority())));
		
		gridPanel.add(new JLabel("Description: "));
		JTextArea description = new JTextArea(task.getDescription());
		description.setLineWrap(true);
		description.setEditable(false);
		
		gridPanel.setLayout(new GridLayout((gridPanel.getComponentCount() + 1) / 2, 2, 10, 10));
		
		panel.add(gridPanel);
		panel.add(description);
		add(panel);
		pack();
		
		setMinimumSize(new Dimension(400, 300));
		setLocationRelativeTo(null);
	}
}
