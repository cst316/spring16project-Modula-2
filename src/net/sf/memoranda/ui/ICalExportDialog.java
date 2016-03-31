package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicFileChooserUI;

import net.sf.memoranda.EventsManager;
import net.sf.memoranda.util.Local;

import javax.swing.*;

import java.awt.*;

public class ICalExportDialog extends JDialog {	
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  public JFileChooser fileChooser = null;
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();

  public boolean CANCELLED = true;
  Border border1;
  Border border2;
  JPanel jPanel4 = new JPanel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  GridLayout gridLayout1 = new GridLayout();

  public ICalExportDialog(Frame frame, String title, JFileChooser chooser) {
    super(frame, title, true);
    try {
      fileChooser = chooser;
      jbInit();
      pack();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
  }


  void jbInit() throws Exception {
    this.setResizable(false);

    border1 = BorderFactory.createEmptyBorder(10,10,0,10);
    border2 = BorderFactory.createEmptyBorder(5,10,5,5);
    fileChooser.setBorder(null);
    fileChooser.setControlButtonsAreShown(false);
    jPanel2.setLayout(borderLayout3);
    okB.setMaximumSize(new Dimension(100, 26));
    okB.setPreferredSize(new Dimension(100, 26));
    okB.setText(Local.getString("Save"));
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
		if (fileChooser.getUI() instanceof BasicFileChooserUI){
			BasicFileChooserUI ui = (BasicFileChooserUI)fileChooser.getUI();
			ui.getApproveSelectionAction().actionPerformed(e);
		}
      }
    });
    this.getRootPane().setDefaultButton(okB);
    cancelB.setMaximumSize(new Dimension(100, 26));
    cancelB.setPreferredSize(new Dimension(100, 26));
    cancelB.setText(Local.getString("Cancel"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    jPanel3.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    borderLayout3.setHgap(5);
    borderLayout3.setVgap(5);
    jPanel2.setBorder(border1);
    jPanel3.setBorder(border2);
    jPanel1.setLayout(borderLayout2);
    jPanel4.setLayout(gridLayout1);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(3);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jPanel4,  BorderLayout.SOUTH);
    jPanel4.add(jPanel1, null);
    jPanel2.add(fileChooser, BorderLayout.NORTH);
    this.getContentPane().add(jPanel3,  BorderLayout.SOUTH);
    jPanel3.add(okB, null);
    jPanel3.add(cancelB, null);
  }

   void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }
}