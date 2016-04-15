package net.sf.memoranda.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.DefectImpl;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.Phase;
import net.sf.memoranda.Start;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;

public class DefectListImplTest {
	
	static DefectListImpl defectlist;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		String[] starter = {"m", "e", "m", "o", "r", "a", "n", "d", "a"};
		Start.main(starter);
		defectlist = new DefectListImpl(null);
	}

	@After
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void initializeGoodDefectList() {
		
		assert(defectlist.getLastDefectId().equals("1"));
		
		/*	Defect 1
		 *	ID: 1
		 *	Description: the first defect
		 *	Date Found: 4/14/16
		 *	Type: 30 Build, Package
		 *	Est Fix Time: 34
		 *	Injection: Planning
		 *	Status: Active
		 */
		
		Phase injphase1 = null;
		String inj1 = "Planning";
		
		for (Phase p : Phase.values()) {
			if (inj1.equals(p.toString())) {
				injphase1 = p;
			}
		}
		
		Phase remphase1 = null;
		
		defectlist.createDefect(new CalendarDate("4/14/16"), "30 Build, Package", injphase1, Util.getMillisFromMinutes("34"), 
				Util.getMillisFromMinutes("0"), null, remphase1, null, "the first defect", false);
		
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/16
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		
		
		/*	Defect 3
		 *	ID: 3
		 *	Description: the 3rd defect
		 *	Date Found: 9/23/14
		 *	Type: 50 Interface
		 *	Est Fix Time: 1000
		 *	Injection: Code Review
		 *	Status: Completed
		 *	Date Fixed: 4/14/16
		 *	Act Time: 89
		 *	Remove: Code Review
		 *	Fix Reference: lazy
		 */
		
	}
	
	@Test
	public void initializeBadDefectList() {
		
		
		/*	Defect 1
		 *	ID: 1
		 *	Description: the first defect
		 *	Date Found: 02/20/95
		 *	Type: 30 Build, Package
		 *	Est Fix Time: asdf
		 *	Injection: Planning
		 *	Status: Active
		 */
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/44
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		
		
		/*	Defect 3
		 *	ID: 3
		 *	Description: the 3rd defect
		 *	Date Found: 4/20/44
		 *	Type: 50 Interface
		 *	Est Fix Time: 1000
		 *	Injection: Postmortem
		 *	Status: Completed
		 *	Date Fixed: 4/14/16
		 *	Act Time: -1
		 *	Remove: Code Review
		 */
		
		
		/*	Defect 4
		 *	ID: 4
		 *	Description: the 4 defect
		 *	Date Found: 4/14/4$
		 *	Type: 50 Interface
		 *	Est Fix Time: 55
		 *	Injection: Test
		 *	Status: Completed
		 *	Date Fixed: 4/14/16
		 *	Act Time: 0
		 *	Remove: Postmortem
		 *	Fix Reference: stan
		 */
		
		
	}
	
	@Test
	public void editDefectList() {
	}
	
	@Test
	public void removeDefectsFromDefectList() {
	}
}
