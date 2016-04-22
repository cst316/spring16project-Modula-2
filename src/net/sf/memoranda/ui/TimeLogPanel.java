package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.ColorScheme;
import net.sf.memoranda.util.Local;

public class TimeLogPanel extends JPanel {

	PSPPanel parentPanel = null;
	
	BorderLayout borderLayout;
	JToolBar toolBar;
	JButton newEntry;
    TimeLogTable table;
    JButton removeEntry;
    JButton editEntry;
    JScrollPane scrollPane;
    
    public TimeLogPanel(PSPPanel parent) {
    	parentPanel = parent;
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    
    void jbInit() {
    	
    	borderLayout = new BorderLayout();
    	toolBar = new JToolBar();
    	newEntry = new JButton();
    	table = new TimeLogTable(this);
    	removeEntry = new JButton();
    	editEntry = new JButton();
    	scrollPane = new JScrollPane();
    	
    	this.setLayout(borderLayout);
    	
    	newEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
    	newEntry.setEnabled(true);
        newEntry.setMaximumSize(new Dimension(24, 24));
        newEntry.setMinimumSize(new Dimension(24, 24));
        newEntry.setToolTipText(Local.getString("New time entry"));
        newEntry.setRequestFocusEnabled(false);
        newEntry.setPreferredSize(new Dimension(24, 24));
        newEntry.setFocusable(false);
        newEntry.setBorderPainted(false);
        newEntry.addActionListener(e -> {
        	newEntry_actionPerformed(e);
        });
        newEntry.setBackground(ColorScheme.getColor("taskbar_primary"));
        
        removeEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
        removeEntry.setFocusable(false);
        removeEntry.setPreferredSize(new Dimension(24, 24));
        removeEntry.setRequestFocusEnabled(false);
        removeEntry.setToolTipText(Local.getString("Remove time entry"));
        removeEntry.setMinimumSize(new Dimension(24, 24));
        removeEntry.setMaximumSize(new Dimension(24, 24));
        removeEntry.setBorderPainted(false);
        removeEntry.setEnabled(false);
        removeEntry.addActionListener(e -> {
        	removeEntry_actionPerformed(e);
        });
        removeEntry.setBackground(ColorScheme.getColor("taskbar_primary"));
        
    	editEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_edit.png")));
    	editEntry.setEnabled(true);
    	editEntry.setMaximumSize(new Dimension(24, 24));
        editEntry.setMinimumSize(new Dimension(24, 24));
        editEntry.setToolTipText(Local.getString("Edit time entry"));
        editEntry.setRequestFocusEnabled(false);
        editEntry.setPreferredSize(new Dimension(24, 24));
        editEntry.setFocusable(false);
        editEntry.setBorderPainted(false);
        editEntry.setEnabled(false);
        editEntry.addActionListener(e -> {
        	editEntry_actionPerformed(e);
        });
        editEntry.setBackground(ColorScheme.getColor("taskbar_primary"));
        
        toolBar.setFloatable(false);
        toolBar.add(newEntry, null);
        toolBar.add(removeEntry, null);
        toolBar.add(editEntry, null);
        toolBar.addSeparator();
        toolBar.setBackground(ColorScheme.getColor("taskbar_primary"));
        toolBar.setBorder(null);
        
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getViewport().add(table, null);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    private void newEntry_actionPerformed(ActionEvent e) {
    	TimeRecordLogDialog dlg = new TimeRecordLogDialog(App.getFrame(), "New Timelog Entry");
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
    	dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
    }
    
    private void removeEntry_actionPerformed(ActionEvent e) {
    	List<TimeEntry> log = CurrentProject.getTimeLog().getLog();
    	if(log.size() <= 0) return;
    	
    	TimeEntry entry = log.get(table.getSelectedRow());
    	if(entry == null) return;
    	
		CurrentProject.getTimeLog().removeTimeEntry(entry);
    }
    
    private void editEntry_actionPerformed(ActionEvent e) {
    	List<TimeEntry> log = CurrentProject.getTimeLog().getLog();
    	if(log.size() <= 0) return;
    	
    	TimeEntry entry = log.get(table.getSelectedRow());
    	if(entry == null) return;
    	
    	TimeRecordLogDialog dlg = new TimeRecordLogDialog(App.getFrame(), Local.getString("New task"));

        int entryInterruptTime = entry.getInterruptionTime();
        int hour = (int) Math.floor(entryInterruptTime / 60.0);
        int minute = entryInterruptTime%60;
        
        String dateString = Integer.toString(hour) + ":" + Integer.toString(minute) + ":0";
        SimpleDateFormat dateZeroFormat = new SimpleDateFormat("H:m:s");
		try {
			Date interruptDate = dateZeroFormat.parse(dateString + " UTC");
			
			dlg.header.setText("Edit Timelog Entry");
	    	dlg.startDate.getModel().setValue(entry.getCalendarDate().getDate());
	    	dlg.logStartTime.getModel().setValue(entry.getStartTime().getTime());
	    	dlg.logInterruptTime.getModel().setValue(interruptDate);
	    	dlg.logEndTime.getModel().setValue(entry.getEndTime().getTime());
	    	dlg.dpPhaseSelector.getModel().setSelectedItem(entry.getPhase());
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
    	
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        
        // Remove the (before edited) entry
        if(!dlg.CANCELLED)
        	CurrentProject.getTimeLog().removeTimeEntry(entry);
    }
}
