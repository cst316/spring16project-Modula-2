package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.*;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;

public class ProjectNewTeamMemberDialog extends JPanel {
	private JTextField nameTextfield;
	private JTextField emailTextfield;
	private JTextField phoneNumberTextfield;
	public ProjectNewTeamMemberDialog() {
		
		JPanel newUserPanel = new JPanel();
		newUserPanel.setPreferredSize(new Dimension(300, 170));
		newUserPanel.setMinimumSize(new Dimension(500, 500));
		newUserPanel.setLayout(new BoxLayout(newUserPanel, BoxLayout.PAGE_AXIS));
		add(newUserPanel);
		
		JPanel titlePanel = new JPanel();
		newUserPanel.add(titlePanel);
		
		JLabel lblNewLabel = new JLabel("Enter New Team Member");
		titlePanel.add(lblNewLabel);
		
		JPanel entryPanel = new JPanel();
		entryPanel.setPreferredSize(new Dimension(100, 100));
		newUserPanel.add(entryPanel);
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.PAGE_AXIS));
		
		JPanel usernamePanel = new JPanel();
		entryPanel.add(usernamePanel);
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.LINE_AXIS));
		
		JLabel nameLabel = new JLabel("Name");
		usernamePanel.add(nameLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(71, 0));
		usernamePanel.add(horizontalStrut);
		
		nameTextfield = new JTextField();
		nameTextfield.setPreferredSize(new Dimension(6, 15));
		usernamePanel.add(nameTextfield);
		nameTextfield.setColumns(10);
		
		JPanel emailPanel = new JPanel();
		entryPanel.add(emailPanel);
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.LINE_AXIS));
		
		JLabel emailLabel = new JLabel("Email");
		emailPanel.add(emailLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(73, 0));
		emailPanel.add(horizontalStrut_1);
		
		emailTextfield = new JTextField();
		emailTextfield.setPreferredSize(new Dimension(6, 15));
		emailPanel.add(emailTextfield);
		emailTextfield.setColumns(10);
		
		JPanel phoneNumberPanel = new JPanel();
		entryPanel.add(phoneNumberPanel);
		phoneNumberPanel.setLayout(new BoxLayout(phoneNumberPanel, BoxLayout.LINE_AXIS));
		
		JLabel phoneNumLabel = new JLabel("Phone Number");
		phoneNumberPanel.add(phoneNumLabel);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		phoneNumberPanel.add(horizontalStrut_2);
		
		phoneNumberTextfield = new JTextField();
		phoneNumberTextfield.setPreferredSize(new Dimension(6, 15));
		phoneNumberPanel.add(phoneNumberTextfield);
		phoneNumberTextfield.setColumns(10);
		
		JPanel finalPanel = new JPanel();
		newUserPanel.add(finalPanel);
		
		JButton cancelButton = new JButton("Cancel");
		finalPanel.add(cancelButton);
		
		JButton acceptButton = new JButton("Accept");
		finalPanel.add(acceptButton);
	}
	

    
}
