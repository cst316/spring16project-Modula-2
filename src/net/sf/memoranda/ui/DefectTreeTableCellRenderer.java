package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import net.sf.memoranda.Defect;
import net.sf.memoranda.Project;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CurrentDate;

public class DefectTreeTableCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer, TableCellRenderer {
	
	//KEEP ICONS FOR NOW
	static ImageIcon DEFECT_ACTIVE_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_active.png"));
	
	static ImageIcon DEFECT_COMPLETED_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_completed.png"));
	
	// reusable cellrenderers
    JLabel label = new JLabel();
    JPanel empty_panel = new JPanel();
    // get Defect objects via table (maybe not most elegant solution)
    DefectTable table;
    
    //SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy");
    //  use localized date format, modified from default locale's short format if possible
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);//createModifiedShortFormat();

    public DefectTreeTableCellRenderer(DefectTable table) {
        super();
        this.table = table;
        //progressLabel = new TaskProgressLabel(table);
        label.setOpaque(true);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // if root then just return some component
        // it is not shown anyway
        super.getTreeCellRendererComponent(
                tree, value, selected,
                expanded, leaf, row,
                hasFocus);
        if (value instanceof Project)
            return empty_panel;
        if (!(value instanceof Defect))
            return empty_panel;
        Defect d = (Defect) value; 
        setText(d.getDefectId());
        setToolTipText(d.getDescription());
        setIcon(getStatusIcon(d));
        applyFont(d, this);
        return this;
    }

    public Component getTableCellRendererComponent(JTable ignore, Object value, boolean selected,
            boolean hasFocus, int row, int column) {        
        Defect d = (Defect) table.getValueAt(row, 1);
        if (column == 1) {
            // this never happens because
            // column 1 contains TreeTableModel
            // and default renderer for it
            // is JTree directly            
            return table.getTree();
        }
        // default values
        // label.setOpaque(true);
        label.setForeground(Color.BLACK);
        label.setIcon(null);
        applyFont(d, label);
        applySelectionStyle(selected, label);
        applyFocus(hasFocus, label);
        if (value == null) {
            label.setText("");
            return label;
        }
        
        if (column == 6) {
            //return getProgressCellRenderer(d, selected, hasFocus, column);
        }
        
        if ((column == 2) || (column == 3)) {
            label.setText(dateFormat.format((Date) value));
            return label;
        }
        if (column == 5) {
            label.setText(value.toString());
            label.setForeground(getColorForDefectStatus(d, false));
            return label;
        }
        label.setText(value.toString());
        return label;
    }

    /**
     * Component used to render tree cells in treetable
     */
    private Component getDefectTreeCellRenderer(Defect d, boolean selected, boolean hasFocus) {
        JLabel tree_label = new JLabel();       
        tree_label.setText(d.getDefectId());
        tree_label.setIcon(getStatusIcon(d));
        applyFont(d, tree_label);
        return tree_label;        
    }
    
    /*
    private Component getProgressCellRenderer(Defect d, boolean selected, boolean hasFocus, int column) {
        progressLabel.setDefect(d);
        progressLabel.setColumn(column);
        applyFocus(hasFocus, progressLabel);
        return progressLabel;
    }

    private Component getPriorityIconCellRenderer(Defect d, boolean selected, boolean hasFocus) {
        applyFocus(false, label); // disable focus borders
        label.setIcon(getPriorityIcon(d));
        label.setToolTipText(d.getDescription());
        return label;
    }
    */
    
    // some convenience methods
    private void applySelectionStyle(boolean selected, JComponent c) {
        if (selected)
            c.setBackground(table.getSelectionBackground());
        else
            c.setBackground(table.getBackground());
    }

    private void applyFocus(boolean hasFocus, JComponent c) {
        if (hasFocus) {
            c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, table.getSelectionBackground()
                    .darker()));
        } else {
            if (c.getBorder() != null) {
                c.setBorder(null);
            }
        }
    }

    private void applyFont(Defect d, JComponent c) {
        if (d.getCompleted(CurrentDate.get()) == Defect.ACTIVE)
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        else
            c.setFont(c.getFont().deriveFont(Font.PLAIN));
    }

    /**
     * Color representing defect status, "light" color is useful for backgrounds
     * and other for text
     */
    public static Color getColorForDefectStatus(Defect d, boolean light) {
        if (light) {
            switch (d.getCompleted(CurrentDate.get())) {
            case Defect.ACTIVE:
                return new Color(192, 255, 192);
            case Defect.COMPLETED:
                return new Color(230, 255, 230);
            }
        } else {
            switch (d.getCompleted(CurrentDate.get())) {
            case Defect.ACTIVE:
                return new Color(0, 180, 0);
            case Defect.COMPLETED:
                return new Color(0, 120, 0);
            }
        }
        System.err.println("Problem finding color for defect status");
        return null;
    }

    public static ImageIcon getStatusIcon(Defect d) {
        switch (d.getCompleted(CurrentDate.get())) {
        case Defect.ACTIVE:
            return DEFECT_ACTIVE_ICON;
        case Defect.COMPLETED:
            return DEFECT_COMPLETED_ICON;
        }
        System.err.println("Problem finding status icon");
        return null;
    }

}
