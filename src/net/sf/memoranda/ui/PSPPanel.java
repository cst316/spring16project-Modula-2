package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class PSPPanel extends JPanel {

	public PSPPanel(WorkPanel workPanel) {
		JPanel panel = new JPanel();
		add(panel);
		
		JButton bdeffectlog = new JButton("Deffect Log");
		bdeffectlog.setMargin(new Insets(2, 14, 2, 30));
		bdeffectlog.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		bdeffectlog.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		bdeffectlog.setBackground(SystemColor.activeCaption);
		
		JButton btimelog = new JButton("Time Recording Log");
		btimelog.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btimelog.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		btimelog.setBackground(SystemColor.activeCaption);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(bdeffectlog, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(btimelog, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(28)
					.addComponent(bdeffectlog)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btimelog)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JButton bnewdeffect = new JButton("New Deffect Entry");
		bnewdeffect.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bnewdeffect.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		bnewdeffect.setBackground(SystemColor.controlHighlight);
		
		JButton bnewtimerecord = new JButton("New Time Entry");
		bnewtimerecord.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		bnewtimerecord.setBackground(SystemColor.controlHighlight);
		
		JLabel lblNewLabel = new JLabel("PSP Taskbar");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setMaximumSize(new Dimension(200, 200));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(215)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bnewtimerecord, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bnewdeffect, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addGap(38))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(bnewdeffect)
							.addComponent(bnewtimerecord))
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);

	}
	}
	
 
 
   
