package net.sf.memoranda.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
 
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import javax.swing.DefaultComboBoxModel;
import net.sf.memoranda.Phase;
import net.sf.memoranda.TimeEntry;

import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DropMode;
import javax.swing.SpinnerListModel;
 
public class TimeRecordLogDialog extends JDialog {
      	JPanel mPanel = new JPanel(new BorderLayout());
        JPanel areaPanel = new JPanel();
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelB = new JButton();
        JButton okB = new JButton();
        Border border1;
        Border border2;
        JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel header = new JLabel();
        public boolean CANCELLED = true;
        Border border3;
        Border border4;
        JPanel jPanel2 = new JPanel();
        Border border8;
       
        JPanel jPanel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel jPanel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel6 = new JLabel();
        JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        public JSpinner startDate;
        JSpinner endDate;
        //JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jPanelEffort = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jEndTime = new JLabel();
        // added by rawsushi
        JLabel jInteruptionTime = new JLabel();
       
        //Forbid to set dates outside the bounds
        CalendarDate startDateMin = CurrentProject.get().getStartDate();
        CalendarDate startDateMax = CurrentProject.get().getEndDate();
        CalendarDate endDateMin = startDateMin;
        CalendarDate endDateMax = startDateMax;
        private final JLabel lblStartTime = new JLabel("Start Time");
        private final JLabel lblPhase = new JLabel("Phase");
       
        SpinnerDateModel model = new SpinnerDateModel();
       
        public JSpinner logStartTime = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        public JSpinner logInterruptTime = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        public JSpinner logEndTime= new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
       
        private final JComboBox dpPhaseSelector = new JComboBox();
       
        private final JLabel lblComments = new JLabel("Comments");
        private final JTextArea textArea = new JTextArea();
 
