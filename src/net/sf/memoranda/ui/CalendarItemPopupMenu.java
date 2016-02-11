package net.sf.memoranda.ui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class CalendarItemPopupMenu extends JPopupMenu {

	public CalendarItemPopupMenu() {
		
		super();
		
		JMenuItem edit = new JMenuItem("Edit");
		JMenuItem delete  = new JMenuItem("Delete");
		
		add(edit);
		add(delete);
	}
}
