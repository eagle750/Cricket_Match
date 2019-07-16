package Cricket;

import repo.CricketRepoMysqlImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

class PrintMatchDetails {
    static private CricketRepoMysqlImpl cricketRepoMysql = new CricketRepoMysqlImpl();

    static private DecimalFormat df = new DecimalFormat("#.#");

    static private PreparedStatement preparedStatement = null;

    static void ongoingScoreBoard(Team team, int innings, double curOver, int striker1, int striker2)    //function to print the scoreboard for given team and given over
    {

        System.out.println("************************************************************");
        System.out.println("				SCOREBOARD				");
        System.out.println("************************************************************\n");
        System.out.println(innings + " innings" + " " + team.getName() +" is batting\n");
        if(innings == 2)
            System.out.println( MatchController.prevTeam.getName() +  "  scored -  " + MatchController.prevTeam.getTotalScore());
        System.out.println("Over - " + df.format(curOver));
        System.out.println( team.getPlayerList().get(striker1).getName() + "*" + "      - " + team.getPlayerList().get(striker1).getRunScored() + "   4's - " + team.getPlayerList().get(striker1).getFours() + "   6's - " + team.getPlayerList().get(striker1).getSixes());
        System.out.println( team.getPlayerList().get(striker2).getName() + "      - " + team.getPlayerList().get(striker2).getRunScored() + "   4's - " + team.getPlayerList().get(striker2).getFours() + "   6's - " + team.getPlayerList().get(striker2).getSixes());

        System.out.println("************************************************************\n");
    }


     static void finalScoreBoard(int Series_id,Team team1,Team team2,Series series)             //function to print the result of the match
    {
        System.out.println("\n*****************************************************************");
        System.out.println("FINAL RESULT");
        System.out.println("*****************************************************************\n");
        if (team1.getTossWon() == 1)
            System.out.println(team1.getName() + "won the toss");
        else
            System.out.println(team2.getName() + "won the toss");

        computeFinalResult(Series_id,team1,team2,series);
    }


    public static void displayTeamDetails(String TeamName,int TotalRuns,int TotalWickets)
    {
        int cntr = 1;
        System.out.println("        "+TeamName);
        System.out.println("--------------------------------------------------");

        try {

            ResultSet resultSet1;
            preparedStatement = Connect_db.getConnection().prepareStatement("SELECT * FROM player WHERE team_name= '"+ TeamName + "'");
            resultSet1 = preparedStatement.executeQuery();

            while(resultSet1.next())
            {
                System.out.print(cntr+ "     ");
                if(resultSet1.getInt("Out") == 1)
                    System.out.println(resultSet1.getString("player_name") + "  "+ resultSet1.getString("type")+ "			" +  resultSet1.getString("run_scored") + "(" + resultSet1.getString("balls_played") + ")   " + "4's-" +resultSet1.getString("fours") + "   6's-" + resultSet1.getString("sixes"));
                else
                    System.out.println(resultSet1.getString("player_name") + "*" + "  " + resultSet1.getString("type") +"			" +  resultSet1.getString("run_scored") + "(" + resultSet1.getString("balls_played") +")   " + "4's-" +resultSet1.getString("fours") + "   6's-" + resultSet1.getString("sixes"));
                cntr++;
            }

            System.out.println("\nTOTAL" + "				" + TotalRuns + "/" + TotalWickets + "\n");
            System.out.println("--------------------------------------------------");
        }

        catch(SQLException se){
            se.printStackTrace();
        }
    }


    public static void seriesResult(Series series) {

        System.out.println(series.getTeam1Name() + " won " + series.getTeamMatchesWon(series.getTeam1Name())+ "\n");
        System.out.println(series.getTeam2Name() + " won " + series.getTeamMatchesWon(series.getTeam2Name())+ "\n");

        int team1Wins = series.getTeamMatchesWon(series.getTeam1Name());
        int team2Wins = series.getTeamMatchesWon(series.getTeam2Name());
        if(team1Wins > team2Wins)
            System.out.println(series.getTeam1Name() +" won the series by " + team1Wins + "-" + team2Wins );
        else if(team2Wins > team1Wins)
            System.out.println(series.getTeam2Name() +" won the series by " + team2Wins + "-" + team1Wins );
        else
            System.out.println("Series Draw");
    }


    private static void computeFinalResult(int Series_id,Team team1,Team team2,Series series) {

        try {
            if( team1.getTotalScore() > team2.getTotalScore()) {
                series.setTeamMatchesWon(team1.getName());

                cricketRepoMysql.updateSeriesInfo(Series_id,team1.getName());

                if(team1.getTossWon() == 1) {
                    int winningRun = winningRunTeam(team1,team2);
                    preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO result(team1_name,team2_name,winning_team,winning_run,winning_wicket,toss_won, series_no) VALUES('" + team1.getName() + "','" + team2.getName() + "','" + team1.getName() + "', " + winningRun + ", " + 0 + ",'" + team1.getName() + "', " + Series_id + ")");
                    preparedStatement.executeUpdate();
                }
                else
                {
                    int winningWicket = winningWicketWicket(team1);
                    preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO result(team1_name,team2_name,winning_team,winning_run,winning_wicket,toss_won,series_no) VALUES('" + team1.getName() + "','" + team2.getName() + "','" + team1.getName() + "', "+ 0 + ", "+ winningWicket + ",'"+ team1.getName() + "'," + Series_id + ")");
                    preparedStatement.executeUpdate();
                }
            }
            else if(team1.getTotalScore() < team2.getTotalScore())
            {
                cricketRepoMysql.updateSeriesInfo(Series_id,team2.getName());

                series.setTeamMatchesWon(team2.getName());
                if(team2.getTossWon() == 1) {

                    int winningRun = winningRunTeam(team2,team1);
                    preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO result(team1_name,team2_name,winning_team,winning_run,winning_wicket,toss_won, series_no) VALUES('" + team1.getName() + "','" + team2.getName() + "','" + team2.getName() + "', " + winningRun + ", " + 0 + ",'" + team2.getName() + "', " + Series_id + ")");
                    preparedStatement.executeUpdate();
                }
                else
                {
                    int winningWicket = winningWicketWicket(team2);
                    preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO result(team1_name,team2_name,winning_team,winning_run,winning_wicket,toss_won,series_no) VALUES('" + team1.getName() + "','" + team2.getName() + "','" + team2.getName() + "', " + 0 + ", " + winningWicket + ",'" + team2.getName() + "'," + Series_id + ")");
                    preparedStatement.executeUpdate();
                }
            }
            else {
                System.out.println("Draw");
            }
            displayTeamDetails(team1.getName(), team1.getTotalScore(), team1.getTotalWickets());
            displayTeamDetails(team2.getName(), team2.getTotalScore(), team2.getTotalWickets());
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    private static int winningRunTeam(Team team1,Team team2)
    {
        int winningRun = team2.getTotalScore() - team1.getTotalScore();
        System.out.println(team2.getName() + " won by " + winningRun + " runs\n");

        return winningRun;
    }


    private static int winningWicketWicket(Team team)
    {
        int winningWicket = 10 - team.getTotalWickets();
        System.out.println(team.getName() + " won by " + winningWicket + " wickets\n");

        return winningWicket;
    }
}
