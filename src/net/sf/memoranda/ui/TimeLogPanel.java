package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

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
    	scrollPane = new JScrollPane();
    	
    	this.setLayout(borderLayout);
    	
    	newEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
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
        
        removeEntry.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removeresource.png")));
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
        
        toolBar.setFloatable(false);
        toolBar.add(newEntry, null);
        toolBar.add(removeEntry, null);
        toolBar.addSeparator();
        toolBar.setBackground(ColorScheme.getColor("taskbar_primary"));
        toolBar.setBorder(null);
        
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getViewport().add(table, null);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    private void newEntry_actionPerformed(ActionEvent e) {
    	TimeRecordLogDialog dlg = new TimeRecordLogDialog(App.getFrame(), Local.getString("New task"));
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
    	dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
    }
    
    private void removeEntry_actionPerformed(ActionEvent e) {
    	TimeEntry entry = CurrentProject.getTimeLog().getLog().get(table.getSelectedRow());
		CurrentProject.getTimeLog().removeTimeEntry(entry);
    }
}
