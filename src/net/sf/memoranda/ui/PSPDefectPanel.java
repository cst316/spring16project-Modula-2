package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListListener;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class PSPDefectPanel extends JPanel {
	
	private WorkPanel parentPanel = null;
	
    BorderLayout borderLayout1 = new BorderLayout();

	JToolBar defectsToolBar = new JToolBar();
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
	JMenuItem ppAddSubDefect = new JMenuItem();
	JMenuItem ppCalcDefect = new JMenuItem();
	
	private static Vector<DefectListListener> defectListListeners = new Vector<DefectListListener>();
	
	public PSPDefectPanel(WorkPanel _parentPanel) {
		setLayout(null);
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    } 
    
    void jbInit() throws Exception {
    	
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
        newDefectB.setBorderPainted(false);
		
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
        
        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(
			Local.getString("Show Active only"));
		ppShowActiveOnlyChB
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleShowActiveOnly_actionPerformed(e);
			}
		});
		
		this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        ppEditDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppEditDefect.setText(Local.getString("Edit defect")+"...");
	    ppEditDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppEditDefect_actionPerformed(e);
	            }
	        });
	    
	    defectTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (defectTable.getRowCount() > 0)&&(defectTable.getSelectedRow() > -1);
                editDefectB.setEnabled(enbl);ppEditDefect.setEnabled(enbl);
                removeDefectB.setEnabled(enbl);ppRemoveDefect.setEnabled(enbl);
				
				ppCompleteDefect.setEnabled(enbl);
				completeDefectB.setEnabled(enbl);
				ppAddSubDefect.setEnabled(enbl);
				ppCalcDefect.setEnabled(enbl);
				
                if (enbl) {   
    				String thisDefectId = defectTable.getModel().getValueAt(defectTable.getSelectedRow(), DefectTable.DEFECT_ID).toString();
                }
            }
        });
	    
	    ppEditDefect.setEnabled(false);
	    defectPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
	    ppRemoveDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppRemoveDefect.setText(Local.getString("Remove defect"));
	    ppRemoveDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppRemoveDefect_actionPerformed(e);
	            }
	        });
	    ppRemoveDefect.setEnabled(false);
	    ppNewDefect.setFont(new java.awt.Font("Dialog", 1, 11));
	    ppNewDefect.setText(Local.getString("New defect")+"...");
	    ppNewDefect.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ppNewDefect_actionPerformed(e);
	            }
	        });
	    
	    ppCompleteDefect.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCompleteDefect.setText(Local.getString("Complete defect"));
		ppCompleteDefect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ppCompleteDefect_actionPerformed(e);
				}
			});
		ppCompleteDefect.setEnabled(false);

		ppCalcDefect.setFont(new java.awt.Font("Dialog", 1, 11));
		ppCalcDefect.setText(Local.getString("Calculate defect data"));
		ppCalcDefect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ppCalcDefect_actionPerformed(e);
				}
			});
		ppCalcDefect.setEnabled(false);
        
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.getViewport().add(defectTable, null);
		this.add(scrollPane, BorderLayout.CENTER);
        defectsToolBar.add(newDefectB, null);
        defectsToolBar.add(removeDefectB, null);
        defectsToolBar.addSeparator(new Dimension(8, 24));
        defectsToolBar.add(editDefectB, null);
        defectsToolBar.add(completeDefectB, null);

        this.add(defectsToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        defectTable.addMouseListener(ppListener);
		scrollPane.setBounds(0, 41, 625, 342);
		add(scrollPane);
		
    }

	protected void newDefectB_actionPerformed(ActionEvent arg0) {
		DefectDialog defectdialog = new DefectDialog(App.getFrame(), Local.getString("New Defect"));
        defectdialog.spnDateFound.getModel().setValue(CurrentDate.get().getDate());
        defectdialog.setLocationRelativeTo(this);
        defectdialog.pack();
        defectdialog.setVisible(true);
        
        if (defectdialog.CANCELLED)
            return;
        
        CalendarDate sd = new CalendarDate((Date) defectdialog.spnDateFound.getModel().getValue());
        CalendarDate ed;
 		
        if(defectdialog.chkDateFixed.isSelected())
 			ed = new CalendarDate((Date) defectdialog.spnDateFixed.getModel().getValue());
 		else
 			ed = null;
        
        long esttime = Util.getMillisFromMinutes(defectdialog.txtEstFixTime.getText());
        
        long acttime;
        boolean iscompleted;
        if(defectdialog.chkDateFixed.isSelected()) {
        	acttime = Util.getMillisFromMinutes(defectdialog.txtActFixTime.getText());
        	iscompleted = true;
        }
        else {
        	acttime = 0;
        	iscompleted = false;
        }
        
        String fixref;
        if(defectdialog.chkFixReference.isSelected())
        	fixref = defectdialog.txtFixReference.getText();
        else
        	fixref = null;
        	
		//SO FAR SO GOOD
        Defect newDefect = CurrentProject.getDefectList().createDefect(sd, Integer.parseInt(defectdialog.tfNumber.getText()), 
        		defectdialog.cmbType.getSelectedItem().toString(), defectdialog.txtInjection.getText(), 
        		esttime, acttime, ed, defectdialog.txtRemove.getText(), fixref, defectdialog.txtaDescription.getText(), iscompleted);
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
	
	protected void ppCalcDefect_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void ppRemoveDefect_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void ppEditDefect_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void ppCompleteDefect_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void removeDefectB_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void editDefectB_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void ppNewDefect_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
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
}