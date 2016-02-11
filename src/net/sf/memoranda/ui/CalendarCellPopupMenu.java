package net.sf.memoranda.ui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class CalendarCellPopupMenu extends JPopupMenu {

	public CalendarCellPopupMenu() {
		
		super();
		
		JMenuItem addEvent = new JMenuItem("Add Event");
		JMenuItem addTask  = new JMenuItem("Add Task");
		
		add(addEvent);
		add(addTask);
	}
}
