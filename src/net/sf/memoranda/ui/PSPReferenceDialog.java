package net.sf.memoranda.ui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import net.sf.memoranda.util.ColorScheme;

/*$Id: TaskDialog.java,v 1.25 2005/12/01 08:12:26 alexeya Exp $*/
public class PSPReferenceDialog extends JDialog {
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Defect Reference Table
	 */
	
	
	String columnDefectNames[] = { "Type Number", "Type Name", "Description" };
	String dataDefectValues[][] =
		{	
			{ "Number", "Type Name", "Description" },
			{ "10", "Documentation", "Comments, messages." },
			{ "20", "Syntax", "Spelling, punctuation, typos, istruction formats." },
			{ "30", "Build, Package", "Change management, library, version control." },
			{ "40", "Assignment", "Declaration, duplicate names, scope, limits." },
			{ "50", "Interface", "Procedure calls and references, I/O, user formats." },
			{ "60", "Checking", "Error messages, inadquate checks." },
			{ "70", "Data", "Structure, content." },
			{ "80", "Function", "Logic, pointers, loops, recursion, computation, function defects." },
			{ "90", "System", "Configuration, timing, memory." },
			{ "100", "Environment", "Design, compile, test, or other support system problems." },
			
		};
	 JTable ReferenceDefectTable = new JTable(dataDefectValues, columnDefectNames) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
	    
    /**
	 * Defect Phase Table
     */   
	    String columnPhaseNames[] = { "Phase",  "Description" };
		String dataPhaseValues[][] =
			{	
				{ "Phase", "Description"},
				{ "Planning", "Obtain a description of the program functions, estimate LOC, Calculate development time, record time." },
				{ "Design", "Design the program, record the design in the specidfied format, record the design time." },
				{ "Code", "Implement the design, Use a standard format for entering the code, record coding time." },
				{ "Code Review", "Completely review the source code, fix and review defects found."},
				{ "Compile", "Compile the program, Fix and record defects found, record compile time."},
				{ "Test", "Test the program, Fix and record all defects found, record testing time."},
				{ "Postmortem", "Complete the project summary, Review the defect data and update the code review checklist. Record Time."},
				
			};
		 JTable ReferencePhaseTable = new JTable(dataPhaseValues, columnPhaseNames) {
		        private static final long serialVersionUID = 1L;

		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		    };
		 private JLabel PSPQuickPhaseLabel;
		 private JLabel PSPQuickDefectLabel;
		    
	    
	    
    public PSPReferenceDialog(Frame frame, String title) {
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
    	
    	this.setPreferredSize(new Dimension(1000,800));
    	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBackground(ColorScheme.getColor("taskbar_primary"));
		contentPane.setBackground(ColorScheme.getColor("taskbar_primary"));
		setBackground(ColorScheme.getColor("taskbar_primary"));
		contentPane.setBackground(ColorScheme.getColor("taskbar_primary"));
		
		
		JLabel PSPQuickLabel;
		PSPQuickDefectLabel = new JLabel("PSP Defect Quick Reference");
		PSPQuickDefectLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		PSPQuickDefectLabel.setBounds(313, 90, 299, 29);
		PSPQuickDefectLabel.setForeground(ColorScheme.getColor("taskbar_text"));
		contentPane.add(PSPQuickDefectLabel);
		
		
	
		ReferencePhaseTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ReferencePhaseTable.setFont(new Font("Dialog", Font.PLAIN, 14));
		ReferencePhaseTable.setRowSelectionAllowed(false);
		ReferencePhaseTable.setFocusable(false);
		ReferencePhaseTable.setBounds(93, 472, 777, 131);
		ReferencePhaseTable.setBackground(ColorScheme.getColor("button_primary"));
		contentPane.add(ReferencePhaseTable);
		
		JLabel PSPQuickLabel_1;
		PSPQuickPhaseLabel = new JLabel("PSP Phase Quick Reference");
		PSPQuickPhaseLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		PSPQuickPhaseLabel.setBounds(313, 402, 299, 29);
		PSPQuickPhaseLabel.setForeground(ColorScheme.getColor("taskbar_text"));
		contentPane.add(PSPQuickPhaseLabel);
		
		ReferenceDefectTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ReferenceDefectTable.setFont(new Font("Dialog", Font.PLAIN, 14));
		ReferenceDefectTable.setRowSelectionAllowed(false);
		ReferenceDefectTable.setFocusable(false);
		ReferenceDefectTable.setBounds(200, 132, 567, 176);
		ReferenceDefectTable.setBackground(ColorScheme.getColor("button_primary"));
		
		contentPane.add(ReferenceDefectTable);
		
		int[] columnsWidth = {
	            55, 100, 500
	    };
		int[] columnsWidth2 = {
				100, 800
		};
	
		
		int i = 0;
		for (int width : columnsWidth) {
	        TableColumn column = ReferenceDefectTable.getColumnModel().getColumn(i++);
	        column.setMinWidth(width);
	        column.setMaxWidth(width);
	        column.setPreferredWidth(width);
	    }  
		
		int j = 0;
		for (int width : columnsWidth2) {
	        TableColumn column = ReferencePhaseTable.getColumnModel().getColumn(j ++);
	        column.setMinWidth(width);
	        column.setMaxWidth(width);
	        column.setPreferredWidth(width);
	    }  
    }
}