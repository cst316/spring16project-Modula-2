package net.sf.memoranda.ui;

import javax.swing.JToolBar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;

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
		
		private final JPanel panel = new JPanel();

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
			btnMonthly.setBackground(UIManager.getColor("Button.light"));
			btnMonthly.setMargin(new Insets(5, 14, 5, 14));
			btnMonthly.setSize(new Dimension(5, 5));
			add(btnMonthly);
			
			JButton btnWeekly = new JButton("Week");
			btnWeekly.setBackground(UIManager.getColor("Button.light"));
			btnWeekly.setMargin(new Insets(5, 14, 5, 14));
			add(btnWeekly);
			
			JButton btnDaily = new JButton("Day");
			btnDaily.setBackground(UIManager.getColor("Button.light"));
			btnDaily.setMargin(new Insets(5, 14, 5, 14));
			add(btnDaily);
			
			JButton btnPreviousMonth = new JButton();
			Image leftArrow = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/left_arrow.gif"));
			Image resizedLeftArrow = leftArrow.getScaledInstance(20, 20, 0);
			
			btnPreviousMonth.setIcon(new ImageIcon(resizedLeftArrow));
			btnPreviousMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeToPreviousDate_actionPerformed(arg0);
				}
			});
			add(btnPreviousMonth);
			panel.setPreferredSize(new Dimension(500, 40));
			
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
			
			JButton btnNextMonth = new JButton();
			Image rightArrow = ImageIO.read(CalendarTaskbarPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/right_arrow.gif"));
			Image resizedRightArrow = rightArrow.getScaledInstance(20, 20, 0);
			
			btnNextMonth.setIcon(new ImageIcon(resizedRightArrow));
			btnNextMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeToNextDate_actionPerformed(arg1);
				}
			});
			add(btnNextMonth);
			
			chckbxTasks.setSelected(true);
			chckbxTasks.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeShowTasks_actionPerformed(arg1);
				}
			});
			add(chckbxTasks);
			
			chckbxEvents.setSelected(true);
			chckbxEvents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeShowEvents_actionPerformed(arg1);
				}
			});
			add(chckbxEvents);
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
			
			parentPanel.populateDays();
		}
		
		void changeToPreviousDate_actionPerformed(ActionEvent e){
			// Decrement by day
			//CurrentDate.set(getCurrentYesterday());
			//date = CurrentDate.get();
			//updateCalendar();
			
			// Decrement by month
			Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			date = CurrentDate.get();
			updateCalendar();
		}
		
		void changeToNextDate_actionPerformed(ActionEvent e){
			// Increments by day
			//CurrentDate.set(getCurrentTomorrow());
			//date = CurrentDate.get();
			//updateCalendar();
			
			// Increments by month
			Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			CurrentDate.set(new CalendarDate(cal));
			date = CurrentDate.get();
			updateCalendar();
		}

		void changeShowTasks_actionPerformed(ActionEvent e){
			parentPanel.populateDays();
		}

		void changeShowEvents_actionPerformed(ActionEvent e){
			parentPanel.populateDays();			
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