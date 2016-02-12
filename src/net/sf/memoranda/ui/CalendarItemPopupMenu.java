package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class CalendarItemPopupMenu extends JPopupMenu {

	public CalendarItemPopupMenu() {
		
		super();
		
		JMenuItem edit = new JMenuItem("Edit");
		JMenuItem delete  = new JMenuItem("Delete");
		
		add(edit);
		add(delete);
		
		edit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteB_actionPerformed(e);
			}
		});
		
		delete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editB_actionPerformed(e);
			}
		});
	}
	
	public void deleteB_actionPerformed(ActionEvent e) {
		
	}
	
	public void editB_actionPerformed(ActionEvent e) {
		
	}
}
