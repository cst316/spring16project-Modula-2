package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import javax.swing.DefaultComboBoxModel;

public class DefectDialog extends JDialog {

	JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton btnCancel = new JButton();
    JButton btnOK = new JButton();
    Border border1;
    Border border2;
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    public boolean CANCELLED = true;
    JPanel jpNumberDescription = new JPanel(new GridBagLayout());
    Border border3;
    Border border4;
    JPanel jPanel2 = new JPanel(new GridLayout(3, 2));
    JTextField tfNumber = new JTextField();
    
    // added by rawsushi
    JTextField txtEstFixTime = new JTextField();
    JTextArea txtaDescription = new JTextArea();
    JScrollPane descriptionScrollPane = new JScrollPane(txtaDescription);
	JLabel lblActFixTime = new JLabel("Act Fix Time(mins)");
	JTextField txtActFixTime = new JTextField();
    
    Border border8;
    CalendarFrame startCalFrame = new CalendarFrame();
    CalendarFrame endCalFrame = new CalendarFrame();
    //FOR MULTI-LANGUAGE SUPPORT
    /*
    String[] type = {Local.getString("10 Documentation"), Local.getString(20),
	    		Local.getString(30), Local.getString(40),
				Local.getString(50), Local.getString(60),
				Local.getString(70), Local.getString(80), 
				Local.getString(90), Local.getString(100)};
	*/
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    JPanel jpType = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JPanel jpDateFound = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel lblDateFound = new JLabel();
    JButton btnSetDateFound = new JButton();
    JPanel jpDateFixed = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel lblDateFixed = new JLabel();
    
    JSpinner spnDateFound;
    JSpinner spnDateFixed;
    JButton btnSetDateFixed = new JButton();
    JPanel jpSetNotification = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jpFixTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    JButton btnSetNotification = new JButton();
    JComboBox cmbType = new JComboBox(/*type*/);
    JLabel lblType = new JLabel();
    // added by rawsushi
    JLabel lblEstFixTime = new JLabel();
    JLabel lblDescription = new JLabel();
	JCheckBox chkDateFixed = new JCheckBox();
	
	JPanel jPanelProgress = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JLabel jLabelProgress = new JLabel();
	JSpinner progress = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
	
	//Forbid to set dates outside the bounds
	CalendarDate startDateMin = CurrentProject.get().getStartDate();
	CalendarDate startDateMax = CurrentProject.get().getEndDate();
	CalendarDate endDateMin = startDateMin;
	CalendarDate endDateMax = startDateMax;
	JLabel lblInjection = new JLabel("Injection");
	JTextField txtInjection = new JTextField();
	JLabel lblFixReference = new JLabel("Fix Reference");
	JTextField txtFixReference = new JTextField();
	JCheckBox chkFixReference = new JCheckBox("");
	
    
	