        public TimeRecordLogDialog(Frame frame, String title) {
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
            border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            border2 = BorderFactory.createEtchedBorder(Color.white,
                new Color(142, 142, 142));
            border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0),
            Local.getString("Time Entry"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
            border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
            border8 = BorderFactory.createEtchedBorder(Color.white,
                new Color(178, 178, 178));
            cancelB.setMaximumSize(new Dimension(100, 26));
            cancelB.setMinimumSize(new Dimension(100, 26));
            cancelB.setPreferredSize(new Dimension(100, 26));
            cancelB.setText(Local.getString("Cancel"));
            cancelB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelB_actionPerformed(e);
                }
            });
 
            startDate = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
            okB.setMaximumSize(new Dimension(100, 26));
            okB.setMinimumSize(new Dimension(100, 26));
            okB.setPreferredSize(new Dimension(100, 26));
            okB.setText(Local.getString("Ok"));
            okB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    okB_actionPerformed(e);
                }
            });
           
            this.getRootPane().setDefaultButton(okB);
            mPanel.setBorder(border1);
            areaPanel.setBorder(border2);
            dialogTitlePanel.setBackground(Color.WHITE);
            dialogTitlePanel.setBorder(border4);
            //dialogTitlePanel.setMinimumSize(new Dimension(159, 52));
            //dialogTitlePanel.setPreferredSize(new Dimension(159, 52));
            header.setFont(new java.awt.Font("Dialog", 0, 20));
            header.setForeground(new Color(0, 0, 124));
            header.setText("New Time Entry");
            header.setIcon(new ImageIcon(net.sf.memoranda.ui.TaskDialog.class.getResource(
                "resources/icons/task48.png")));
            GridBagConstraints gbCon = new GridBagConstraints();
            gbCon.gridwidth = GridBagConstraints.REMAINDER;
            gbCon.weighty = 1;
            gbCon = new GridBagConstraints();
            gbCon.gridwidth = GridBagConstraints.REMAINDER;
            gbCon.weighty = 1;
            gbCon.anchor = GridBagConstraints.WEST;
            gbCon = new GridBagConstraints();
            gbCon.gridwidth = GridBagConstraints.REMAINDER;
            gbCon.weighty = 3;
 
            jInteruptionTime.setMaximumSize(new Dimension(100, 16));
            jInteruptionTime.setMinimumSize(new Dimension(60, 16));
            jInteruptionTime.setText("Interruption Time");
 
            startDate.setBorder(border8);
            startDate.setPreferredSize(new Dimension(80, 24));                
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
            // //Added by (jcscoobyrs) on 14-Nov-2003 at 10:45:16 PM
            startDate.setEditor(new JSpinner.DateEditor(startDate, sdf.toPattern()));
 
           
 
            jLabel6.setText("Start Date");
            //jLabel6.setPreferredSize(new Dimension(60, 16));
            jLabel6.setMinimumSize(new Dimension(60, 16));
            jLabel6.setMaximumSize(new Dimension(100, 16));
           
           
           
         
            jEndTime.setMaximumSize(new Dimension(100, 16));
            jEndTime.setMinimumSize(new Dimension(60, 16));
            //jLabel7.setPreferredSize(new Dimension(60, 16));
            jEndTime.setText("End Time");
            jPanel4.add(jEndTime, null);
            jPanel4.add(logEndTime);
            getContentPane().add(mPanel);
            mPanel.add(areaPanel, BorderLayout.CENTER);
            mPanel.add(buttonsPanel, BorderLayout.SOUTH);
            buttonsPanel.add(lblComments);
            textArea.setLineWrap(true);
            textArea.setColumns(70);
           
            buttonsPanel.add(textArea);
            buttonsPanel.add(okB, null);
            buttonsPanel.add(cancelB, null);
            this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
            dialogTitlePanel.add(header, null);
            areaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            areaPanel.add(jPanel2);
            jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            jPanel2.add(jPanel6);
            jPanel6.add(jLabel6, null);
            jPanel6.add(startDate, null);
            jPanel2.add(jPanel1);
           
            jPanel1.add(lblStartTime);
            jPanel1.add(logStartTime);
           
            // added by rawsushi
            jPanel2.add(jPanelEffort);
            jPanelEffort.add(jInteruptionTime, null);
            jPanelEffort.add(logInterruptTime);
 
            jPanel2.add(jPanel4);
            jPanel2.add(jPanel3);
           
            jPanel3.add(lblPhase);
            dpPhaseSelector.setModel(new DefaultComboBoxModel(Phase.values()));
            dpPhaseSelector.setToolTipText("(select a phase)");
           
            jPanel3.add(dpPhaseSelector);
 
            JSpinner.DateEditor dStart = new JSpinner.DateEditor(logStartTime, "HH:mm");
            logStartTime.setEditor(dStart);
            JSpinner.DateEditor dInturrupt = new JSpinner.DateEditor(logInterruptTime, "HH:mm");
            logInterruptTime.setEditor(dInturrupt);
            JSpinner.DateEditor dEnd = new JSpinner.DateEditor(logEndTime, "HH:mm");
            logEndTime.setEditor(dEnd);
            
            
        }
       
        void okB_actionPerformed(ActionEvent e) {
        	/*public TimeEntry addTimeEntry(CalendarDate date,
                    Calendar startTime, 
                    Calendar endTime, 
                    int interruptionTime, 
                    Phase phase, 
                    String comments) {
        	}*/
        	
        	Calendar c = Calendar.getInstance();
        	c.setTime((Date) startDate.getValue());
        	int day = c.get(Calendar.DAY_OF_MONTH);
        	int month = c.get(Calendar.MONTH);
        	int year = c.get(Calendar.YEAR);
        	CalendarDate date = new CalendarDate(day, month, year);
        	
        	Calendar startTime = Calendar.getInstance();
        	startTime.setTime((Date) startDate.getValue());
        	
        	Calendar endTime = Calendar.getInstance();
        	endTime.setTime((Date) logEndTime.getValue());
        	
        	int interruptionTime = 0;
        	Calendar it = Calendar.getInstance();
        	it.setTime((Date) logInterruptTime.getValue());
        	interruptionTime += it.get(Calendar.HOUR_OF_DAY) * 60 + it.get(Calendar.MINUTE);
        	
        	String ps = dpPhaseSelector.getSelectedItem().toString();
        	Phase phase = null;
        	for (Phase p : Phase.values()) {
    			if (ps.equals(p.toString())) {
    				phase = p;
    			}
    		}
        	
        	String comments = textArea.getText();
        	
        	CurrentProject.getTimeLog().addTimeEntry(date, startTime, endTime, interruptionTime, phase, comments);
        	
            this.dispose();
        }
 
        void cancelB_actionPerformed(ActionEvent e) {
            this.dispose();
        }
       
 
}