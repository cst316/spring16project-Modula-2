package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class TaskPlanningRecordLogDialog extends JDialog {
	public boolean CANCELLED = false;
	
	GridBagConstraints gbc;
	
    public JPanel mainLayout = new JPanel(new GridBagLayout());

    // Top tier
    public JPanel taskLayout = new JPanel(new GridBagLayout());
    
    public JLabel taskNumLabel = new JLabel();
    public JSpinner taskNum = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    
    public JLabel taskNameLabel = new JLabel();
    public JTextArea taskName = new JTextArea();
    
    // Middle tier
    public JPanel logLayout = new JPanel(new GridBagLayout());
    
    public JLabel plannedHourLabel = new JLabel();
    public JSpinner plannedHour = new JSpinner(new SpinnerNumberModel(1,0,Integer.MAX_VALUE,0.25));
    
    public JLabel plannedValueLabel = new JLabel();
    public JSpinner plannedValue = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));

    public JLabel plannedDateLabel = new JLabel();
    public JSpinner plannedDate = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
    
    public JLabel completedCheckboxLabel = new JLabel();
    public JCheckBox completedCheckbox = new JCheckBox();
    
    public JLabel earnedValueLabel = new JLabel();
    public JSpinner earnedValue = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
    
    public JLabel actualDateLabel = new JLabel();
    public JSpinner actualDate = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
    
    // Bottom tier
    public JPanel buttonLayout = new JPanel(new GridBagLayout());

    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    

    

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

        // Top tier stuff (task # / task name)
        // Task # label
        taskNumLabel.setText("Task #:");
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(8,16,0,8);
        taskLayout.add(taskNumLabel, gbc);
        
        // Task # spinner
        gbc.gridx = 1; gbc.gridy = 0; gbc.insets = new Insets(8,8,0,8);
        taskLayout.add(taskNum,gbc);
        
        // Task name label
        taskNameLabel.setText("Task name:");
        gbc.gridx = 2; gbc.gridy = 0; gbc.insets = new Insets(8,16,0,8);
        taskLayout.add(taskNameLabel, gbc);
        
        // Task name text area
        taskName.setLineWrap(true);
        taskName.setColumns(64);
        gbc.gridx = 3; gbc.gridy = 0; gbc.insets = new Insets(8,8,0,16);
        taskLayout.add(taskName,gbc);
        
        // Middle tier stuff
        // Planned hour label
        plannedHourLabel.setText("Planned hours:");
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedHourLabel,gbc);
        
        // Planned hour spinner
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedHour,gbc);
        
        // Planned value label
        plannedValueLabel.setText("Planned value:");
        gbc.gridx = 3; gbc.gridy = 0; gbc.gridwidth = 1; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedValueLabel,gbc);
        
        // Planned value
        gbc.gridx = 4; gbc.gridy = 0; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedValue,gbc);
        
        // Planned date label
        plannedDateLabel.setText("Planned date:");
        gbc.gridx = 5; gbc.gridy = 0; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedDateLabel,gbc);
        
        // Planned date
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        plannedDate.setEditor(new JSpinner.DateEditor(plannedDate, sdf.toPattern()));
        gbc.gridx = 6; gbc.gridy = 0; gbc.insets = new Insets(16,8,0,16);
        logLayout.add(plannedDate,gbc);
        
        // Completed label
        completedCheckboxLabel.setText("Completed");
        gbc.gridx = 1; gbc.gridy = 1; gbc.insets = new Insets(32,32,0,4);
        logLayout.add(completedCheckboxLabel,gbc);
        
        // Completed checkbox
        completedCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	completedCheckbox_actionPerformed(e);
            }
        });
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.insets = new Insets(32,4,0,8);
        logLayout.add(completedCheckbox,gbc);
        
        // Earned value label
        earnedValueLabel.setText("Planned value:");
        gbc.gridx = 3; gbc.gridy = 1; gbc.insets = new Insets(32,8,0,16);
        logLayout.add(earnedValueLabel,gbc);
        
        // Earned value
        gbc.gridx = 4; gbc.gridy = 1; gbc.insets = new Insets(32,8,0,16);
        earnedValue.setEnabled(completedCheckbox.isSelected());
        logLayout.add(earnedValue,gbc);
        
        // Actual date label
        actualDateLabel.setText("Actual date:");
        gbc.gridx = 5; gbc.gridy = 1; gbc.insets = new Insets(32,8,0,16);
        logLayout.add(actualDateLabel,gbc);
        
        // Actual date
        sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        actualDate.setEditor(new JSpinner.DateEditor(actualDate, sdf.toPattern()));
        actualDate.setEnabled(completedCheckbox.isSelected());
        gbc.gridx = 6; gbc.gridy = 1; gbc.insets = new Insets(32,8,0,16);
        logLayout.add(actualDate,gbc);
        
        // Bottom tier stuff (ok/cancel button)
        // Ok button
        okButton.setMaximumSize(new Dimension(100, 26));
        okButton.setMinimumSize(new Dimension(100, 26));
        okButton.setPreferredSize(new Dimension(100, 26));
        okButton.setText(Local.getString("Ok"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(16,0,0,8);
        buttonLayout.add(okButton,gbc);
        
        // Cancel button
        cancelButton.setMaximumSize(new Dimension(100, 26));
        cancelButton.setMinimumSize(new Dimension(100, 26));
        cancelButton.setPreferredSize(new Dimension(100, 26));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        gbc.gridx = 1; gbc.gridy = 0; gbc.insets = new Insets(16,0,0,0);
        buttonLayout.add(cancelButton,gbc);
        
        // Clear insets
        gbc.insets = new Insets(0,0,0,0);
        
        // Add top tier layout to mainLayout
        gbc.gridx = 0; gbc.gridy = 0;
        mainLayout.add(taskLayout,gbc);
        
        // Add middle tier layout to mainLayout
        gbc.gridx = 0; gbc.gridy = 1;
        mainLayout.add(logLayout, gbc);
        
        // Add bottom tier layout to mainLayout
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = gbc.EAST;
        mainLayout.add(buttonLayout, gbc);
        
        // Add main layout to dialog
        this.add(mainLayout);
    }
   
    void okB_actionPerformed(ActionEvent e) {
    	
    	Date pd = (Date) plannedDate.getValue();
    	Date ad = (Date) actualDate.getValue();

    	CurrentProject.getTaskPlanningLog().addTaskPlanningEntry((int) taskNum.getValue(),
    			                                                 taskName.getText(),
    			                                                 (double) plannedHour.getValue(),
    			                                                 (int) plannedValue.getValue(),
    			                                                 (int) earnedValue.getValue(),
    			                                                 new CalendarDate(pd),
    			                                                 new CalendarDate(ad),
    			                                                 completedCheckbox.isSelected());   	
    	
        this.dispose();
    }
    
    protected void completedCheckbox_actionPerformed(ActionEvent e) {
		earnedValue.setEnabled(completedCheckbox.isSelected());
		actualDate.setEnabled(completedCheckbox.isSelected());
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
