package Cricket;
import repo.*;

public class MatchController {

    public static Team prevTeam  = null;
    private Team team1 =  null;
    private Team team2 =  null;

    CricketRepoMysqlImpl cricketRepoMysql = new CricketRepoMysqlImpl();

    public void controller(String printOver,MatchType matchType,Series series)
    {
        int Series_id = cricketRepoMysql.insertSeriesInfo(series.getTeam1Name(),series.getTeam2Name());

        setTeamDetails(series);

        startMatch(series.getNoOfMatches(),printOver,Series_id,matchType.getOvers(),series);
        endMatch(Series_id,series);
    }


    public void setTeamDetails(Series series) {

        team1 = cricketRepoMysql.fetchTeamDetails(series.getTeam1Name());
        team2 = cricketRepoMysql.fetchTeamDetails(series.getTeam2Name());

    }


    public void startMatch(int noOfMatches,String printOver,int Series_id,int oversPlayed,Series series)
    {
        for(int i=1;i <= noOfMatches; i++) {

            Match match =  new Match();
            int tossResult = match.doToss();                          //invoking toss function

            CricketUtils.teamReset(team1);
            CricketUtils.teamReset(team2);

            cricketRepoMysql.updateMatchStatus("ongoing");

            if (tossResult == 1)  {                               //if tossResult is 1 team1 gets to play first else team2
                team1.setTossWon(1); team2.setTossWon(0);
                match.playInnings(oversPlayed, team1, printOver, 1,Series_id,i);
                prevTeam = team1;

                match.playInnings(oversPlayed, team2, printOver, 2,Series_id,i);
                cricketRepoMysql.updateMatchStatus("completed");
            }

            else {
                team2.setTossWon(1);  team1.setTossWon(0);
                match.playInnings(10, team2, printOver, 1,Series_id,i);
                prevTeam = team2;

                match.playInnings(10, team1, printOver, 2,Series_id,i);
                cricketRepoMysql.updateMatchStatus("completed");
            }

            PrintMatchDetails.finalScoreBoard(Series_id,team1,team2,series);
        }
    }


    private void endMatch(int Series_id,Series series) {

        cricketRepoMysql.updateSeriesWinnerInfo( Series_id);
        PrintMatchDetails.seriesResult(series);
        Connect_db.destroy();
    }


}