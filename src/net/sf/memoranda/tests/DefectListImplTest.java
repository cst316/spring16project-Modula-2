package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectListListener;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;

public class DefectListImplTest {

	private static Vector<DefectListListener> defectListListeners = new Vector<DefectListListener>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	public static void addDefectListListener(DefectListListener listener) {
		  defectListListeners.add(listener);
	}
	
	static void notifyDefectListListeners() {
		  for (DefectListListener listener : defectListListeners) {
			  listener.defectListModified();
		  }
	}

}
