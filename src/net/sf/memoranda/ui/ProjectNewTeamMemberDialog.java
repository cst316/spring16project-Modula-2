package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.util.Local;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class ProjectNewTeamMemberDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameTextfield;
	private JTextField emailTextfield;
	private JTextField phoneNumberTextfield;
	
	ProjectDialog dlg = new ProjectDialog(null, Local.getString("New project"));
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
	
		public ProjectNewTeamMemberDialog(Frame frame, String title) {
		super(frame, title, true);
		try {
            jbInit();
            
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
	}

		void jbInit() throws Exception {

			
		this.setPreferredSize(new Dimension(310, 200));
		this.setMinimumSize(new Dimension(310, 200));
		newUserPanel.setLayout(new BoxLayout(newUserPanel, BoxLayout.PAGE_AXIS));
		getContentPane().add(newUserPanel);
		
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
		nameTextfield.setPreferredSize(new Dimension(6, 17));
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
		horizontalStrut_2.setMinimumSize(new Dimension(18, 0));
		phoneNumberPanel.add(horizontalStrut_2);
		
		phoneNumberTextfield = new JTextField();
		phoneNumberTextfield.setPreferredSize(new Dimension(5, 14));
		phoneNumberPanel.add(phoneNumberTextfield);
		phoneNumberTextfield.setColumns(10);
				
		newUserPanel.add(finalPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectNewTeamMemberDialog.this.setVisible(false);
				
	
			}
		});
		finalPanel.add(cancelButton);
		
		JButton acceptButton = new JButton("Accept");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CurrentProject.getContactList().addContact(
		        		nameTextfield.getText(),
		        		emailTextfield.getText(),
		        		phoneNumberTextfield.getText());
		        ProjectNewTeamMemberDialog.this.setVisible(false);
			}
		});
		finalPanel.add(acceptButton);
	}
	

    
}
