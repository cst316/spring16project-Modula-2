package net.sf.memoranda;

import nu.xom.Attribute;



public class ProjectTeamBuilder {
	
    public static int[][] teamBuilder(int[][] currentTeam, int[][] appendedPerson) {
        int[][] teamCombined = new int[currentTeam.length + appendedPerson.length][];
        System.arraycopy(currentTeam, 0, teamCombined, 0, currentTeam.length);
        System.arraycopy(appendedPerson, 0, teamCombined, currentTeam.length, appendedPerson.length);
        return teamCombined;
    }
    

    
    
}
