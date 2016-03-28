package net.sf.memoranda.ui;


import javax.swing.JToolBar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.ColorScheme;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.swing.border.*;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.GridLayout;


public class PSPTaskbarPanel extends JPanel {

		PSPPanel parentPanel = null;

		private final JLabel pspLabel = new JLabel("Personal Software Process");
		private final JPanel viewbtnPanel = new JPanel();

		/**
		 * Create the application.
		 */
		 public PSPTaskbarPanel(PSPPanel _parentPanel) {

			 super();
			 setVisible(true);
			 super.setBorder(new EmptyBorder(new Insets(10,10,10,2)));
			 setAlignmentX(Component.LEFT_ALIGNMENT);

		        try {
		            parentPanel = _parentPanel;

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

			
			this.setBackground(ColorScheme.getColor("frame_secondary"));
			this.setBorder(null);
			setLayout(null);
		    viewbtnPanel.setBounds(439, 81, 464, 42);
		    viewbtnPanel.setBackground(ColorScheme.getColor("frame_secondary"));
																		
			add(viewbtnPanel);
																		
		    JButton btnSummary = new JButton("Summary");
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
			changeToDefectView_actionPerformed(arg0);
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
				changeToTimeView_actionPerformed(arg0);
			}
			});
			
		    btnDefect.setPreferredSize(new Dimension(72,32));
			btnDefect.setBackground(ColorScheme.getColor("button_primary"));
			btnDefect.setBorder(null);
			btnDefect.setMargin(new Insets(5, 14, 5, 14));
			pspLabel.setBounds(489, 13, 373, 38);
			add(pspLabel);
			pspLabel.setFont(new Font("Dialog", Font.BOLD, 29));		   
																					
		}
		
		@Override
		public Dimension getMinimumSize()
		{
			return new Dimension(300, 300);
		}
	
		// Change views
		void changeToSummaryView_actionPerformed(ActionEvent e) {
			//currentView = CalendarPanelView.VIEW_MONTH;
			//parentPanel.changeCalendarPanelView(currentView);
		}

		void changeToTimeView_actionPerformed(ActionEvent e) {
			//currentView = CalendarPanelView.VIEW_WEEK;
			//parentPanel.changeCalendarPanelView(currentView);
		}

		void changeToDefectView_actionPerformed(ActionEvent e) {
			//currentView = CalendarPanelView.VIEW_DAY;
			//parentPanel.changeCalendarPanelView(currentView);
		}

	

		

		
	}
