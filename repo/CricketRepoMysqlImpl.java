package repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import Cricket.*;

public class CricketRepoMysqlImpl implements  CricketRepo  {

    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static CricketRepoMysqlImpl cricketRepoMysql =  new CricketRepoMysqlImpl();

    public static CricketRepoMysqlImpl getCricketRepoMysql() {
        return cricketRepoMysql;
    }


    @Override
    public Team fetchTeamDetails(String teamName)
    {
        List<Player> playerList = new ArrayList<>();
        try {
            preparedStatement = Connect_db.getConnection().prepareStatement("SELECT * FROM player WHERE team_name = '" + teamName + "'");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Player player =  new Player(resultSet.getString("player_name"),resultSet.getString("type"),resultSet.getInt("player_id"));
                playerList.add(player);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return new Team(teamName, playerList, 0, 0);
    }


    @Override
    public void resetPlayerInfo(String teamName) {

        try {
            int Zero = 0;
            preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET run_scored = "+Zero+" ,balls_played = "+Zero+",strike_rate = "+Zero+",fours = "+Zero+",sixes = "+Zero+", PLayer.out = "+Zero+" WHERE team_name = '"+teamName+"'");
            preparedStatement.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Override
    public void updateBallInfo(int cntr, Team team, int striker1, int run, int striker2,int Series_id,int matchNo)
    {
        try {
            int prev_team_score = 0;
            if(MatchController.prevTeam != null)
                prev_team_score = MatchController.prevTeam.getTotalScore();

            if(run<7)
            {
                int teamTotalRuns = team.getTotalScore() + run;
                int teamTotalWickets = team.getTotalWickets();
                preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO balls_data(ballno,runs,team_name,wicket,team_total_runs,team_total_wickets,player1,player2,player1_score,player2_score,prev_team_score,series_no,match_no) VALUES("+ cntr +","+ run +",'"+ team.getName()+"', '"+ 0 +"',"+ teamTotalRuns +","+ teamTotalWickets +",'"+ team.getPlayerList().get(striker1).getName()+"','"+ team.getPlayerList().get(striker2).getName()+"',"+ team.getPlayerList().get(striker1).getRunScored()+","+ team.getPlayerList().get(striker2).getRunScored()+","+ prev_team_score+","+Series_id+","+matchNo+")");
                preparedStatement.executeUpdate();
            }
            else
            {
                run = 0;
                int teamTotalRuns = team.getTotalScore();
                int teamTotalWickets = team.getTotalWickets()+ 1;
                preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO balls_data(ballno,runs,team_name,wicket,team_total_runs,team_total_wickets,player1,player2,player1_score,player2_score,prev_team_score,series_no,match_no) VALUES("+ cntr +","+ run +",'"+ team.getName()+"', '"+ 1 +"',"+ teamTotalRuns +","+ teamTotalWickets +",'"+ team.getPlayerList().get(striker1).getName()+"','"+ team.getPlayerList().get(striker2).getName()+"',"+ team.getPlayerList().get(striker1).getRunScored()+","+ team.getPlayerList().get(striker2).getRunScored()+","+ prev_team_score+","+Series_id+","+matchNo+")");
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Override
    public void updatePlayerInfo(Team team)
    {
        try {
            for(int i = 0;i < team.getPlayerList().size();i++)
            {
                preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET run_scored = "+ team.getPlayerList().get(i).getRunScored()+" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                preparedStatement.executeUpdate();

                preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET fours= "+ team.getPlayerList().get(i).getFours() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                preparedStatement.executeUpdate();

                preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET sixes= "+ team.getPlayerList().get(i).getSixes() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                preparedStatement.executeUpdate();

                preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET balls_played = "+ team.getPlayerList().get(i).getBallsPlayed() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                preparedStatement.executeUpdate();

                if(team.getPlayerList().get(i).getBallsPlayed() != 0)
                {
                    double StrikeRate = (team.getPlayerList().get(i).getRunScored()/team.getPlayerList().get(i).getBallsPlayed())*100;
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET strike_rate = "+ StrikeRate +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                    preparedStatement.executeUpdate();
                }
                if(team.getPlayerList().get(i).getOut() == 1) {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE player SET player.out = "+ 1 +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Override
    public void updateMatchStatus(String completed)
    {
        try{
            preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE balls_data SET status = '"+ completed +"'");
            preparedStatement.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Override
    public int insertSeriesInfo(String teamName1, String teamName2)
    {
        int id=0;
        try {
            preparedStatement = Connect_db.getConnection().prepareStatement("INSERT INTO series_info(team1_name,team2_name,total_matches) VALUES('"+teamName1+"', '"+teamName2+"', "+5+")");
            preparedStatement.executeUpdate();

            preparedStatement = Connect_db.getConnection().prepareStatement("SELECT * FROM series_info ORDER BY series_id DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();


            while(resultSet.next())
            {
                id = resultSet.getInt(1);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return id;
    }


    @Override
    public void updateSeriesInfo(int Series_id,String teamName)
    {
        try{
            preparedStatement = Connect_db.getConnection().prepareStatement("SELECT * FROM series_info WHERE series_id = "+ Series_id+"");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                if(teamName.equals("CSK"))
                {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE series_info SET team1_won = team1_won + 1 WHERE series_id= "+ Series_id+" ");
                    preparedStatement.executeUpdate();
                }

                else {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE series_info SET team2_won = team2_won + 1 WHERE series_id= "+ Series_id +"");
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Override
    public void updateSeriesWinnerInfo(int Series_id)
    {
        try {
            preparedStatement = Connect_db.getConnection().prepareStatement("SELECT * FROM series_info WHERE series_id = " + Series_id + "");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (resultSet.getInt("team1_won") > resultSet.getInt("team2_won")) {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE series_info SET team_won = 'CSK' WHERE series_id= " + Series_id + "");
                    preparedStatement.executeUpdate();
                }
                else
                if (resultSet.getInt("team1_won") < resultSet.getInt("team2_won")) {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE series_info SET team_won = 'RCB' WHERE series_id= " + Series_id + "");
                    preparedStatement.executeUpdate();
                }
                else {
                    preparedStatement = Connect_db.getConnection().prepareStatement("UPDATE series_info SET team_won = 'DRAW' WHERE series_id= " + Series_id + "");
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
}
