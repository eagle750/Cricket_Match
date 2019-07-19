package Cricket;
import repo.*;

public class MatchController {

    public static Team prevTeam  = null;
    private Team team1 =  null,battingTeam = null;
    private Team team2 =  null,ballingTeam = null;

    private CricketRepoMysqlImpl cricketRepoMysql = new CricketRepoMysqlImpl();

    void controller(String printOver, MatchType matchType, Series series)
    {
        int Series_id = cricketRepoMysql.insertSeriesInfo(series.getTeam1Name(),series.getTeam2Name());

        setTeamDetails(series);
        startMatch(series.getNoOfMatches(),printOver,Series_id,matchType.getOvers(),series);
        endMatch(Series_id,series);
    }


    private void setTeamDetails(Series series)
    {
        team1 = cricketRepoMysql.fetchTeamDetails(series.getTeam1Name());
        team2 = cricketRepoMysql.fetchTeamDetails(series.getTeam2Name());
    }


    private void startMatch(int noOfMatches, String printOver, int Series_id, int oversPlayed, Series series)
    {
        for(int i=1;i <= noOfMatches; i++) {

            Match match =  new Match();
            int tossResult = match.doToss();                          //invoking toss function

            CricketUtils.teamReset(team1);
            CricketUtils.teamReset(team2);

            cricketRepoMysql.updateMatchStatus("ongoing");

            if(tossResult == 1)
            {
                battingTeam = team1; team1.setTossWon(1);
                ballingTeam = team2; team2.setTossWon(0);
            }
            else
            {
                battingTeam = team2; team2.setTossWon(1);
                ballingTeam = team1; team1.setTossWon(0);
            }

            match.playInnings(oversPlayed, battingTeam, printOver, 1,Series_id,i);
            prevTeam = battingTeam;
            match.playInnings(oversPlayed, ballingTeam, printOver, 2,Series_id,i);
            cricketRepoMysql.updateMatchStatus("completed");

            PrintMatchDetails.finalScoreBoard(Series_id,battingTeam,ballingTeam,series);
        }
    }


    private void endMatch(int Series_id,Series series) {

        cricketRepoMysql.updateSeriesWinnerInfo( Series_id);
        PrintMatchDetails.seriesResult(series);
        Connect_db.destroy();
    }
}