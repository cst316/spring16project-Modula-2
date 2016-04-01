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

/*$Id: TaskDialog.java,v 1.25 2005/12/01 08:12:26 alexeya Exp $*/
public class PSPReferenceDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String columnNames[] = { "Type Number", "Type Name", "Description" };
	String dataValues[][] =
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
	 JTable ReferenceTable = new JTable(dataValues, columnNames) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
    public PSPReferenceDialog(Frame frame, String title) {
        super(frame, title, true);
        setResizable(false);
        try {
            jbInit();            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    
    
    
    void jbInit() throws Exception {
    	this.pack();
    	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ReferenceTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ReferenceTable.setFont(new Font("Dialog", Font.PLAIN, 14));
		ReferenceTable.setRowSelectionAllowed(false);
		ReferenceTable.setFocusable(false);
		ReferenceTable.setBounds(51, 130, 567, 176);
		contentPane.add(ReferenceTable);
		
		JLabel lblNewLabel = new JLabel("PSP Defect Quick Reference");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel.setBounds(185, 76, 299, 29);
		contentPane.add(lblNewLabel);
		
		int[] columnsWidth = {
	            55, 100, 500
	    };
	
		
		int i = 0;
		for (int width : columnsWidth) {
	        TableColumn column = ReferenceTable.getColumnModel().getColumn(i++);
	        column.setMinWidth(width);
	        column.setMaxWidth(width);
	        column.setPreferredWidth(width);
	    }  
    }
}