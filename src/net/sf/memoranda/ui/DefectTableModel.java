package net.sf.memoranda.ui;

import javax.swing.event.EventListenerList;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.Project;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.ui.treetable.AbstractTreeTableModel;
import net.sf.memoranda.ui.treetable.TreeTableModel;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

public class DefectTableModel extends AbstractTreeTableModel implements TreeTableModel {

	String[] columnNames = {Local.getString("Description"), Local.getString("Defect ID"),
            Local.getString("Found date"), Local.getString("Fixed date"),
            Local.getString("Injection"), Local.getString("Type"), 
            Local.getString("Approximate Fix Time"), Local.getString("Fix Time"),
            Local.getString("Fix Reference"), Local.getString("Status"),
            Local.getString("Removal") };

    //protected EventListenerList listenerList = new EventListenerList();

    private boolean activeOnly = check_activeOnly();
    
    public DefectTableModel() {
    	super(CurrentProject.get());
    }

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof Project)
            return null;
        Defect d = (Defect) node;
        switch (column) {
        case 0:
            return d.getDescription();
        case 1:
            return d;
        case 2:
            return d.getDateFound().getDate();
        case 3:
            if (d.getDateRemoved() == null)
                return null;
            else
                return d.getDateRemoved().getDate();       
        case 4:
            return d.getInjection();
        case 5:
        	return d.getType();
        case 6:    
        	return Long.toString(Util.getMinsFromMillis(d.getApproximateFixTimeInMillis()));
        case 7:
        	if (d.getApproximateFixTimeInMillis() == 0)
        		return null;
        	else
        		return Long.toString(Util.getMinsFromMillis(d.getFixTimeInMillis()));
        case 8:
        	if (d.getFixReference() == null)
        		return null;
        	else
        		return d.getFixReference();
        case 9:
            return getStatusString(d.getCompleted(CurrentDate.get()));
        case 10:            
        	if (d.getRemove() == null)
        		return null;
        	else
        		return d.getRemove();
        case DefectTable.DEFECT_ID:
            return d.getDefectId();
        case DefectTable.DEFECT:
            return d;
        }
        return "";
	}

	String getStatusString(int status) {
        switch (status) {
        case Defect.ACTIVE:
            return Local.getString("Active");
        case Defect.COMPLETED:
            return Local.getString("Completed");
        }
        return "";
    }
	
	@Override
	public Object getChild(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof Project) {
			if( activeOnly() ){
				return CurrentProject.getDefectList().getActiveDefects(null, CurrentDate.get()).size();
			}
			else {
				return CurrentProject.getDefectList().getAllDefects().size();
	        }
		}
        Defect d = (Defect) parent;
        if( activeOnly() ) {
        	return CurrentProject.getDefectList().getActiveDefects(d.getDefectId(), CurrentDate.get()).size();
        }
        else {
        	return 0;
        }
    }
	
	public Class getColumnClass(int column) {
        try {
            switch (column) {
            case 1:
                return TreeTableModel.class;
            case 0:
                return DefectTable.class;
            case 4:
            case 5:
                return Class.forName("java.lang.String");
            case 2:
            case 3:
                return Class.forName("java.util.Date");
            case 6:
                return Class.forName("java.lang.Integer");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void fireTreeStructureChanged(){	    
	    fireTreeStructureChanged( this,
	    			new Object[]{getRoot()},
				new int[0],
				new Object[0]
				);
    }
    
    
    /**
     * Update cached data
     */
    public void fireUpdateCache(){
		activeOnly = check_activeOnly();
    }

    public static boolean check_activeOnly(){
		Object o = Context.get("SHOW_ACTIVE_DEFECTS_ONLY");
		if(o == null) return false;
		return o.toString().equals("true");
	}

    public boolean activeOnly(){
		return activeOnly;
    }
    
    public boolean isCellEditable(Object node, int column) {
		if(column == 6) return true; 
        return super.isCellEditable(node, column); 
    }
        
}
