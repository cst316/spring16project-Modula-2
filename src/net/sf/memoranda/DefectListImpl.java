package net.sf.memoranda;

import java.util.Collection;
import java.util.Date;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;

public class DefectListImpl implements DefectList {

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Defect getDefect(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNextDefectNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Defect createDefect(Date datefound, int number, String type, String injection, String removal,
			int approximatefixtime, int fixtime, String fixreference, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDefect(Defect defect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDefect(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shiftDefectNumbers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection getAllDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getActiveDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long calculateTotalCompletionTimeFromDefects() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CalendarDate getEarliestDateFromDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getEarliestDateFromActiveDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getLatestDateFromDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarDate getLatestDateFromActiveDefects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getXMLContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
