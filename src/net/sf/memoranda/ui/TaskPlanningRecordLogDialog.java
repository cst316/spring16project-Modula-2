package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class TaskPlanningRecordLogDialog extends JDialog {
	public boolean CANCELLED = false;
	
	GridBagConstraints gbc;
	
    public JPanel mainLayout = new JPanel(new GridBagLayout());

    public JPanel taskLayout = new JPanel(new GridBagLayout());;
    
    public JLabel taskNumLabel = new JLabel();
    public JSpinner taskNum = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    
    public JLabel taskNameLabel = new JLabel();
    public JTextArea taskName = new JTextArea();

    public JLabel startDateLabel;
    public JSpinner startDate;

    

    public TaskPlanningRecordLogDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
	
    void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(937, 524));
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;

        // Task # label
        taskNumLabel.setText("Task #:");
        gbc.gridx = 0; gbc.gridy = 0;
        taskLayout.add(taskNumLabel, gbc);
        
        // Task # spinner
        gbc.gridx = 1; gbc.gridy = 0;
        taskLayout.add(taskNum,gbc);
        
        // Task name label
        taskNameLabel.setText("Task name:");
        gbc.gridx = 2; gbc.gridy = 0;
        taskLayout.add(taskNameLabel, gbc);
        
        // Task name text area
        taskName.setLineWrap(true);
        taskName.setColumns(64);
        gbc.gridx = 3; gbc.gridy = 0;
        taskLayout.add(taskName,gbc);
        
        gbc.gridx = 0; gbc.gridy = 0;
        mainLayout.add(taskLayout,gbc);
    }
   
    void okB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
    	CANCELLED = true;
        this.dispose();
    }
    
    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
}
