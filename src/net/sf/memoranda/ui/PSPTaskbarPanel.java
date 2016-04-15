package net.sf.memoranda.ui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import net.sf.memoranda.util.ColorScheme;


public class PSPTaskbarPanel extends JToolBar {

		PSPPanel parentPanel = null;

		private final JLabel pspLabel = new JLabel("Personal Software Process");
		private final JPanel viewbtnPanel = new JPanel();
		
		public JButton btnSummary = new JButton();
		public JButton btnTaskPlanning = new JButton();
		public JButton btnTimeRecording = new JButton();
		public JButton btnDefect = new JButton();

		/**
		 * Create the application.
		 */
		 public PSPTaskbarPanel(PSPPanel _parentPanel) {

			 super();
			 
			 parentPanel = _parentPanel;
			 setFloatable(false);
			 setVisible(true);
			 super.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
			 setAlignmentX(Component.LEFT_ALIGNMENT);
			 setLayout(new FlowLayout(FlowLayout.CENTER));

		        try {
		            jbInit();
		        }
		        catch (Exception ex) {
		            new ExceptionDialog(ex);
		        }
		        
		    }


		/**
		 * Initialize the contents of the frame.
		 */
		private void jbInit () throws Exception {
			this.setBackground(ColorScheme.getColor("taskbar_primary"));
			this.setBorder(null);
			setLayout(null);

		    viewbtnPanel.setBounds(-32, 0, 464, 42);
		    viewbtnPanel.setBackground(ColorScheme.getColor("taskbar_primary"));
																		
			add(viewbtnPanel);
																		
		    btnSummary.setText("Summary");
		    btnSummary.setEnabled(false);
		    viewbtnPanel.add(btnSummary);
		    btnSummary.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    changeToSummaryView_actionPerformed(arg0);
		    }
		    });			
		    btnSummary.setPreferredSize(new Dimension(96,32));
		    btnSummary.setBackground(ColorScheme.getColor("button_primary"));
		    btnSummary.setBorder(null);
			btnSummary.setMargin(new Insets(5, 14, 5, 14));
		    btnSummary.setSize(new Dimension(5, 5));	
		    btnSummary.setFocusPainted(false);
			
		    
			viewbtnPanel.add(btnTaskPlanning);
			btnTaskPlanning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeToTaskPlanningView_actionPerformed(arg0);
			}
			});
			
			btnTaskPlanning.setText("Planning Log");
			btnTaskPlanning.setPreferredSize(new Dimension(96,32));
			btnTaskPlanning.setBackground(ColorScheme.getColor("button_primary"));
			btnTaskPlanning.setBorder(null);
			btnTaskPlanning.setMargin(new Insets(5, 14, 5, 14));
			btnTaskPlanning.setBounds(489, 13, 373, 38);
			btnTaskPlanning.setFocusPainted(false);
			//add(pspLabel);
			pspLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		    
			btnTimeRecording.setText("Time Log");
			viewbtnPanel.add(btnTimeRecording);
			btnTimeRecording.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			changeToTimeView_actionPerformed(arg0);
			}			
			});
			
			btnTimeRecording.setPreferredSize(new Dimension(96,32));
			btnTimeRecording.setBackground(ColorScheme.getColor("button_primary"));
			btnTimeRecording.setBorder(null);
			btnTimeRecording.setMargin(new Insets(5, 14, 5, 14));
			btnTimeRecording.setFocusPainted(false);
						
			viewbtnPanel.add(btnDefect);
			
			btnDefect.setText("Defect Log");
			btnDefect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeToDefectView_actionPerformed(arg0);
			}
			});
		    btnDefect.setPreferredSize(new Dimension(96,32));
			btnDefect.setBackground(ColorScheme.getColor("button_primary"));
			btnDefect.setBorder(null);
			btnDefect.setMargin(new Insets(5, 14, 5, 14));
			btnDefect.setFocusPainted(false);
			
			pspLabel.setBounds(489, 13, 373, 38);
			//add(pspLabel);
			pspLabel.setFont(new Font("Dialog", Font.BOLD, 20));		   	
		}
	
		// Change views
		void changeToSummaryView_actionPerformed(ActionEvent e) {
			parentPanel.updateView(PSPPanel.VIEW_SUMMARY);
		}

		void changeToTimeView_actionPerformed(ActionEvent e) {
			parentPanel.updateView(PSPPanel.VIEW_TIMELOG);
		}

		void changeToDefectView_actionPerformed(ActionEvent e) {
			parentPanel.updateView(PSPPanel.VIEW_DEFECTLOG);
		}
		
		void changeToTaskPlanningView_actionPerformed(ActionEvent e) {
			parentPanel.updateView(PSPPanel.VIEW_TASKPLANNINGLOG);
		}
	}
