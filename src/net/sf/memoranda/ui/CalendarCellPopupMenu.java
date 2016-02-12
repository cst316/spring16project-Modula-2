package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sf.memoranda.util.Local;

public class CalendarCellPopupMenu extends JPopupMenu {

	public CalendarCellPopupMenu() {
		
		super();
		
		JMenuItem addEvent = new JMenuItem("Add Event");
		JMenuItem addTask  = new JMenuItem("Add Task");
		
		add(addEvent);
		add(addTask);
		
		addEvent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEventB_actionPerformed(e);
			}
		});
		
		addTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTaskB_actionPerformed(e);
			}
		});
	}
	
	public void addEventB_actionPerformed(ActionEvent e) {
		//EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Event"));
	}
	
	public void addTaskB_actionPerformed(ActionEvent e) {
		//TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("Task"));
	}
}
