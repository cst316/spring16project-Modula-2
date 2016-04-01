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


public class CalendarTaskbarPanel extends JToolBar {

		JLabel lblDay = new JLabel("New label");
		JLabel lblMonth = new JLabel("New label");
		JLabel lblYear = new JLabel("New label");

		JCheckBox chckbxTasks = new JCheckBox("Show Tasks");
		JCheckBox chckbxEvents = new JCheckBox("Show Events");

		CalendarPanel parentPanel = null;
		CalendarDate date;

		private int currentView = CalendarPanelView.VIEW_INVALID;

		public int getCurrentView() {
			return currentView;
		}

		private final JPanel panel = new JPanel();

		private final JPanel panel_1 = new JPanel();
		private final JButton btnLittleStepBack = new JButton();
		private final JButton btnBigStepBack = new JButton();

		private final JPanel panel_2 = new JPanel();
		private final JButton btnLittleStepForward = new JButton();
		private final JButton btnBigStepForward = new JButton();

		/**
		 * Create the application.
		 */
		 public CalendarTaskbarPanel(CalendarPanel _parentPanel) {

			 super();
			 setFloatable(false);
			 super.setBorder(new EmptyBorder(new Insets(10,10,10,2)));
			 setAlignmentX(Component.LEFT_ALIGNMENT);
			 setLayout(new FlowLayout(FlowLayout.CENTER));

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

			JButton btnMonthly = new JButton("Month");
			btnMonthly.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeToMonthlyView_actionPerformed(arg0);
				}
			});
			btnMonthly.setPreferredSize(new Dimension(72,32));
			btnMonthly.setBackground(ColorScheme.getColor("button_primary"));
			btnMonthly.setBorder(null);
			btnMonthly.setMargin(new Insets(5, 14, 5, 14));
			btnMonthly.setSize(new Dimension(5, 5));
			add(btnMonthly);

			JButton btnWeekly = new JButton("Week");
			btnWeekly.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeToWeeklyView_actionPerformed(arg0);
				}
			});
			btnWeekly.setPreferredSize(new Dimension(72,32));
			btnWeekly.setBackground(ColorScheme.getColor("button_primary"));
			btnWeekly.setBorder(null);
			btnWeekly.setMargin(new Insets(5, 14, 5, 14));
			add(btnWeekly);

			JButton btnDaily = new JButton("Day");
			btnDaily.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeToDailyView_actionPerformed(arg0);
				}
			});
			btnDaily.setPreferredSize(new Dimension(72,32));
			btnDaily.setBackground(ColorScheme.getColor("button_primary"));
			btnDaily.setBorder(null);
			btnDaily.setMargin(new Insets(5, 14, 5, 14));
			add(btnDaily);

			Image leftArrowSmall = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/left_arrow_small.gif"));
			Image resizedLeftArrowSmall = leftArrowSmall.getScaledInstance(20, 20, 0);

			Image leftArrowBig = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/left_arrow.gif"));
			Image resizedLeftArrowBig = leftArrowBig.getScaledInstance(20, 20, 0);
			btnLittleStepBack.setIcon(new ImageIcon(resizedLeftArrowSmall));
			btnLittleStepBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeSmallStepBackward_actionPerformed(arg0);
				}
			});
			btnLittleStepBack.setPreferredSize(new Dimension(32,32));
			btnLittleStepBack.setBackground(ColorScheme.getColor("button_primary"));
			btnLittleStepBack.setBorder(null);
			panel_1.add(btnLittleStepBack);
			btnBigStepBack.setIcon(new ImageIcon(resizedLeftArrowBig));
			btnBigStepBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeLargeStepBackward_actionPerformed(arg0);
				}
			});
			btnBigStepBack.setPreferredSize(new Dimension(32,32));
			btnBigStepBack.setBackground(ColorScheme.getColor("button_primary"));
			btnBigStepBack.setBorder(null);
			panel_1.add(btnBigStepBack);
			panel_1.setBackground(ColorScheme.getColor("taskbar_primary"));
			add(panel_1);

			panel.setPreferredSize(new Dimension(500, 40));
			panel.setBackground(ColorScheme.getColor("taskbar_primary"));
			panel.setBorder(null);
			add(panel);
			panel.add(lblDay);

			lblDay.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblDay.setText(this.getDay());
			panel.add(lblMonth);

			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblMonth.setText(this.getMonth());
			panel.add(lblYear);

			lblYear.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblYear.setText(this.getYear());

			panel_2.setBackground(ColorScheme.getColor("taskbar_primary"));
			add(panel_2);

			Image rightArrowSmall = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/right_arrow_small.gif"));
			Image resizedRightArrowSmall = rightArrowSmall.getScaledInstance(20, 20, 0);
			btnLittleStepForward.setIcon(new ImageIcon(resizedRightArrowSmall));
			btnLittleStepForward.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeSmallStepForward_actionPerformed(arg0);
				}
			});
			btnLittleStepForward.setPreferredSize(new Dimension(32,32));
			btnLittleStepForward.setBackground(ColorScheme.getColor("button_primary"));
			btnLittleStepForward.setBorder(null);
			panel_2.add(btnBigStepForward);

			Image rightArrowBig = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/right_arrow.gif"));
			Image resizedRightArrowBig = rightArrowBig.getScaledInstance(20, 20, 0);
			btnBigStepForward.setIcon(new ImageIcon(resizedRightArrowBig));
			btnBigStepForward.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeLargeStepForward_actionPerformed(arg1);
				}
			});
			btnBigStepForward.setPreferredSize(new Dimension(32,32));
			btnBigStepForward.setBackground(ColorScheme.getColor("button_primary"));
			btnBigStepForward.setBorder(null);
			panel_2.add(btnLittleStepForward);

			chckbxEvents.setSelected(true);
			chckbxEvents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeShowEvents_actionPerformed(arg1);
				}
			});
			
			chckbxEvents.setForeground(ColorScheme.getColor("taskbar_text"));
			chckbxEvents.setBackground(ColorScheme.getColor("taskbar_primary"));
			add(chckbxEvents);

			chckbxTasks.setSelected(true);
			chckbxTasks.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeShowTasks_actionPerformed(arg1);
				}
			});
			chckbxTasks.setBackground(ColorScheme.getColor("taskbar_primary"));
			chckbxTasks.setForeground(ColorScheme.getColor("taskbar_text"));
			add(chckbxTasks);
			
			lblDay.setForeground(ColorScheme.getColor("taskbar_text"));
			lblMonth.setForeground(ColorScheme.getColor("taskbar_text"));
			lblYear.setForeground(ColorScheme.getColor("taskbar_text"));
			
			this.setBackground(ColorScheme.getColor("taskbar_primary"));
			this.setBorder(null);
		}
		public String getDay(){
			date = CurrentDate.get();
			return Integer.toString(date.getDay());
		}

		public String getMonth(){
			date = CurrentDate.get();
			return new DateFormatSymbols().getMonths()[date.getMonth()];
		}

		protected String getYear(){
			date = CurrentDate.get();
			return Integer.toString(date.getYear());
		}

		protected void updateCalendar() {
			lblDay.setText(this.getDay());
			lblYear.setText(this.getYear());
			lblMonth.setText(this.getMonth());

			parentPanel.updateCalendarPanelView();
		}

		// Change views
		void changeToMonthlyView_actionPerformed(ActionEvent e) {
			currentView = CalendarPanelView.VIEW_MONTH;
			parentPanel.changeCalendarPanelView(currentView);
		}

		void changeToWeeklyView_actionPerformed(ActionEvent e) {
			currentView = CalendarPanelView.VIEW_WEEK;
			parentPanel.changeCalendarPanelView(currentView);
		}

		void changeToDailyView_actionPerformed(ActionEvent e) {
			currentView = CalendarPanelView.VIEW_DAY;
			parentPanel.changeCalendarPanelView(currentView);
		}

		// Date stepping
		void changeLargeStepBackward_actionPerformed(ActionEvent e){
			parentPanel.viewPanel.stepLargeBackward();
			date = CurrentDate.get();
			updateCalendar();
		}

		void changeLargeStepForward_actionPerformed(ActionEvent e){
			parentPanel.viewPanel.stepLargeForward();
			date = CurrentDate.get();
			updateCalendar();
		}

		void changeSmallStepBackward_actionPerformed(ActionEvent e){
			parentPanel.viewPanel.stepSmallBackward();
			date = CurrentDate.get();
			updateCalendar();
		}

		void changeSmallStepForward_actionPerformed(ActionEvent e){
			parentPanel.viewPanel.stepSmallForward();
			date = CurrentDate.get();
			updateCalendar();
		}

		// Show/hide tasks
		void changeShowTasks_actionPerformed(ActionEvent e){
			parentPanel.updateCalendarPanelView();
		}

		void changeShowEvents_actionPerformed(ActionEvent e){
			parentPanel.updateCalendarPanelView();
		}

		protected CalendarDate getCurrentYesterday() {
	        Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
	        cal.add(Calendar.DATE, -1);
	        return new CalendarDate(cal);
	    }

		protected CalendarDate getCurrentTomorrow() {
	        Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
	        cal.add(Calendar.DATE, 1);
	        return new CalendarDate(cal);
	    }

		public boolean isShowEvents() {
			return chckbxEvents.isSelected();
		}

		public boolean isShowTasks() {
			return chckbxTasks.isSelected();
		}
	}
