package net.sf.memoranda.ui;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

import net.sf.memoranda.Contact;
import net.sf.memoranda.ContactListImpl;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.util.Local;

public class ContactListTable extends JTable {

	ProjectDialog parent;
	List<Contact> contacts;
	
	public ContactListTable(ProjectDialog parent) {
		super();
		
		this.parent = parent;
		
		initTable();
		setModel(new ContactListTableModel());
		
		ListSelectionModel selectionModel = getSelectionModel();
		selectionModel.addListSelectionListener(e -> {
			listSelection_actionPerformed(e);
		});
		
		ContactListImpl.addContactListener(() -> {
			tableChanged();
		});
	}
	
	public void initTable() {
		contacts = CurrentProject.getContactList().getLog();
	}
	
	public void tableChanged() {
		initTable();
		updateUI();
	}
	
	class ContactListTableModel extends AbstractTableModel {
		
		final String[] columnNames = {
				Local.getString("Name"),
				Local.getString("Email"),
				Local.getString("Phone")
		};
		
		public String getColumnName(int i) {
			return columnNames[i];
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return contacts.size();
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			Contact c = contacts.get(row);
			switch (col) {
				case 0: return c.getName();
				case 1: return c.getEmail();
				case 2: return c.getPhone();
			}
			
			return null;
		}
	}
	
	private void listSelection_actionPerformed(ListSelectionEvent e) {
		parent.buttonRemoveTeam.setEnabled(true);
	}
}
