package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectListListener;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TaskListListener;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PSPDefectPanel extends JPanel {
	
	WorkPanel parentPanel = null;
	
	private static Vector<DefectListListener> defectListListeners = new Vector<DefectListListener>();
	
	public PSPDefectPanel(WorkPanel _parentPanel) {
		setLayout(null);
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
    	JButton btnOpenDefectPanel = new JButton("Open Defect Panel");
    	btnOpenDefectPanel.setBounds(380, 225, 123, 23);
    	btnOpenDefectPanel.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			opendefectsB_actionPerformed(arg0);
    		}
    	});
		add(btnOpenDefectPanel);
		
		JButton btnNewButton = new JButton("Record New Defect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recorddefectB_actionPerformed(arg0);
			}
		});
		btnNewButton.setBounds(373, 265, 140, 23);
		add(btnNewButton);
		
		
    }

	protected void recorddefectB_actionPerformed(ActionEvent arg0) {
		DefectDialog defectdialog = new DefectDialog(App.getFrame(), Local.getString("New Defect"));
        defectdialog.spnDateFound.getModel().setValue(CurrentDate.get().getDate());
        defectdialog.setLocationRelativeTo(this);
        defectdialog.pack();
        defectdialog.setVisible(true);
        
        if (defectdialog.CANCELLED)
            return;
        
        CalendarDate sd = new CalendarDate((Date) defectdialog.spnDateFound.getModel().getValue());
        CalendarDate ed;
 		
        if(defectdialog.chkDateFixed.isSelected())
 			ed = new CalendarDate((Date) defectdialog.spnDateFixed.getModel().getValue());
 		else
 			ed = null;
        
        long esttime = Util.getMillisFromMinutes(defectdialog.txtEstFixTime.getText());
        
        long acttime;
        boolean iscompleted;
        if(defectdialog.chkDateFixed.isSelected()) {
        	acttime = Util.getMillisFromMinutes(defectdialog.txtActFixTime.getText());
        	iscompleted = true;
        }
        else {
        	acttime = 0;
        	iscompleted = false;
        }
        
        String fixref;
        if(defectdialog.chkFixReference.isSelected())
        	fixref = defectdialog.txtFixReference.getText();
        else
        	fixref = null;
        	
		//SO FAR SO GOOD
        Defect newDefect = CurrentProject.getDefectList().createDefect(sd, Integer.parseInt(defectdialog.tfNumber.getText()), 
        		defectdialog.cmbType.getSelectedItem().toString(), defectdialog.txtInjection.getText(), 
        		esttime, acttime, ed, defectdialog.txtRemove.getText(), fixref, defectdialog.txtaDescription.getText(), iscompleted);
        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        //defectTable.tableChanged();
       
        notifyDefectListListeners();
	}

	protected void opendefectsB_actionPerformed(ActionEvent arg0) {
		//OPEN DEFECT PANEL HERE
		
	}
	
	public static void addDefectListListener(DefectListListener listener) {
		  defectListListeners.add(listener);
	}
	
	static void notifyDefectListListeners() {
		  for (DefectListListener listener : defectListListeners) {
			  listener.defectListModified();
		  }
	}
	
	/* FUTURE ALARM FEATURE ON THE PANEL FOR NOTIFICATIONS ON THE DEFECT FIX
	public void updateIndicators(CalendarDate date, TaskList tl) {
        indicatorsPanel.removeAll();
        if (date.equals(CalendarDate.today())) {
            if (tl.getActiveSubTasks(null,date).size() > 0)
                indicatorsPanel.add(taskB, null);
            if (EventsScheduler.isEventScheduled()) {
                net.sf.memoranda.Event ev = EventsScheduler.getFirstScheduledEvent();
                alarmB.setToolTipText(ev.getTimeString() + " - " + ev.getText());
                indicatorsPanel.add(alarmB, null);
            }
        }
        indicatorsPanel.updateUI();
    }
    */
}