package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.memoranda.Defect;

public class DefectTableSorter extends DefectTableModel {

	// -1 == no sorting
		int sorting_column = -1;
		
		// sort opposite direction
		boolean opposite = false;
		
		Comparator comparator = new Comparator(){
			public int compare(Object o1, Object o2){
				if(sorting_column == -1) return 0;
				if( (o1 instanceof Defect) == false) return 0;
				if( (o2 instanceof Defect) == false ) return 0;
				
				
				Defect defect1 = (Defect) o1;
				Defect defect2 = (Defect) o2;
				
				// based on DefectTableModel.columnNames
				switch(sorting_column){
					case 1: return defect1.getDefectId().compareTo(defect2.getDefectId());
					case 2: return defect1.getDateFound().getDate().compareTo(defect2.getDateFound().getDate());
					case 3: return defect1.getDateRemoved().getDate().compareTo(defect2.getDateRemoved().getDate());
					/*
					 case 4: if(defect1.getCompleted())
								return 1;
							 if(defect2.getCompleted())
								return 2;
					*/
				}
				
				return 0;
			}
		};
		
		public DefectTableSorter( DefectTable table ){
			JTableHeader tableHeader = table.getTableHeader();
			tableHeader.addMouseListener( new MouseHandler() );
			tableHeader.setDefaultRenderer( new SortableHeaderRenderer());
		}
		
		
		/*
		public Object getChild(Object parent, int index) {
			Collection c = null;
			
			if (parent instanceof Project){
				if( activeOnly() ) c = CurrentProject.getDefectList().getActiveDefects(null, CurrentDate.get());
				else c = CurrentProject.getTaskList().getTopLevelTasks();
			}
			else{
				Defect t = (Defect) parent;
				if(activeOnly()) c = CurrentProject.getTaskList().getActiveSubTasks(t.getID(), CurrentDate.get());
				else c = t.getSubTasks();
			}
			
			Object array[] = c.toArray();
			Arrays.sort(array, comparator);
			if(opposite){
				return array[ array.length - index - 1];
			}
			return array[index];
		}
		*/

		
	    
	    private class MouseHandler extends MouseAdapter {
	        public void mouseClicked(MouseEvent e) {
	            JTableHeader h = (JTableHeader) e.getSource();
	            TableColumnModel columnModel = h.getColumnModel();
	            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
	            int column = columnModel.getColumn(viewColumn).getModelIndex();
	            if (column != -1) {
			sorting_column = column;
			
			if(e.isControlDown()) sorting_column = -1;
			else opposite = !opposite;
			
			DefectTable treetable = ( (DefectTable) h.getTable());
						
			treetable.tableChanged();
			h.resizeAndRepaint();
	            }
	        }
	    }
	    
		/**
		* Render sorting header differently
		*/
		private class SortableHeaderRenderer implements TableCellRenderer {
		    
		    
		    
			public Component getTableCellRendererComponent(JTable table, 
								       Object value,
								       boolean isSelected, 
								       boolean hasFocus,
								       int row, 
								       int column) {
				JComponent c = new JLabel(value.toString());
				if(column == sorting_column){
					c.setFont(c.getFont().deriveFont(Font.BOLD));
				}
				else c.setFont(c.getFont().deriveFont(Font.PLAIN));
				return c;
			}
		}
}
