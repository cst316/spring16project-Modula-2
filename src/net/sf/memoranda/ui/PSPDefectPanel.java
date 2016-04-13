package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.DefectListListener;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Phase;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.ColorScheme;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

public class PSPDefectPanel extends JPanel {
	
	private PSPPanel parentPanel = null;
	
    BorderLayout borderLayout1 = new BorderLayout();

	JToolBar defectsToolbar = new JToolBar();
	JButton newDefectB = new JButton();
    JButton subDefectB = new JButton();
    JButton editDefectB = new JButton();
    JButton removeDefectB = new JButton();
    JButton completeDefectB = new JButton();
    
    JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
	
    JScrollPane scrollPane = new JScrollPane();
    DefectTable defectTable = new DefectTable();
	JMenuItem ppEditDefect = new JMenuItem();
	JPopupMenu defectPPMenu = new JPopupMenu();
	JMenuItem ppRemoveDefect = new JMenuItem();
	JMenuItem ppNewDefect = new JMenuItem();
	JMenuItem ppCompleteDefect = new JMenuItem();
	//JMenuItem ppCalcDefect = new JMenuItem();
	
	private static Vector<DefectListListener> defectListListeners = new Vector<DefectListListener>();
	
	public PSPDefectPanel(PSPPanel pspPanel) {
		setLayout(null);
        try {
            parentPanel = pspPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    } 
    
    void jbInit() throws Exception {
    	
    	defectsToolbar.setFloatable(false);
    	defectsToolbar.setBackground(ColorScheme.getColor("taskbar_primary"));
    	defectsToolbar.setBorder(null);
    		
    	newDefectB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
    	newDefectB.setEnabled(true);
        newDefectB.setMaximumSize(new Dimension(24, 24));
        newDefectB.setMinimumSize(new Dimension(24, 24));
        newDefectB.setToolTipText(Local.getString("Create new defect"));
        newDefectB.setRequestFocusEnabled(false);
        newDefectB.setPreferredSize(new Dimension(24, 24));
        newDefectB.setFocusable(false);
        newDefectB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDefectB_actionPerformed(e);
            }
        });
        newDefectB.setBackground(ColorScheme.getColor("taskbar_primary"));
        newDefectB.setBorderPainted(false);
		
        editDefectB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_edit.png")));
        editDefectB.setBorderPainted(false);
        editDefectB.setFocusable(false);
        editDefectB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDefectB_actionPerformed(e);
            }
        });
        editDefectB.setPreferredSize(new Dimension(24, 24));
        editDefectB.setRequestFocusEnabled(false);
        editDefectB.setToolTipText(Local.getString("Edit defect"));
        editDefectB.setMinimumSize(new Dimension(24, 24));
        editDefectB.setMaximumSize(new Dimension(24, 24));
        editDefectB.setBackground(ColorScheme.getColor("taskbar_primary"));
        
        removeDefectB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
        removeDefectB.setBorderPainted(false);
        removeDefectB.setFocusable(false);
        removeDefectB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeDefectB_actionPerformed(e);
            }
        });
        removeDefectB.setPreferredSize(new Dimension(24, 24));
        removeDefectB.setRequestFocusEnabled(false);
        removeDefectB.setToolTipText(Local.getString("Remove defect"));
        removeDefectB.setMinimumSize(new Dimension(24, 24));
        removeDefectB.setMaximumSize(new Dimension(24, 24));
        removeDefectB.setBackground(ColorScheme.getColor("taskbar_primary"));
        
        completeDefectB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_complete.png")));
        completeDefectB.setBorderPainted(false);
        completeDefectB.setFocusable(false);
        completeDefectB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppCompleteDefect_actionPerformed(e);
            }
        });
        completeDefectB.setPreferredSize(new Dimension(24, 24));
        completeDefectB.setRequestFocusEnabled(false);
        completeDefectB.setToolTipText(Local.getString("Complete defect"));
        completeDefectB.setMinimumSize(new Dimension(24, 24));
        completeDefectB.setMaximumSize(new Dimension(24, 24));
        completeDefectB.setBackground(ColorScheme.getColor("taskbar_primary"));
        
        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(
			Local.getString("Show Active only"));
		ppShowActiveOnlyChB
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleShowActiveOnly_actionPerformed(e);
			}
		});
		
		boolean isShao =
				(Context.get("SHOW_ACTIVE_DEFECTS_ONLY") != null)
					&& (Context.get("SHOW_ACTIVE_DEFECTS_ONLY").equals("true"));
			ppShowActiveOnlyChB.setSelected(isShao);
			toggleShowActiveOnly_actionPerformed(null);
		
		
		this.setLayout(borderLayout1);
	    ppEditDefect.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_edit.png")));
        ppEditDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppEditDefect.setText(Local.getString("Edit defect")+"...");
	    ppEditDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppEditDefect_actionPerformed(e);
	            }
	        });
	    
	    
	    
	    ppEditDefect.setEnabled(false);
	    defectPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
	    ppRemoveDefect.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_remove.png")));
	    ppRemoveDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppRemoveDefect.setText(Local.getString("Remove defect"));
	    ppRemoveDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppRemoveDefect_actionPerformed(e);
	            }
	        });
	    ppRemoveDefect.setEnabled(false);
	    ppNewDefect.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_new.png")));
	    ppNewDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppNewDefect.setText(Local.getString("New defect")+"...");
	    ppNewDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppNewDefect_actionPerformed(e);
	            }
	        });
	    
		ppCompleteDefect.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/todo_complete.png")));
	    ppCompleteDefect.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCompleteDefect.setText(Local.getString("Complete defect"));
		ppCompleteDefect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ppCompleteDefect_actionPerformed(e);
				}
			});
		ppCompleteDefect.setEnabled(false);

		/*
		ppCalcDefect.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCalcDefect.setText(Local.getString("Calculate defect data"));
		ppCalcDefect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ppCalcDefect_actionPerformed(e);
				}
			});
		ppCalcDefect.setEnabled(false);
		*/
        
        
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.getViewport().add(defectTable, null);
		this.add(scrollPane, BorderLayout.CENTER);
        defectsToolbar.add(newDefectB, null);
        defectsToolbar.add(removeDefectB, null);
        defectsToolbar.addSeparator(new Dimension(8, 24));
        defectsToolbar.add(editDefectB, null);
        defectsToolbar.add(completeDefectB, null);

        this.add(defectsToolbar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        defectTable.addMouseListener(ppListener);
		scrollPane.setBounds(0, 41, 625, 342);
		add(scrollPane);
		
		CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                newDefectB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
            }
        });
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, DefectList dl, ResourcesList rl) {
                newDefectB.setEnabled(
                    CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }
            public void projectWasChanged() {
            }
        });
		
		defectTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (defectTable.getRowCount() > 0)&&(defectTable.getSelectedRow() > -1);
                editDefectB.setEnabled(enbl);ppEditDefect.setEnabled(enbl);
                removeDefectB.setEnabled(enbl);ppRemoveDefect.setEnabled(enbl);
				
				ppCompleteDefect.setEnabled(enbl);
				completeDefectB.setEnabled(enbl);
				//ppCalcDefect.setEnabled(enbl);
				
                if (enbl) {   
    				String thisDefectId = defectTable.getModel().getValueAt(defectTable.getSelectedRow(), DefectTable.DEFECT_ID).toString();
    				
                }
            }
        });
		
		
		editDefectB.setEnabled(false);
        removeDefectB.setEnabled(false);
		completeDefectB.setEnabled(false);
	    defectPPMenu.add(ppEditDefect);
	    
	    defectPPMenu.addSeparator();
	    defectPPMenu.add(ppNewDefect);
	    defectPPMenu.add(ppRemoveDefect);
	    
	    defectPPMenu.addSeparator();
	    defectPPMenu.add(ppCompleteDefect);
	    //defectPPMenu.add(ppCalcDefect);
    
	    defectPPMenu.addSeparator();
	    //defectPPMenu.add(ppShowActiveOnlyChB);

	
		// define key actions in PSPDefectPanel:
		// - KEY:DELETE => delete defects (recursively).
		// - KEY:INSERT => insert new Defect if nothing is selected.
		// - KEY:SPACE => finish Defect.
		defectTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(defectTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemoveDefect_actionPerformed(null);
				
				else if(e.getKeyCode()==KeyEvent.VK_INSERT) {
					if(defectTable.getSelectedRows().length>0) {
					}
					else {
						ppNewDefect_actionPerformed(null);						
					}
				}
				
				else if(e.getKeyCode()==KeyEvent.VK_SPACE
						&& defectTable.getSelectedRows().length>0) {
					ppCompleteDefect_actionPerformed(null);
				}
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});	
		
    }

	protected void newDefectB_actionPerformed(ActionEvent arg0) {
		DefectDialog defectdialog = new DefectDialog(App.getFrame(), Local.getString("Record New Defect: " ) + (CurrentProject.getDefectList().getLastDefectId()));
		
        defectdialog.spnDateFound.getModel().setValue(CurrentDate.get().getDate());
        defectdialog.setLocationRelativeTo(this);
        defectdialog.pack();
        defectdialog.setVisible(true);
        
        if (defectdialog.CANCELLED)
            return;
        
        CalendarDate sd = new CalendarDate((Date) defectdialog.spnDateFound.getModel().getValue());
        
        long esttime = Util.getMillisFromMinutes(defectdialog.txtEstFixTime.getText());
        
        String inj = defectdialog.cmbInjection.getSelectedItem().toString();
    	Phase injphase = null;
    	for (Phase p : Phase.values()) {
			if (inj.equals(p.toString())) {
				injphase = p;
			}
		}
    	
    	CalendarDate ed;
        long acttime;
        String rem;
        Phase remphase = null;
        boolean iscompleted;
        if(defectdialog.chkDateFixed.isSelected()) {
        	ed = new CalendarDate((Date) defectdialog.spnDateFixed.getModel().getValue());
        	acttime = Util.getMillisFromMinutes(defectdialog.txtActFixTime.getText());
        	rem = defectdialog.cmbRemove.getSelectedItem().toString();
        	for (Phase p : Phase.values()) {
    			if (rem.equals(p.toString())) {
    				remphase = p;
    			}
    		}
        	iscompleted = true;
        }
        else {
 			ed = null;
        	acttime = 0;
        	iscompleted = false;
        }
        
        String fixref;
        if(defectdialog.chkFixReference.isSelected())
        	fixref = defectdialog.txtFixReference.getText();
        else
        	fixref = null;
        	
        Defect newDefect = CurrentProject.getDefectList().createDefect(sd, defectdialog.cmbType.getSelectedItem().toString(), 
        		injphase, esttime, acttime, ed, remphase, fixref, 
        		defectdialog.txtaDescription.getText(), iscompleted);
        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        defectTable.tableChanged();
        
       
        notifyDefectListListeners();
	}
	
	public static void addDefectListListener(DefectListListener listener) {
		  defectListListeners.add(listener);
	}
	
	static void notifyDefectListListeners() {
		  for (DefectListListener listener : defectListListeners) {
			  listener.defectListModified();
		  }
	}

	protected void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
		Context.put(
				"SHOW_ACTIVE_DEFECTS_ONLY",
				new Boolean(ppShowActiveOnlyChB.isSelected()));
			defectTable.tableChanged();		
	}
	
	protected void editDefectB_actionPerformed(ActionEvent e) {
		Defect d =
	            CurrentProject.getDefectList().getDefect(
	                defectTable.getModel().getValueAt(defectTable.getSelectedRow(), DefectTable.DEFECT_ID).toString());
	        DefectDialog defectdialog = new DefectDialog(App.getFrame(), Local.getString("Edit Defect: ") + d.getDefectId());
	        Dimension frmSize = App.getFrame().getSize();
	        Point loc = App.getFrame().getLocation();
	        defectdialog.setLocation((frmSize.width - defectdialog.getSize().width) / 2 + loc.x, (frmSize.height - defectdialog.getSize().height) / 2 + loc.y);
	        defectdialog.txtaDescription.setText(d.getDescription());
	        defectdialog.spnDateFound.getModel().setValue(d.getDateFound().getDate());
	        defectdialog.txtEstFixTime.setText(Long.toString(Util.getMinsFromMillis(d.getApproximateFixTimeInMillis())));
	        defectdialog.cmbInjection.setSelectedItem(d.getInjection().toString());
	        defectdialog.cmbType.setSelectedItem(d.getType());
	        
	    
		//Conditional for enabling defect completion properties
		if(d.getIsCompleted() == true) {
			defectdialog.chkDateFixed.setSelected(true);
			defectdialog.lblDateFixed.setEnabled(true);
			defectdialog.btnSetDateFixed.setEnabled(true);
			defectdialog.spnDateFixed.setEnabled(true);
	        defectdialog.spnDateFixed.getModel().setValue(d.getDateRemoved().getDate());
			defectdialog.lblActFixTime.setEnabled(true);
			defectdialog.txtActFixTime.setEnabled(true);
	        defectdialog.txtActFixTime.setText(Long.toString(Util.getMinsFromMillis(d.getFixTimeInMillis())));
			defectdialog.lblRemove.setEnabled(true);
			defectdialog.cmbRemove.setEnabled(true);
			defectdialog.cmbRemove.setSelectedItem(d.getRemove().toString());
		}
		
		//Conditional for enabling fix reference properties
		if(!d.getFixReference().equals("")) {
			defectdialog.chkFixReference.setSelected(true);
			defectdialog.lblFixReference.setEnabled(true);
			defectdialog.txtFixReference.setEnabled(true);
	        defectdialog.txtFixReference.setText(d.getFixReference());
		}
		
		defectdialog.setVisible(true);
	        
		//Conditional when user closed application
        if (defectdialog.CANCELLED)
            return;
        
        CalendarDate sd = new CalendarDate((Date) defectdialog.spnDateFound.getModel().getValue());
        long esttime = Util.getMillisFromMinutes(defectdialog.txtEstFixTime.getText());
        
        String inj = defectdialog.cmbInjection.getSelectedItem().toString();
    	Phase injphase = null;
    	for (Phase p : Phase.values()) {
			if (inj.equals(p.toString())) {
				injphase = p;
			}
		}
        
        CalendarDate ed;
        long acttime;
        String rem = defectdialog.cmbRemove.getSelectedItem().toString();
        Phase remphase = null;
        boolean iscompleted;
        if(defectdialog.chkDateFixed.isSelected()) {
 			ed = new CalendarDate((Date) defectdialog.spnDateFixed.getModel().getValue());
 			for (Phase p : Phase.values()) {
 				if (rem.equals(p.toString())) {
 					remphase = p;
 				}
 			}
        	acttime = Util.getMillisFromMinutes(defectdialog.txtActFixTime.getText());
        	iscompleted = true;
        }
        else {
 			ed = null;
        	acttime = 0;
        	iscompleted = false;
        }
        
        String fixref;
        if(defectdialog.chkFixReference.isSelected())
        	fixref = defectdialog.txtFixReference.getText();
        else
        	fixref = null;
        	
        
        
        d.editInjection(injphase);
        d.editDateFound(sd);
        d.editType(defectdialog.cmbType.getSelectedItem().toString());
        d.editApproximateFixTimeInMillis(esttime);
        d.editFixTimeInMillis(acttime);
        d.editDateRemoved(ed);
        d.editRemove(remphase);
        d.editFixReference(fixref);
        d.editDescription(defectdialog.txtaDescription.getText());
        d.editCompleted(iscompleted);
        
        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        defectTable.tableChanged();
        
        notifyDefectListListeners();
	}
	
	protected void ppCompleteDefect_actionPerformed(ActionEvent e) {
		Defect d =
	            CurrentProject.getDefectList().getDefect(
	                defectTable.getModel().getValueAt(defectTable.getSelectedRow(), DefectTable.DEFECT_ID).toString());
		d.editCompleted(true);
		d.editDateRemoved(CalendarDate.today());
		
		CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        defectTable.tableChanged();
        
        notifyDefectListListeners();
	}
	
	protected void removeDefectB_actionPerformed(ActionEvent e) {
		
		String msg;
        String thisDefectId = defectTable.getModel().getValueAt(defectTable.getSelectedRow(), DefectTable.DEFECT_ID).toString();
        
        if (defectTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove")+" "+defectTable.getSelectedRows().length +" "+Local.getString("defects")+"?"
             + "\n"+Local.getString("Are you sure?");
        else {
        	Defect d = CurrentProject.getDefectList().getDefect(thisDefectId);
        	msg = Local.getString("Remove defect")+"\n'" + d.getDescription() + "'\n"+Local.getString("Are you sure?");
        }
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove defect"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        Vector toremove = new Vector();
        for (int i = 0; i < defectTable.getSelectedRows().length; i++) {
            Defect d =
            CurrentProject.getDefectList().getDefect(
                defectTable.getModel().getValueAt(defectTable.getSelectedRows()[i], DefectTable.DEFECT_ID).toString());
            if (d != null)
                toremove.add(d);
        }
        for (int i = 0; i < toremove.size(); i++) {
            CurrentProject.getDefectList().removeDefect((Defect)toremove.get(i));
        }
        defectTable.tableChanged();
        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        
        notifyDefectListListeners();
		
	}
	
	/*
	protected void calcDefect_actionPerformed(ActionEvent e) {
		
	}
	*/
	
	class PopupListener extends MouseAdapter {

	    public void mouseClicked(MouseEvent e) {
			if ((e.getClickCount() == 2) && (defectTable.getSelectedRow() > -1)){
				editDefectB_actionPerformed(null);
			}
	    }

	            public void mousePressed(MouseEvent e) {
	                maybeShowPopup(e);
	            }

	            public void mouseReleased(MouseEvent e) {
	                maybeShowPopup(e);
	            }

	            private void maybeShowPopup(MouseEvent e) {
	                if (e.isPopupTrigger()) {
	                    defectPPMenu.show(e.getComponent(), e.getX(), e.getY());
	                }
	            }

	}
	
	void ppEditDefect_actionPerformed(ActionEvent e) {
	    editDefectB_actionPerformed(e);
	  }
	  void ppRemoveDefect_actionPerformed(ActionEvent e) {
	    removeDefectB_actionPerformed(e);
	  }
	  void ppNewDefect_actionPerformed(ActionEvent e) {
	    newDefectB_actionPerformed(e);
	  }
	
	  /*
	  void ppCalcDefect_actionPerformed(ActionEvent e) {
	      calcDefect_actionPerformed(e);
	  }
	  */
}