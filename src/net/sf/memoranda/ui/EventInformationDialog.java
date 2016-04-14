package net.sf.memoranda.ui;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.memoranda.Event;

public class EventInformationDialog extends JDialog {

	public EventInformationDialog(Frame owner, String title, Event event) {
		
		super(owner, title, true);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panel.add(new JLabel("Event Name: "));
		panel.add(new JLabel(event.getText()));

		if (event.getStartDate() != null) {
			panel.add(new JLabel("Start Date: "));
			panel.add(new JLabel(event.getStartDate().getShortDateString() + " @ " + event.getTimeString()));
		}
		
		if (event.getEndDate() != null) {
			panel.add(new JLabel("End Date: "));
			panel.add(new JLabel(event.getEndDate().getShortDateString() + " @ " + event.getTimeString()));
		}
		
		panel.add(new JLabel("Repeatable Event: "));
		panel.add(new JLabel(Boolean.toString(event.isRepeatable())));
		
		panel.setLayout(new GridLayout(panel.getComponentCount() / 2, 2, 10, 10));
		add(panel);
		
		setLocationRelativeTo(null);
		pack();
	}
}
