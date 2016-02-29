package net.sf.memoranda.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.sf.memoranda.CurrentProject;

import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class DefectsPanel extends JPanel {
	
	public DefectsPanel() {
		
		try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
		
		this.setVisible(true);

	}
	
	public void jbInit() throws Exception {
		
		setLayout(null);
		
		JLabel lblTitle = new JLabel("PSP Defect Recording Log");
		lblTitle.setBounds(201, 17, 179, 17);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.add(lblTitle);
		
		JLabel lblProject = new JLabel("Project: ");
		lblProject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProject.setBounds(405, 11, 53, 28);
		add(lblProject);
		
		JLabel lblProjectName = new JLabel(CurrentProject.get().getTitle());
		lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProjectName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblProjectName.setLabelFor(lblProject);
		lblProjectName.setBounds(468, 14, 238, 23);
		add(lblProjectName);
		
		JButton btnNewDefect = new JButton("New Defect");
		btnNewDefect.setBounds(56, 14, 89, 23);
		add(btnNewDefect);
		
		JButton btnRemoveDefect = new JButton("Remove Defect");
		btnRemoveDefect.setBounds(728, 14, 100, 23);
		add(btnRemoveDefect);
		
	}
}