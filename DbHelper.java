import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class DbHelper {
	
	static {
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           e.getMessage();
       }
    }
	
	static PreparedStatement preparedStatement = null;
	static ResultSet resultSet = null;
	
	public static void insertInitialInfo(String[] collection)
	{
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){

            for(int i = 0;i < collection.length;i++)        //initialising the player list
            {
            	String str = collection[i];
            	preparedStatement = con.prepareStatement("INSERT INTO player(player_name) VALUES('"+str+"')");
            	preparedStatement.executeUpdate();
            }

        	preparedStatement = con.prepareStatement("INSERT INTO team(team_name) VALUES('CSK')");
        	preparedStatement.executeUpdate();
        	
        	preparedStatement = con.prepareStatement("INSERT INTO team(team_name) VALUES('RCB')");
        	preparedStatement.executeUpdate();

        }
        catch(SQLException se){
        	se.printStackTrace();
        }
	}
	
	
	public static void updateBallInfo(int cntr,Team team, int striker1, int run,int striker2)
	{
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){

			int prev_team_score;
			if(MatchController.prevTeam != null)

				prev_team_score = MatchController.prevTeam.getTotalScore();
			else
				prev_team_score = 0;

        	if(run<7)
        	{
        		int teamTotalRuns = team.getTotalScore() + run;
        		int teamTotalWickets = team.getTotalWickets();

        		preparedStatement = con.prepareStatement("INSERT INTO balls_data(ballno,runs,team_name,wicket,team_total_runs,team_total_wickets,player1,player2,player1_score,player2_score,prev_team_score) VALUES("+ cntr +","+ run +",'"+ team.getName()+"', '"+ 0 +"',"+ teamTotalRuns +","+ teamTotalWickets +",'"+ team.getPlayerList().get(striker1).getName()+"','"+ team.getPlayerList().get(striker2).getName()+"',"+ team.getPlayerList().get(striker1).getRunScored()+","+ team.getPlayerList().get(striker2).getRunScored()+","+ prev_team_score+")");
        		preparedStatement.executeUpdate();
        	
        	}
        	else
        	{
        		run = 0;
        		int teamTotalRuns = team.getTotalScore();
        		int teamTotalWickets = team.getTotalWickets()+ 1;
        		preparedStatement = con.prepareStatement("INSERT INTO balls_data(ballno,runs,team_name,wicket,team_total_runs,team_total_wickets,player1,player2,player1_score,player2_score,prev_team_score) VALUES("+ cntr +","+ run +",'"+ team.getName()+"', '"+ 1 +"',"+ teamTotalRuns +","+ teamTotalWickets +",'"+ team.getPlayerList().get(striker1).getName()+"','"+ team.getPlayerList().get(striker2).getName()+"',"+ team.getPlayerList().get(striker1).getRunScored()+","+ team.getPlayerList().get(striker2).getRunScored()+","+ prev_team_score+")");
        		preparedStatement.executeUpdate();
        	}
        		

        }
        catch(SQLException se){
        	se.printStackTrace();
        }
	}
	
	
	public static void updateTeamInfo(String team1, String team2)
	{
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){
        	
        	
        	preparedStatement = con.prepareStatement("UPDATE team SET toss_won = '"+ 1 +"' where team_name = '"+team1 +"'");
        	preparedStatement.executeUpdate();
        		
        	preparedStatement = con.prepareStatement("UPDATE team SET toss_won = '"+ 0 +"' where team_name ='"+team2+"'");
        	preparedStatement.executeUpdate();

        }
        catch(SQLException se){
        	se.printStackTrace();
        }
	}
	
	
	public static void updatePlayerInfo(Team team)
	{
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){
        	
        	preparedStatement = con.prepareStatement("UPDATE team SET total_runs = "+ team.getTotalScore() +" WHERE team_name= '"+ team.getName()+"'");
        	preparedStatement.executeUpdate();
        	
        	preparedStatement = con.prepareStatement("UPDATE Team SET total_wickets = "+ team.getTotalWickets() +" WHERE team_name= '"+ team.getName()+"'");
        	preparedStatement.executeUpdate();
        	
        	for(int i = 0;i < team.getPlayerList().size();i++)
        	{
        		preparedStatement = con.prepareStatement("UPDATE player SET run_scored = "+ team.getPlayerList().get(i).getRunScored()+" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            	preparedStatement.executeUpdate(); 
            	
            	preparedStatement = con.prepareStatement("UPDATE player SET fours= "+ team.getPlayerList().get(i).getFours() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            	preparedStatement.executeUpdate();
            	
            	preparedStatement = con.prepareStatement("UPDATE player SET sixes= "+ team.getPlayerList().get(i).getSixes() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            	preparedStatement.executeUpdate();
            	
            	preparedStatement = con.prepareStatement("UPDATE player SET balls_played = "+ team.getPlayerList().get(i).getBallsPlayed() +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            	preparedStatement.executeUpdate();
            	
            	if(team.getPlayerList().get(i).getBallsPlayed() != 0)
            	{
            		double StrikeRate = (team.getPlayerList().get(i).getRunScored()/team.getPlayerList().get(i).getBallsPlayed())*100;
            		preparedStatement = con.prepareStatement("UPDATE player SET strike_rate = "+ StrikeRate +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            		preparedStatement.executeUpdate();
            	}
           	    if(team.getPlayerList().get(i).getOut() == 1) {
            		int temp = 1;
            		preparedStatement = con.prepareStatement("UPDATE player SET player.out = "+ temp +" WHERE player_id=" + team.getPlayerList().get(i).getPlayerId());
            		preparedStatement.executeUpdate();
            	}
        	}
        }
  
    catch(SQLException se){
    	se.printStackTrace();
    }		
	}
	
	
    
    public static Vector<Player> assignTeam( String name, Set<Integer> playerSet)                        //function to create 2 teams from given list of players
    {
    	Vector<Player> tempList = new Vector<Player>();
    	Vector<Integer>playerIdList = new Vector<Integer>();
    	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){
        	preparedStatement = con.prepareStatement("SELECT * FROM player");
        	resultSet = preparedStatement.executeQuery();
        	
        	while(resultSet.next())
        	{
        		playerIdList.add(resultSet.getInt(1));
        	}
        	
    	    int noOfPlayers = playerIdList.size();
        	  int  i = 0;    
        	  
        	    while(i<7)
        	        {
        	            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);     //randomly assign an index of a player to put in a team
        	            if(!playerSet.contains(ind))                                    //if the set doesn't contain player list then add to the designated team
        	            {                                                               //else it has already been assigned a team
        	                playerSet.add(ind);
        	                int index = playerIdList.get(ind);
        	            	preparedStatement = con.prepareStatement("UPDATE player SET team_name = '"+name+"' where player_id = "+ index);
        	            	preparedStatement.executeUpdate();
        	                i++;
        	            }
        	        }
        	    
        	    preparedStatement =  con.prepareStatement("SELECT * FROM player WHERE team_name ='"+ name + "' ");
        	    resultSet = preparedStatement.executeQuery();
        	    
        	    while(resultSet.next())
        	    {
        	    	Player player = new Player(resultSet.getString(2), resultSet.getInt(1),0,0,0,0,0);
        	    	tempList.add(player);
        	    }  
        }
    	
        catch(SQLException se){
        	se.printStackTrace();
        }
    	return tempList;
    }

	public static void updateMatchStatus(String completed) {

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){


			preparedStatement = con.prepareStatement("UPDATE balls_data SET status = '"+ completed +"'");
			preparedStatement.executeUpdate();

		}
		catch(SQLException se){
			se.printStackTrace();
		}
	}
}
