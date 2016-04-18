package net.sf.memoranda.tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectImpl;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.Phase;
import net.sf.memoranda.Start;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;

public class DefectListImplTest {
	
	static DefectListImpl defectlist;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] starter = {"m", "e", "m", "o", "r", "a", "n", "d", "a"};
		Start.main(starter);
	}
		
	@Before
	public void setUpBefore() throws Exception {
		defectlist = new DefectListImpl(null);
	}

	@After
	public void tearDownAfter() throws Exception {
		defectlist = new DefectListImpl(null);
	}
	
	@Test
	public void initializeGoodDefectList() {
		
		assert(defectlist.getAllDefects().size() == 0);
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
		
		assert(defectlist.getAllDefects().size() == 1);
		assert(defectlist.getLastDefectId().equals("2"));
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/16
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		Phase injphase2 = null;
		String inj2 = "Test";
		
		for (Phase p : Phase.values()) {
			if (inj2.equals(p.toString())) {
				injphase2 = p;
			}
		}
		
		Phase remphase2 = null;
		
		defectlist.createDefect(new CalendarDate("4/20/16"), "70 Data", injphase2, Util.getMillisFromMinutes("2"), 
				Util.getMillisFromMinutes("0"), null, remphase2, null, "the secUND defect", false);
		
		assert(defectlist.getAllDefects().size() == 2);
		assert(defectlist.getLastDefectId().equals("3"));
		
		
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
		Phase injphase3 = null;
		String inj3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (inj3.equals(p.toString())) {
				injphase3 = p;
			}
		}
		
		Phase remphase3 = null;
		String rem3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (rem3.equals(p.toString())) {
				remphase3 = p;
			}
		}
		
		defectlist.createDefect(new CalendarDate("9/23/14"), "50 Interface", injphase3, Util.getMillisFromMinutes("1000"), 
				Util.getMillisFromMinutes("89"), new CalendarDate("4/14/16"), remphase3, "lazy", "the 3rd defect", true);
		
		assert(defectlist.getAllDefects().size() == 3);
		assert(defectlist.getLastDefectId().equals("4"));
	}
	
	@Test
	public void initializeBadDefectList() {
		
		assert(defectlist.getAllDefects().size() == 0);
		assert(defectlist.getLastDefectId().equals("1"));
		
		/*	Defect 1
		 *	ID: 1
		 *	Description: the first defect
		 *	Date Found: 02/20/95
		 *	Type: 30 Build, Package
		 *	Est Fix Time: asdf
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
		
		defectlist.createDefect(new CalendarDate("02/20/95"), "30 Build, Package", injphase1, Util.getMillisFromMinutes("asdf"), 
				Util.getMillisFromMinutes("0"), null, remphase1, null, "the first defect", false);
		
		assert(defectlist.getAllDefects().size() == 1);
		assert(defectlist.getLastDefectId().equals("2"));
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/44
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		Phase injphase2 = null;
		String inj2 = "Test";
		
		for (Phase p : Phase.values()) {
			if (inj2.equals(p.toString())) {
				injphase2 = p;
			}
		}
		
		Phase remphase2 = null;
		
		defectlist.createDefect(new CalendarDate("4/20/44"), "70 Data", injphase2, Util.getMillisFromMinutes("2"), 
				Util.getMillisFromMinutes("0"), null, remphase2, null, "the secUND defect", false);
		
		assert(defectlist.getAllDefects().size() == 2);
		assert(defectlist.getLastDefectId().equals("3"));
		
		
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
		Phase injphase3 = null;
		String inj3 = "Postmortem";
		
		for (Phase p : Phase.values()) {
			if (inj3.equals(p.toString())) {
				injphase3 = p;
			}
		}
		
		Phase remphase3 = null;
		String rem3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (rem3.equals(p.toString())) {
				remphase3 = p;
			}
		}
		
		defectlist.createDefect(new CalendarDate("4/20/44"), "50 Interface", injphase3, Util.getMillisFromMinutes("1000"), 
				Util.getMillisFromMinutes("-1"), new CalendarDate("4/14/16"), remphase3, null, "the 3rd defect", true);
		
		assert(defectlist.getAllDefects().size() == 3);
		assert(defectlist.getLastDefectId().equals("4"));
		
		
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
		Phase injphase4 = null;
		String inj4 = "Test";
		
		for (Phase p : Phase.values()) {
			if (inj4.equals(p.toString())) {
				injphase4 = p;
			}
		}
		
		Phase remphase4 = null;
		String rem4 = "Postmortem";
		
		for (Phase p : Phase.values()) {
			if (rem4.equals(p.toString())) {
				remphase4 = p;
			}
		}
		
		defectlist.createDefect(new CalendarDate("4/14/4$"), "50 Interface", injphase4, Util.getMillisFromMinutes("55"),
				Util.getMillisFromMinutes("0"), new CalendarDate("4/14/16"), remphase4, "stan", "the 4 defect", true);
		
		assert(defectlist.getAllDefects().size() == 4);
		assert(defectlist.getLastDefectId().equals("5"));
	}
	
	@Test
	public void editDefectList() {
		
		assert(defectlist.getAllDefects().size() == 0);
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
		
		Defect d1 = defectlist.createDefect(new CalendarDate("4/14/16"), "30 Build, Package", injphase1, Util.getMillisFromMinutes("34"), 
				Util.getMillisFromMinutes("0"), null, remphase1, null, "the first defect", false);
		
		assert(defectlist.getAllDefects().size() == 1);
		assert(defectlist.getLastDefectId().equals("2"));
		
		d1.editApproximateFixTimeInMillis(Util.getMillisFromMinutes("33"));
		d1.editDescription("this is THE description");
		
		inj1 = "Test";
		for (Phase p : Phase.values()) {
			if (inj1.equals(p.toString())) {
				injphase1 = p;
			}
		}
		
		d1.editInjection(injphase1);
		
		assert(d1.getApproximateFixTimeInMillis() == Util.getMillisFromMinutes("33"));
		assert(d1.getDescription().equals("this is THE description"));
		assert(d1.getInjection() == injphase1);
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/16
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		Phase injphase2 = null;
		String inj2 = "Test";
		
		for (Phase p : Phase.values()) {
			if (inj2.equals(p.toString())) {
				injphase2 = p;
			}
		}
		
		Phase remphase2 = null;
		
		Defect d2 = defectlist.createDefect(new CalendarDate("4/20/16"), "70 Data", injphase2, Util.getMillisFromMinutes("2"), 
				Util.getMillisFromMinutes("0"), null, remphase2, null, "the secUND defect", false);
		
		assert(defectlist.getAllDefects().size() == 2);
		assert(defectlist.getLastDefectId().equals("3"));
		
		d2.editApproximateFixTimeInMillis(Util.getMillisFromMinutes("2222"));
		d2.editDescription("this is THE second description");
		
		inj2 = "Planning";
		for (Phase p : Phase.values()) {
			if (inj2.equals(p.toString())) {
				injphase2 = p;
			}
		}
		
		d2.editInjection(injphase2);
		
		assert(d2.getApproximateFixTimeInMillis() == Util.getMillisFromMinutes("2222"));
		assert(d2.getDescription().equals("this is THE second description"));
		assert(d2.getInjection() == injphase2);
		
		
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
		Phase injphase3 = null;
		String inj3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (inj3.equals(p.toString())) {
				injphase3 = p;
			}
		}
		
		Phase remphase3 = null;
		String rem3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (rem3.equals(p.toString())) {
				remphase3 = p;
			}
		}
		
		Defect d3 = defectlist.createDefect(new CalendarDate("9/23/14"), "50 Interface", injphase3, Util.getMillisFromMinutes("1000"), 
				Util.getMillisFromMinutes("89"), new CalendarDate("4/14/16"), remphase3, "lazy", "the 3rd defect", true);
		
		assert(defectlist.getAllDefects().size() == 3);
		assert(defectlist.getLastDefectId().equals("4"));
		
		d1.editApproximateFixTimeInMillis(Util.getMillisFromMinutes("1"));
		d1.editFixTimeInMillis(Util.getMillisFromMinutes("69"));
		d1.editDescription("this is THE third description");
		
		inj3 = "Postmortem";
		for (Phase p : Phase.values()) {
			if (inj3.equals(p.toString())) {
				injphase3 = p;
			}
		}
		
		d3.editInjection(injphase3);
		
		rem3 = "Test";
		for (Phase p : Phase.values()) {
			if (rem3.equals(p.toString())) {
				remphase3 = p;
			}
		}
		
		d3.editInjection(remphase3);
		
		assert(d3.getApproximateFixTimeInMillis() == Util.getMillisFromMinutes("1"));
		assert(d3.getFixTimeInMillis() == Util.getMillisFromMinutes("69"));
		assert(d3.getDescription().equals("this is THE third description"));
		assert(d3.getInjection() == injphase3);
		assert(d3.getRemove() == remphase3);
	}
	
	@Test
	public void removeDefectsFromDefectList() {
		
		assert(defectlist.getAllDefects().size() == 0);
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
		
		Defect d1 = defectlist.createDefect(new CalendarDate("4/14/16"), "30 Build, Package", injphase1, Util.getMillisFromMinutes("34"), 
				Util.getMillisFromMinutes("0"), null, remphase1, null, "the first defect", false);
		
		assert(defectlist.getAllDefects().size() == 1);
		assert(defectlist.getLastDefectId().equals("2"));
		
		assert(defectlist.removeDefect(d1));
		
		
		/*	Defect 2
		 *	ID: 2
		 *	Description: the secUND defect
		 *	Date Found: 4/20/16
		 *	Type: 70 Data
		 *	Est Fix Time: 2
		 *	Injection: Test
		 *	Status: Active
		 */
		Phase injphase2 = null;
		String inj2 = "Test";
		
		for (Phase p : Phase.values()) {
			if (inj2.equals(p.toString())) {
				injphase2 = p;
			}
		}
		
		Phase remphase2 = null;
		
		Defect d2 = defectlist.createDefect(new CalendarDate("4/20/16"), "70 Data", injphase2, Util.getMillisFromMinutes("2"), 
				Util.getMillisFromMinutes("0"), null, remphase2, null, "the secUND defect", false);
		
		assert(defectlist.getAllDefects().size() == 2);
		assert(defectlist.getLastDefectId().equals("3"));
		
		assert(defectlist.removeDefect(d2));
		
		
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
		Phase injphase3 = null;
		String inj3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (inj3.equals(p.toString())) {
				injphase3 = p;
			}
		}
		
		Phase remphase3 = null;
		String rem3 = "Code Review";
		
		for (Phase p : Phase.values()) {
			if (rem3.equals(p.toString())) {
				remphase3 = p;
			}
		}
		
		Defect d3 = defectlist.createDefect(new CalendarDate("9/23/14"), "50 Interface", injphase3, Util.getMillisFromMinutes("1000"), 
				Util.getMillisFromMinutes("89"), new CalendarDate("4/14/16"), remphase3, "lazy", "the 3rd defect", true);
		
		assert(defectlist.getAllDefects().size() == 3);
		assert(defectlist.getLastDefectId().equals("4"));
		
		assert(defectlist.removeDefect(d3));
	}
}