	public DefectDialog(Frame frame, String title) {
        
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
		this.setSize(new Dimension(430,300));
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(142, 142, 142));
        border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0), 
        Local.getString("Defect Number (FILL IN SEQUENTIALLY FOR NOW!!!)"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
        border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        border8 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
        btnCancel.setMaximumSize(new Dimension(100, 26));
        btnCancel.setMinimumSize(new Dimension(100, 26));
        btnCancel.setPreferredSize(new Dimension(100, 26));
        btnCancel.setText(Local.getString("Cancel"));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });

        spnDateFound = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
        spnDateFixed = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
		
        chkDateFixed.setSelected(false);
		chkDateFixed.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkDateFixed_actionPerformed(e);
			}
		});
		
		chkFixReference.setSelected(false);
		chkFixReference.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkFixReference_actionPerformed(e);
			}
		});
		
        btnOK.setMaximumSize(new Dimension(100, 26));
        btnOK.setMinimumSize(new Dimension(100, 26));
        btnOK.setPreferredSize(new Dimension(100, 26));
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        
        this.getRootPane().setDefaultButton(btnOK);
        mPanel.setBorder(border1);
        areaPanel.setBorder(border2);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(border4);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Record New Defect"));
        header.setIcon(new ImageIcon(DefectDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/mimetypes/application/default.png")));
        
        GridBagLayout gbLayout = (GridBagLayout) jpNumberDescription.getLayout();
        jpNumberDescription.setBorder(border3);
				
        tfNumber.setBorder(border8);
        tfNumber.setPreferredSize(new Dimension(24, 24));
        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 1;
        gbLayout.setConstraints(tfNumber,gbCon);
        
        lblDescription.setMaximumSize(new Dimension(100, 16));
        lblDescription.setMinimumSize(new Dimension(60, 16));
        lblDescription.setText(Local.getString("Description"));
        gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(lblDescription,gbCon);

        txtaDescription.setBorder(border8);
        txtaDescription.setPreferredSize(new Dimension(375, 387)); // 3 additional pixels from 384 so that the last line is not cut off
        txtaDescription.setLineWrap(true);
        txtaDescription.setWrapStyleWord(true);
        gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 3;
        descriptionScrollPane.setPreferredSize(new Dimension(375,96));
        gbLayout.setConstraints(descriptionScrollPane,gbCon);

        lblEstFixTime.setMaximumSize(new Dimension(100, 16));
        lblEstFixTime.setMinimumSize(new Dimension(60, 16));
        lblEstFixTime.setText("Est Fix Time(mins)");
        txtEstFixTime.setBorder(border8);
        txtEstFixTime.setPreferredSize(new Dimension(30, 24));
        lblActFixTime.setEnabled(false);
        lblActFixTime.setLabelFor(txtActFixTime);
		txtActFixTime.setEnabled(false);
		txtActFixTime.setColumns(10);

        spnDateFound.setBorder(border8);
        spnDateFound.setPreferredSize(new Dimension(80, 24));                
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		// //Added by (jcscoobyrs) on 14-Nov-2003 at 10:45:16 PM
		spnDateFound.setEditor(new JSpinner.DateEditor(spnDateFound, sdf.toPattern()));

        spnDateFound.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)spnDateFound.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	spnDateFound.setModel(sdm);

                if (ignoreStartChanged)
                    return;
                ignoreStartChanged = true;
                Date sd = (Date) spnDateFound.getModel().getValue();
                Date ed = (Date) spnDateFixed.getModel().getValue();
                if (sd.after(ed) && chkDateFixed.isSelected()) {
                    spnDateFound.getModel().setValue(ed);
                    sd = ed;
                }
				if ((startDateMax != null) && sd.after(startDateMax.getDate())) {
					spnDateFound.getModel().setValue(startDateMax.getDate());
                    sd = startDateMax.getDate();
				}
                if ((startDateMin != null) && sd.before(startDateMin.getDate())) {
                    spnDateFound.getModel().setValue(startDateMin.getDate());
                    sd = startDateMin.getDate();
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });

        lblDateFound.setText(Local.getString("Date Found"));
        lblDateFound.setMinimumSize(new Dimension(60, 16));
        lblDateFound.setMaximumSize(new Dimension(100, 16));
        btnSetDateFound.setMinimumSize(new Dimension(24, 24));
        btnSetDateFound.setPreferredSize(new Dimension(24, 24));
        btnSetDateFound.setText("");
        btnSetDateFound.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        btnSetDateFound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDateB_actionPerformed(e);
            }
        });
        lblDateFixed.setMaximumSize(new Dimension(270, 16));
        lblDateFixed.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDateFixed.setText("Date Fixed");
        spnDateFixed.setBorder(border8);
        spnDateFixed.setPreferredSize(new Dimension(80, 24));
        
		spnDateFixed.setEditor(new JSpinner.DateEditor(spnDateFixed, sdf.toPattern())); //Added by (jcscoobyrs) on
		//14-Nov-2003 at 10:45:16PM
        
        spnDateFixed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)spnDateFixed.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	spnDateFixed.setModel(sdm);
            	
                if (ignoreEndChanged)
                    return;
                ignoreEndChanged = true;
                Date sd = (Date) spnDateFound.getModel().getValue();
                Date ed = (Date) spnDateFixed.getModel().getValue();				
				if (ed.before(sd)) {
                    spnDateFixed.getModel().setValue(ed);
                    ed = sd;
                }
				if ((endDateMax != null) && ed.after(endDateMax.getDate())) {
					spnDateFixed.getModel().setValue(endDateMax.getDate());
                    ed = endDateMax.getDate();
				}
                if ((endDateMin != null) && ed.before(endDateMin.getDate())) {
                    spnDateFixed.getModel().setValue(endDateMin.getDate());
                    ed = endDateMin.getDate();
                }
				endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
        btnSetDateFixed.setMinimumSize(new Dimension(24, 24));
        btnSetDateFixed.setPreferredSize(new Dimension(24, 24));
        btnSetDateFixed.setText("");
        btnSetDateFixed.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        btnSetDateFixed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEndDateB_actionPerformed(e);
            }
        });
        
        btnSetNotification.setText(Local.getString("Set notification"));
        btnSetNotification.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/notify.png")));
        btnSetNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNotifB_actionPerformed(e);
            }
        });
        lblType.setMaximumSize(new Dimension(100, 16));
        lblType.setMinimumSize(new Dimension(60, 16));
        lblType.setText("Type");
        jpType.add(lblType, null);
        cmbType.setModel(new DefaultComboBoxModel(new String[] {"10 Documentation", "20 Syntax", "30 Build, Package", "40 Assignment", "50 Interface", "60 Checking", "70 Data", "80 Function", "90 System", "100 Environment"}));
        
                cmbType.setFont(new java.awt.Font("Dialog", 0, 11));
                jpType.add(cmbType, null);
                
                cmbType.setSelectedItem(Local.getString("Normal"));
       
        txtFixReference.setColumns(10);
		txtInjection.setColumns(10);
        jpType.add(lblInjection);
        
        jpType.add(txtInjection);
        getContentPane().add(mPanel);
        mPanel.add(areaPanel, BorderLayout.CENTER);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(btnOK, null);
        buttonsPanel.add(btnCancel, null);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        areaPanel.add(jpNumberDescription, BorderLayout.NORTH);
        jpNumberDescription.add(tfNumber, null);
        jpNumberDescription.add(lblDescription);
        jpNumberDescription.add(descriptionScrollPane, null);
        areaPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jpDateFound, null);
        jpDateFound.add(lblDateFound, null);
        jpDateFound.add(spnDateFound, null);
        jpDateFound.add(btnSetDateFound, null);
        jPanel2.add(jpDateFixed, null);
		jpDateFixed.add(chkDateFixed, null);
        jpDateFixed.add(lblDateFixed, null);
        jpDateFixed.add(spnDateFixed, null);
        jpDateFixed.add(btnSetDateFixed, null);
        // added by rawsushi
        jPanel2.add(jpFixTime, null);
        jpFixTime.add(lblEstFixTime, null);
        jpFixTime.add(txtEstFixTime, null);
        
        jpFixTime.add(lblActFixTime);
        
        jpFixTime.add(txtActFixTime);
        
        
        jPanel2.add(jpType, null);
        jPanel2.add(jpSetNotification, null);
        
        jpSetNotification.add(btnSetNotification, null);
        
        jPanelProgress.add(chkFixReference);
        lblFixReference.setEnabled(false);
        
        
        jPanelProgress.add(lblFixReference);
        
        jPanelProgress.add(txtFixReference);
        
        jLabelProgress.setText(Local.getString("Progress"));
        jPanelProgress.add(jLabelProgress, null);
        jPanelProgress.add(progress, null);
        jPanel2.add(jPanelProgress);
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged)
                    return;
                spnDateFound.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                spnDateFixed.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
	}
	
	
	public void setStartDate(CalendarDate d) {
		this.spnDateFound.getModel().setValue(d.getDate());
	}
	
	public void setEndDate(CalendarDate d) {		
		if (d != null) 
			this.spnDateFixed.getModel().setValue(d.getDate());
	}
	
	public void setStartDateLimit(CalendarDate min, CalendarDate max) {
		this.startDateMin = min;
		this.startDateMax = max;
	}
	
	public void setEndDateLimit(CalendarDate min, CalendarDate max) {
		this.endDateMin = min;
		this.endDateMax = max;
	}
	
    void okB_actionPerformed(ActionEvent e) {
	CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
	
	void chkDateFixed_actionPerformed(ActionEvent e) {
		spnDateFixed.setEnabled(chkDateFixed.isSelected());
		btnSetDateFixed.setEnabled(chkDateFixed.isSelected());
		lblDateFixed.setEnabled(chkDateFixed.isSelected());
		lblActFixTime.setEnabled(chkDateFixed.isSelected());
		txtActFixTime.setEnabled(chkDateFixed.isSelected());
		if(chkDateFixed.isSelected()) {
			Date currentEndDate = (Date) spnDateFixed.getModel().getValue();
			Date currentStartDate = (Date) spnDateFound.getModel().getValue();
			if(currentEndDate.getTime() < currentStartDate.getTime()) {
				spnDateFixed.getModel().setValue(currentStartDate);
			}
		}
	}
	
	void chkFixReference_actionPerformed(ActionEvent e) {
		lblFixReference.setEnabled(chkFixReference.isSelected());
		txtFixReference.setEnabled(chkFixReference.isSelected());
	}
	
    void setStartDateB_actionPerformed(ActionEvent e) {
        startCalFrame.setLocation(btnSetDateFound.getLocation());
        startCalFrame.setSize(200, 200);
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();

    }

    void setEndDateB_actionPerformed(ActionEvent e) {
        endCalFrame.setLocation(btnSetDateFixed.getLocation());
        endCalFrame.setSize(200, 200);
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }
    
    void setNotifB_actionPerformed(ActionEvent e) {
    	((AppFrame)App.getFrame()).workPanel.dailyItemsPanel.eventsPanel.newEventB_actionPerformed(e, 
			this.tfNumber.getText(), (Date)spnDateFound.getModel().getValue(),(Date)spnDateFixed.getModel().getValue());
    }
}
