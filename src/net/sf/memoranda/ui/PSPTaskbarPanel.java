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

		    viewbtnPanel.setBounds(-50, 0, 464, 42);
		    viewbtnPanel.setBackground(ColorScheme.getColor("taskbar_primary"));
																		
			add(viewbtnPanel);
																		
		    JButton btnSummary = new JButton("Summary");
		    btnSummary.setEnabled(false);
		    viewbtnPanel.add(btnSummary);
		    btnSummary.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    changeToSummaryView_actionPerformed(arg0);
		    }
		    });			
		    
		    
		    btnSummary.setPreferredSize(new Dimension(72,32));
		    btnSummary.setBackground(ColorScheme.getColor("button_primary"));
		    btnSummary.setBorder(null);
			btnSummary.setMargin(new Insets(5, 14, 5, 14));
		    btnSummary.setSize(new Dimension(5, 5));																			
			
			
			JButton btnTimeRecording = new JButton("Time Log");
			viewbtnPanel.add(btnTimeRecording);
			btnTimeRecording.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			changeToTimeView_actionPerformed(arg0);
			}			
			});
			
			btnTimeRecording.setPreferredSize(new Dimension(72,32));
			btnTimeRecording.setBackground(ColorScheme.getColor("button_primary"));
			btnTimeRecording.setBorder(null);
			btnTimeRecording.setMargin(new Insets(5, 14, 5, 14));
						
						
			JButton btnDefect = new JButton("Defect Log");
			viewbtnPanel.add(btnDefect);
			btnDefect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeToDefectView_actionPerformed(arg0);
			}
			});
			
		    btnDefect.setPreferredSize(new Dimension(72,32));
			btnDefect.setBackground(ColorScheme.getColor("button_primary"));
			btnDefect.setBorder(null);
			btnDefect.setMargin(new Insets(5, 14, 5, 14));
			pspLabel.setBounds(489, 13, 373, 38);
			//add(pspLabel);
			pspLabel.setFont(new Font("Dialog", Font.BOLD, 20));		   
																					
		}
	
		// Change views
		void changeToSummaryView_actionPerformed(ActionEvent e) {
			// TODO: Show summary panel.
		}

		void changeToTimeView_actionPerformed(ActionEvent e) {
			parentPanel.getCardLayout().show(parentPanel.getCardPanel(), "TIMELOG");
		}

		void changeToDefectView_actionPerformed(ActionEvent e) {
			parentPanel.getCardLayout().show(parentPanel.getCardPanel(), "DEFECTLOG");
		}
		
	}
