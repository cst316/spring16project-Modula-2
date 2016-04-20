package net.sf.memoranda;

public enum Phase {

	PLANNING("Planning"), 
	DESIGN("Design"),
	CODE("Code"),
	CODE_REVIEW("Code Review"),
	COMPILE("Compile"),
	TEST("Test"),
	POSTMORTEM("Postmortem"),
	INVALID("Invalid");
	
	private String phaseName;
	
	private Phase(String phaseName) {
		this.phaseName = phaseName;
	}
	
	@Override
	public String toString() {
		return phaseName;
	}
}
