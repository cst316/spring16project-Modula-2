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
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PSPPanel extends JPanel {
	
	WorkPanel parentPanel = null;
	
	public PSPPanel(WorkPanel _parentPanel) {
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
        //defectdialog.startDate.getModel().setValue(CurrentDate.get().getDate());
        defectdialog.setLocationRelativeTo(this);
        defectdialog.pack();
        defectdialog.setVisible(true);
	}

	protected void opendefectsB_actionPerformed(ActionEvent arg0) {
		//OPEN DEFECT PANEL HERE
		
	}
}