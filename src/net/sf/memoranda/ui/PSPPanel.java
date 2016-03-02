package net.sf.memoranda.ui;
import net.sf.memoranda.*;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.ui.App;
import net.sf.memoranda.ui.TaskDialog;
import net.sf.memoranda.ui.TimeRecordLogDialog;
import net.sf.memoranda.ui.WorkPanel;
import net.sf.memoranda.util.Local;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Point;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

public class PSPPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public PSPPanel(WorkPanel workPanel) {
		setLayout(null);
		
		JPanel logViewPanel = new JPanel();
		logViewPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		logViewPanel.setBounds(12, 163, 1169, 576);
		add(logViewPanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 36, 1169, 85);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton bNewTimeEntry = new JButton("New Time Entry");
		bNewTimeEntry.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				TimeRecordLogDialog dlg = new TimeRecordLogDialog(App.getFrame(), Local.getString("New task"));
				Dimension frmSize = App.getFrame().getSize();
		        Point loc = App.getFrame().getLocation();
		        dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
		        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		        dlg.setVisible(true);
			}

			
		});
		
		bNewTimeEntry.setBounds(1021, 60, 123, 25);
		panel_1.add(bNewTimeEntry);
		
		JButton bDeleteSelected = new JButton("Delete Selected ");
		bDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		bDeleteSelected.setBounds(1021, 25, 125, 25);
		panel_1.add(bDeleteSelected);
		
		JLabel lblTimeRecordingLog = new JLabel("Time Recording Log");
		lblTimeRecordingLog.setFont(new Font("Dialog", Font.PLAIN, 26));
		lblTimeRecordingLog.setBounds(426, 25, 254, 47);
		panel_1.add(lblTimeRecordingLog);
		
		
		
		
		
		
		
	}
}
