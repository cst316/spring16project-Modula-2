package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.memoranda.util.ColorScheme;

public class SummaryPanelCellRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) { 
	    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
	    
	    if(row <= 0) {
	    	c.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    	c.setForeground(ColorScheme.getColor("frame_text"));
	    	c.setBackground(ColorScheme.getColor("button_primary"));
	    }
	    else {
	    	c.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    	c.setForeground(ColorScheme.getColor("frame_text"));
	    	c.setBackground(ColorScheme.getColor("frame_secondary")); 
	    }
	    
	    return c; 
	} 
}
