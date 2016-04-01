package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.ui.TaskTable.ExpansionHandler;
import net.sf.memoranda.ui.TaskTable.ListToTreeSelectionModelWrapper;
import net.sf.memoranda.ui.TaskTable.TreeTableCellEditor;
import net.sf.memoranda.ui.TaskTable.TreeTableCellRenderer;
import net.sf.memoranda.ui.TaskTable.ListToTreeSelectionModelWrapper.ListSelectionHandler;
import net.sf.memoranda.ui.treetable.AbstractCellEditor;
import net.sf.memoranda.ui.treetable.TreeTableModel;
import net.sf.memoranda.ui.treetable.TreeTableModelAdapter;
import net.sf.memoranda.util.Local;

public class DefectTable extends JTable {

	public static final int DEFECT_ID = 100;

    public static final int DEFECT = 101;

    protected TreeTableCellRenderer tree;

    protected DefectTableModel model;
    
    protected TreeTableModelAdapter modelAdapter;
    
    protected DefectTreeTableCellRenderer renderer;
	
	protected ExpansionHandler expansion; 
    
    public DefectTable() {
        super();
        initTable();
        // Force the JTable and JTree to share their row selection models.
        ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
        tree.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                tableChanged();
            }
        });
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl,
                    DefectList dl, ResourcesList rl) {
            }

            public void projectWasChanged() {
				tableChanged();
            }
        });
	
    }

    private void initTable() {
	
		model = new DefectTableSorter( this );
	
		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(model);
		
		// store tree expansion status and
		// restore after sorting/project change etc.
		expansion = new ExpansionHandler();
		tree.addTreeExpansionListener(expansion);
	
		// Install a tableModel representing the visible rows in the tree.
		modelAdapter = new TreeTableModelAdapter(model, tree);
		super.setModel(modelAdapter);
			
		// Install the tree editor renderer and editor.
		renderer = new DefectTreeTableCellRenderer(this);
		
		tree.setCellRenderer(renderer);
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultRenderer(Integer.class, renderer);
		setDefaultRenderer(DefectTable.class, renderer);
		setDefaultRenderer(String.class, renderer);
		setDefaultRenderer(java.util.Date.class, renderer);

		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
		
		//  grid.
		setShowGrid(false);

		// No intercell spacing
		setIntercellSpacing(new Dimension(0, 0));

		// And update the height of the trees row to match that of
		// the table.
		//if (tree.getRowHeight() < 1) {
			setRowHeight(18);
		//}
		initColumnWidths();
		
		// do not allow moving columns
		getTableHeader().setReorderingAllowed(false);
    }

    void initColumnWidths() {
        for (int i = 0; i < 7; i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(8);
            } 
            else if (i == 1) {
                column.setPreferredWidth(32767);
            }
	    else if( i == 6 ){
		    column.setPreferredWidth(100);
		    column.setMinWidth(100);
	    }
            else {
                column.setMinWidth(67); // 65);
                column.setPreferredWidth(67); //65);
            }
        }
    }
    
    public void tableChanged() {
		model.fireUpdateCache();
		model.fireTreeStructureChanged();
		expansion.expand(tree);
		updateUI();
    }
    
    /**
     * Overridden to message super and forward the method to the tree. Since the
     * tree is not actually in the component hieachy it will never receive this
     * unless we forward it in this manner.
     */
    public void updateUI() {
		super.updateUI();
			if (tree != null) { 
			tree.updateUI();
			}


        // Use the tree's default foreground and background colors in the
        // table.
        LookAndFeel.installColorsAndFont(this, "Tree.background",
                "Tree.foreground", "Tree.font");
    }

    /*
     * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to paint the
     * renderers and editors and overriding setBounds() below is not the right
     * thing to do for an editor. Returning -1 for the editing row in this case,
     * ensures the editor is never painted.
     */
    public int getEditingRow() {
        return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
                : editingRow;
    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    public void setRowHeight(int rowHeight) {
        super.setRowHeight(rowHeight);
        if (tree != null && tree.getRowHeight() != rowHeight) {
            tree.setRowHeight(getRowHeight());
        }
    }

    /**
     * Returns the tree that is being shared between the model.
     */
    public TreeTableCellRenderer getTree() {
        return tree;
    } 

    /**
     * A TreeCellRenderer that displays a JTree.
     */
	 public class TreeTableCellRenderer extends JTree implements // {{{
            TableCellRenderer {
        /** Last table/tree row asked to renderer. */
        protected int visibleRow;

        public TreeTableCellRenderer(TreeModel model) {
            super(model);
            this.setRootVisible(false);
            this.setShowsRootHandles(true);
			this.setCellRenderer(renderer);                       
        }

        /**
         * updateUI is overridden to set the colors of the Tree's renderer to
         * match that of the table.
         */
        public void updateUI() {
            super.updateUI();
	    
            // Make the tree's cell renderer use the table's cell selection
            // colors.
            TreeCellRenderer tcr = getCellRenderer();
            if (tcr instanceof DefaultTreeCellRenderer) {
                DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
 
				dtcr.setBorderSelectionColor(null);
                dtcr.setTextSelectionColor(UIManager
                        .getColor("Table.selectionForeground"));
                dtcr.setBackgroundSelectionColor(UIManager
                        .getColor("Table.selectionBackground"));
            }
        }

        /**
         * Sets the row height of the tree, and forwards the row height to the
         * table.
         */
        public void setRowHeight(int rowHeight) {
            if (rowHeight > 0) {
                super.setRowHeight(rowHeight);
                if (DefectTable.this != null
                        && DefectTable.this.getRowHeight() != rowHeight) {
                    DefectTable.this.setRowHeight(getRowHeight());
                }
            }
        }

        /**
         * This is overridden to set the height to match that of the JTable.
         */
        public void setBounds(int x, int y, int w, int h) {
            super.setBounds(x, 0, w, DefectTable.this.getHeight());
        }

        /**
         * Subclassed to translate the graphics such that the last visible row
         * will be drawn at 0,0.
         */
        public void paint(Graphics g) {
            g.translate(0, -visibleRow * getRowHeight());
            super.paint(g);
        }

        /**
         * TreeCellRenderer method. Overridden to update the visible row.
         */
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (isSelected)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            visibleRow = row;
            return this;
        }
	 }

    /**
     * TreeTableCellEditor implementation. Component returned is the JTree.
     */
	 public class TreeTableCellEditor extends AbstractCellEditor implements
            TableCellEditor {
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int r, int c) {
            return tree;
        }

        /**
         * Overridden to return false, and if the event is a mouse event it is
         * forwarded to the tree.
         * <p>
         * The behavior for this is debatable, and should really be offered as a
         * property. By returning false, all keyboard actions are implemented in
         * terms of the table. By returning true, the tree would get a chance to
         * do something with the keyboard events. For the most part this is ok.
         * But for certain keys, such as left/right, the tree will
         * expand/collapse where as the table focus should really move to a
         * different column. Page up/down should also be implemented in terms of
         * the table. By returning false this also has the added benefit that
         * clicking outside of the bounds of the tree node, but still in the
         * tree column will select the row, whereas if this returned true that
         * wouldn't be the case.
         * <p>
         * By returning false we are also enforcing the policy that the tree
         * will never be editable (at least by a key sequence).
         */
        public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
                    if (getColumnClass(counter) == TreeTableModel.class) {
                        MouseEvent me = (MouseEvent) e;
                        MouseEvent newME = new MouseEvent(tree, me.getID(), me
                                .getWhen(), me.getModifiers(), me.getX()
                                - getCellRect(0, counter, true).x, me.getY(),
                                me.getClickCount(), me.isPopupTrigger());
                        tree.dispatchEvent(newME);
                        break;
                    }
                }
            }
            return false;
        }
	} // }}}

    /**
     * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to
     * listen for changes in the ListSelectionModel it maintains. Once a change
     * in the ListSelectionModel happens, the paths are updated in the
     * DefaultTreeSelectionModel.
     */
	 public class ListToTreeSelectionModelWrapper extends // {{{
            DefaultTreeSelectionModel {
        /** Set to true when we are updating the ListSelectionModel. */
        protected boolean updatingListSelectionModel;

        public ListToTreeSelectionModelWrapper() {
            super();
            getListSelectionModel().addListSelectionListener(
                    createListSelectionListener());
        }

        /**
         * Returns the list selection model. ListToTreeSelectionModelWrapper
         * listens for changes to this model and updates the selected paths
         * accordingly.
         */
        public ListSelectionModel getListSelectionModel() {
            return listSelectionModel;
        }

        /**
         * This is overridden to set <code>updatingListSelectionModel</code>
         * and message super. This is the only place DefaultTreeSelectionModel
         * alters the ListSelectionModel.
         */
        public void resetRowSelection() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    super.resetRowSelection();
                } finally {
                    updatingListSelectionModel = false;
                }
            }
            // Notice how we don't message super if
            // updatingListSelectionModel is true. If
            // updatingListSelectionModel is true, it implies the
            // ListSelectionModel has already been updated and the
            // paths are the only thing that needs to be updated.
        }

        /**
         * Creates and returns an instance of ListSelectionHandler.
         */
        protected ListSelectionListener createListSelectionListener() {
            return new ListSelectionHandler();
        }

        /**
         * If <code>updatingListSelectionModel</code> is false, this will
         * reset the selected paths from the selected rows in the list selection
         * model.
         */
        protected void updateSelectedPathsFromSelectedRows() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    // This is way expensive, ListSelectionModel needs an
                    // enumerator for iterating.
                    int min = listSelectionModel.getMinSelectionIndex();
                    int max = listSelectionModel.getMaxSelectionIndex();

                    clearSelection();
                    if (min != -1 && max != -1) {
                        for (int counter = min; counter <= max; counter++) {
                            if (listSelectionModel.isSelectedIndex(counter)) {
                                TreePath selPath = tree.getPathForRow(counter);

                                if (selPath != null) {
                                    addSelectionPath(selPath);
                                }
                            }
                        }
                    }
                } finally {
                    updatingListSelectionModel = false;
                }
            }
        }

	/**
	 * Class responsible for calling updateSelectedPathsFromSelectedRows
	 * when the selection of the list changse.
	 */
	class ListSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) {
                updateSelectedPathsFromSelectedRows();
            }
        }
	} // }}}
	
	
	/**
	 * Stores expanded treepaths so that they
	 * can be restored after treeStructureChanged-method call
	 * which collapses everything
	 */
	 class ExpansionHandler implements TreeExpansionListener { // {{{
	
		private java.util.Set expanded = new java.util.HashSet();
		
		public void treeExpanded(TreeExpansionEvent e) {
			expanded.add(e.getPath());
		}
		
		public void treeCollapsed(TreeExpansionEvent e) {
			TreePath p = e.getPath();
			int index = p.getPathCount() - 1;
			Object collapsed = p.getLastPathComponent();

			Object[] components = expanded.toArray();
			for(int i=0; i<components.length; i++){
				TreePath epath = (TreePath) components[i];
				if( (epath.getPathCount() > index) && (epath.getPathComponent(index).equals(collapsed))){
					expanded.remove(epath);
				}
			}
		}
		
		/**
		 * Expands stored treepaths in JTree
		 * <p>
		 * If model has been changed (eg. project change) we
		 * still try to expand paths whick do not exist.
		 * We just assume that this is not causing problems,
		 * and as a side effect it preserved tree expansion status
		 * even after project has been changed to some other project 
		 * and then back.
		 * </p>
		 * <p>
		 * It is possible that there will be memory leak
		 * if expanded paths have been removed from model, but
		 * effect of this is quite insignificant.
		 * </p>
		 */
		public void expand(JTree tree){
			Iterator iter = expanded.iterator();
			while(iter.hasNext()){
				tree.expandPath( (TreePath) iter.next() );
			}
			System.out.println(expanded.size());
		}
		
	} // }}}	
	

}