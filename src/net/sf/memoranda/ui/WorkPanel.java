package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;
import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();
	CardLayout cardLayout1 = new CardLayout();

	public JButton notesB = new JButton();
	public CalendarPanel calendarPanel = new CalendarPanel(this);
	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
	public ResourcesPanel filesPanel = new ResourcesPanel();
	public PSPPanel pspPanel = new PSPPanel(this);
	public JButton calendarB = new JButton();
	public JButton tasksB = new JButton();
	public JButton eventsB = new JButton();
	public JButton filesB = new JButton();
	public JButton pspB = new JButton();
	JButton currentB = null;
	Border border1;

	
	
	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(
					BevelBorder.LOWERED,
					Color.white,
					Color.white,
					new Color(124, 124, 124),
					new Color(178, 178, 178)),
				BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(Color.white);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		calendarB.setBackground(Color.white);
		calendarB.setMaximumSize(new Dimension(60, 80));
		calendarB.setMinimumSize(new Dimension(30, 30));

		calendarB.setFont(new java.awt.Font("Dialog", 1, 10));
		calendarB.setPreferredSize(new Dimension(50, 50));
		calendarB.setBorderPainted(false);
		calendarB.setContentAreaFilled(false);
		calendarB.setFocusPainted(false);
		calendarB.setHorizontalTextPosition(SwingConstants.CENTER);
		calendarB.setText(Local.getString("Calendar"));
		calendarB.setVerticalAlignment(SwingConstants.TOP);
		calendarB.setVerticalTextPosition(SwingConstants.BOTTOM);
		calendarB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarB_actionPerformed(e);
			}
		});
		calendarB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/agenda.png")));
		calendarB.setOpaque(false);
		calendarB.setMargin(new Insets(0, 0, 0, 0));
		calendarB.setSelected(true);

		eventsB.setBackground(Color.white);
		eventsB.setMaximumSize(new Dimension(60, 80));
		eventsB.setMinimumSize(new Dimension(30, 30));

		eventsB.setFont(new java.awt.Font("Dialog", 1, 10));
		eventsB.setPreferredSize(new Dimension(50, 50));
		eventsB.setBorderPainted(false);
		eventsB.setContentAreaFilled(false);
		eventsB.setFocusPainted(false);
		eventsB.setHorizontalTextPosition(SwingConstants.CENTER);
		eventsB.setText(Local.getString("Events"));
		eventsB.setVerticalAlignment(SwingConstants.TOP);
		eventsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		eventsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventsB_actionPerformed(e);
			}
		});
		eventsB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/events.png")));
		eventsB.setOpaque(false);
		eventsB.setMargin(new Insets(0, 0, 0, 0));
		//eventsB.setSelected(true);

		tasksB.setSelected(true);
		tasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksB_actionPerformed(e);
			}
		});
		tasksB.setVerticalAlignment(SwingConstants.TOP);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(50, 50));
		tasksB.setMinimumSize(new Dimension(30, 30));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(60, 80));
		tasksB.setBackground(Color.white);

		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1085, 478));

		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesB_actionPerformed(e);
			}
		});
		filesB.setFont(new java.awt.Font("Dialog", 1, 10));
		filesB.setVerticalAlignment(SwingConstants.TOP);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(50, 50));
		filesB.setMinimumSize(new Dimension(30, 30));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(60, 80));
		filesB.setBackground(Color.white);
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(calendarPanel, "CALENDAR");
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES");
		panel.add(pspPanel, "PSP");
		toolBar.add(calendarB, null);
		toolBar.add(eventsB, null);
		toolBar.add(tasksB, null);
		toolBar.add(notesB, null);
		toolBar.add(filesB, null);
		currentB = calendarB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		
		pspB.addActionListener(new java.awt.event.ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				pspB_actionPerformed(e);
 			}
 		});
 		
 		pspB.setIcon(new ImageIcon(WorkPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/agenda.png")));
 		pspB.setVerticalTextPosition(SwingConstants.BOTTOM);
 		pspB.setVerticalAlignment(SwingConstants.TOP);
 		pspB.setText("PSP");
 		pspB.setSelected(true);
 		pspB.setPreferredSize(new Dimension(50, 50));
 		pspB.setOpaque(false);
 		pspB.setMinimumSize(new Dimension(30, 30));
 		pspB.setMaximumSize(new Dimension(60, 80));
 		pspB.setMargin(new Insets(0, 0, 0, 0));
 		pspB.setHorizontalTextPosition(SwingConstants.CENTER);
 		pspB.setFont(new Font("Dialog", Font.BOLD, 10));
 		pspB.setFocusPainted(false);
 		pspB.setContentAreaFilled(false);
 		pspB.setBorderPainted(false);
 		pspB.setBackground(Color.WHITE);
 		
 		toolBar.add(pspB);
		panel.setBorder(null);
		calendarPanel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);
		pspPanel.setBorder(null);

		
		
	}

	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesB_actionPerformed(null);
			else if (pan.equals("TASKS"))
				tasksB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				eventsB_actionPerformed(null);
			else if (pan.equals("FILES"))
				filesB_actionPerformed(null);
			else if (pan.equals("PSP"))
				pspB_actionPerformed(null);
		}
	}

	public void calendarB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "CALENDAR");
		setCurrentButton(calendarB);
		Context.put("CURRENT_PANEL", "CALENDAR");
	}

	public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void tasksB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void eventsB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(eventsB);
		Context.put("CURRENT_PANEL", "EVENTS");
	}

	public void filesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		setCurrentButton(filesB);
		Context.put("CURRENT_PANEL", "FILES");
	}
	
	public void pspB_actionPerformed(ActionEvent e) {
 		cardLayout1.show(panel, "PSP");
 		setCurrentButton(pspB);
 		Context.put("CURRENT_PANEL", "PSP");
 	}
	
	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}
}