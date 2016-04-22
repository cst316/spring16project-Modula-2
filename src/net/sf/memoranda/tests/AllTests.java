package net.sf.memoranda.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalendarDateTest.class, DefectListImplTest.class, EventTest.class, ProjectTest.class,
		TaskListImplTest.class, TimeLogTest.class })
public class AllTests {

}
