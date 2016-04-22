package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TaskPlanningEntry;
import net.sf.memoranda.util.ColorScheme;
import net.sf.memoranda.util.Local;

public class TaskPlanningLogPanel extends JPanel {
	
	PSPPanel parentPanel = null;
	
	BorderLayout borderLayout;
	JToolBar toolBar;
	JButton newEntry;
    TaskPlanningLogTable table;
    JButton removeEntry;
    JButton editEntry;
    JScrollPane scrollPane;
    
    public TaskPlanningLogPanel(PSPPanel parent) {
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
    	table = new TaskPlanningLogTable(this);
    	removeEntry = new JButton();
    	editEntry = new JButton();
    	scrollPane = new JScrollPane();
    	
    	this.setLayout(borderLayout);
    	
    	newEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
    	newEntry.setEnabled(true);
        newEntry.setMaximumSize(new Dimension(24, 24));
        newEntry.setMinimumSize(new Dimension(24, 24));
        newEntry.setToolTipText(Local.getString("New task planning entry"));
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
        removeEntry.setToolTipText(Local.getString("Remove task planning entry"));
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
        toolBar.add(editEntry,null);
        toolBar.addSeparator();
        toolBar.setBackground(ColorScheme.getColor("taskbar_primary"));
        toolBar.setBorder(null);
        
        table.getTableHeader().setBackground(ColorScheme.getColor("frame_secondary"));
        
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getViewport().add(table, null);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.NORTH);
        this.setBorder(null);
    }
    
    private void newEntry_actionPerformed(ActionEvent e) {
    	TaskPlanningRecordLogDialog dlg = new TaskPlanningRecordLogDialog(App.getFrame(), Local.getString("New task planning entry"));
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
    }
    
    private void removeEntry_actionPerformed(ActionEvent e) {
    	List<TaskPlanningEntry> log = CurrentProject.getTaskPlanningLog().getLog();
    	if(log.size() <= 0) return;
    	
    	TaskPlanningEntry entry = log.get(table.getSelectedRow());
    	if(entry == null) return;

		CurrentProject.getTaskPlanningLog().removeTaskPlanningEntry(entry);
    }
	
    private void editEntry_actionPerformed(ActionEvent e) {
    	List<TaskPlanningEntry> log = CurrentProject.getTaskPlanningLog().getLog();
    	if(log.size() <= 0) return;
    	
    	TaskPlanningEntry entry = log.get(table.getSelectedRow());
    	if(entry == null) return;

    	TaskPlanningRecordLogDialog dlg = new TaskPlanningRecordLogDialog(App.getFrame(), Local.getString("Edit task planning entry"));
    	
    	// Fill in data
    	dlg.taskNum.setValue(entry.getTaskNumber());
    	dlg.taskName.setText(entry.getTaskName());
    	dlg.plannedHour.setValue(entry.getPlannedHours());
    	dlg.plannedValue.setValue(entry.getPV());
    	dlg.plannedDate.setValue(entry.getPlannedDate().getDate());
    	dlg.earnedValue.setValue(entry.getEV());
    	dlg.actualDate.setValue(entry.getActualDate().getDate());
    	dlg.completedCheckbox.setSelected(entry.isComplete());
    	
    	if(entry.isComplete()) {
    		dlg.earnedValue.setEnabled(true);
    		dlg.actualDate.setEnabled(true);
    	}
    	
    	// Finish frame
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
    	
        // Remove the (before edited) entry
        if(!dlg.CANCELLED)
        	CurrentProject.getTaskPlanningLog().removeTaskPlanningEntry(entry);
    }
}
