package net.sf.memoranda.ui;

import javax.swing.JToolBar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CalendarTaskbarPanel extends JToolBar {
	
		JLabel lblDay = new JLabel("New label");
		JLabel lblMonth = new JLabel("New label");
		JLabel lblYear = new JLabel("New label");
	
		CalendarPanel parentPanel = null;
		CalendarDate date;

		/**
		 * Create the application.
		 */
		 public CalendarTaskbarPanel(CalendarPanel _parentPanel) {
			 
			 super();
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
			
			JButton btnMonthly = new JButton("Month");
			add(btnMonthly);
			
			JButton btnWeekly = new JButton("Week");
			add(btnWeekly);
			
			JButton btnDaily = new JButton("Day");
			add(btnDaily);
			
			JButton btnPreviousMonth = new JButton("<-----");
			btnPreviousMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					changeToPreviousDate_actionPerformed(arg0);
				}
			});
			add(btnPreviousMonth);
			
			
			lblDay.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblDay.setText(this.getDay());
			add(lblDay);
			
			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblMonth.setText(this.getMonth());
			add(lblMonth);
			
			lblYear.setFont(new Font("Tahoma", Font.PLAIN, 29));
			lblYear.setText(this.getYear());
			add(lblYear);
			
			JButton btnNextMonth = new JButton("----->");
			btnNextMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					changeToNextDate_actionPerformed(arg1);
				}
			});
			add(btnNextMonth);
			
			JCheckBox chckbxTasks = new JCheckBox("Show Tasks");
			chckbxTasks.setSelected(true);
			add(chckbxTasks);
			
			JCheckBox chckbxEvents = new JCheckBox("Show Events");
			chckbxEvents.setSelected(true);
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
		}
		
		void changeToPreviousDate_actionPerformed(ActionEvent e){
			CurrentDate.set(getCurrentYesterday());
			date = CurrentDate.get();
			updateCalendar();
		}
		
		void changeToNextDate_actionPerformed(ActionEvent e){
			CurrentDate.set(getCurrentTomorrow());
			date = CurrentDate.get();
			updateCalendar();
		}
		
		protected CalendarDate getCurrentYesterday() {
	        Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
	        cal.roll(Calendar.DATE, false);
	        return new CalendarDate(cal);
	    }
		
		protected CalendarDate getCurrentTomorrow() {
	        Calendar cal = CalendarDate.toCalendar(date.getDay(), date.getMonth(), date.getYear());
	        cal.roll(Calendar.DATE, true);
	        return new CalendarDate(cal);
	    }

	}