package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class ProjectNewTeamMemberDialog extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean CANCELLED = true;
	private JTextField nameTextfield;
	private JTextField emailTextfield;
	private JTextField phoneNumberTextfield;
	
	JPanel newUserPanel = new JPanel();
	JPanel titlePanel = new JPanel();
	JLabel lblNewLabel = new JLabel("Enter New Team Member");
	JPanel entryPanel = new JPanel();
	JPanel usernamePanel = new JPanel();
	JPanel emailPanel = new JPanel();
	JLabel emailLabel = new JLabel("Email");
	JPanel phoneNumberPanel = new JPanel();
	JLabel phoneNumLabel = new JLabel("Phone Number");
	JPanel finalPanel = new JPanel();
	
		public ProjectNewTeamMemberDialog(Frame frame) {
		super();
		try {
            jbInit();
            
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
	}
		
		void jbInit() throws Exception {
		
		newUserPanel.setPreferredSize(new Dimension(300, 170));
		newUserPanel.setMinimumSize(new Dimension(500, 500));
		newUserPanel.setLayout(new BoxLayout(newUserPanel, BoxLayout.PAGE_AXIS));
		add(newUserPanel);
		
		newUserPanel.add(titlePanel);
		
		titlePanel.add(lblNewLabel);
		
		entryPanel.setPreferredSize(new Dimension(100, 100));
		newUserPanel.add(entryPanel);
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.PAGE_AXIS));
				
		entryPanel.add(usernamePanel);
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.LINE_AXIS));
		
		JLabel nameLabel = new JLabel("Name");
		usernamePanel.add(nameLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(70, 0));
		usernamePanel.add(horizontalStrut);
		
		nameTextfield = new JTextField();
		nameTextfield.setPreferredSize(new Dimension(6, 15));
		usernamePanel.add(nameTextfield);
		nameTextfield.setColumns(10);
		
		entryPanel.add(emailPanel);
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.LINE_AXIS));
		
		emailPanel.add(emailLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(73, 0));
		emailPanel.add(horizontalStrut_1);
		
		emailTextfield = new JTextField();
		emailTextfield.setPreferredSize(new Dimension(6, 15));
		emailPanel.add(emailTextfield);
		emailTextfield.setColumns(10);
		
		entryPanel.add(phoneNumberPanel);
		phoneNumberPanel.setLayout(new BoxLayout(phoneNumberPanel, BoxLayout.LINE_AXIS));
		
		phoneNumberPanel.add(phoneNumLabel);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(27, 0));
		phoneNumberPanel.add(horizontalStrut_2);
		
		phoneNumberTextfield = new JTextField();
		phoneNumberTextfield.setPreferredSize(new Dimension(6, 15));
		phoneNumberPanel.add(phoneNumberTextfield);
		phoneNumberTextfield.setColumns(10);
				
		newUserPanel.add(finalPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		finalPanel.add(cancelButton);
		
		JButton acceptButton = new JButton("Accept");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        CANCELLED = true;
//		        this.dispose();
			}
		});
		finalPanel.add(acceptButton);
	}
	

    
}
