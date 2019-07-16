package Cricket;

import java.util.HashMap;
import java.util.Map;

public class Series {
    private Map<String,Integer> teamMatchesWon = new HashMap<>();
    private int noOfMatches;
    private String team1Name;
    private String team2Name;

    public Series(int noOfMatches,String team1Name,String team2Name)
    {
        setNoOfMatches(noOfMatches);
        setTeam1Name(team1Name);
        setTeam2Name(team2Name);
        teamMatchesWon.put(team1Name,0);
        teamMatchesWon.put(team2Name,0);
    }

    public void setTeamMatchesWon(String teamName) {

        int MatchesWon = this.teamMatchesWon.get(teamName);
        teamMatchesWon.put(teamName,MatchesWon+1);
    }

    public int getNoOfMatches() {
        return noOfMatches;
    }

    public void setNoOfMatches(int noOfMatches) {
        this.noOfMatches = noOfMatches;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public Integer getTeamMatchesWon(String name) {
        return teamMatchesWon.get(name);
    }
}
