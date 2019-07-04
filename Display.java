import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

public class Display {
	
	static DecimalFormat df = new DecimalFormat("#.#");
	static PreparedStatement preparedStatement = null;
	static ResultSet resultSet = null,resultSet1=null,resultSet2=null;	
	
	public static void ongoingScoreBoard(Team team, String printOver, int innings, double curOver,int striker1, int striker2)    //function to print the scoreboard for given team and given over
	{
		System.out.println("************************************************************");
		System.out.println("				SCOREBOARD				");
		System.out.println("************************************************************\n");
		System.out.println(innings + " innings" + " " + team.getName() +" is batting\n");
		if(innings == 2)
			System.out.println( MatchController.prevTeam.getName() +  "  scored -  " + MatchController.prevTeam.getTotalScore());
		System.out.println("Over - " + df.format(curOver));
		System.out.println( team.getPlayerList().get(striker1).getName() + "*" + "      - " + team.getPlayerList().get(striker1).getRunScored() + "   4's - " + team.getPlayerList().get(striker1).getFours() + "   6's - " + team.getPlayerList().get(striker1).getSixes() );
		System.out.println( team.getPlayerList().get(striker2).getName() + "      - " + team.getPlayerList().get(striker2).getRunScored() + "   4's - " + team.getPlayerList().get(striker2).getFours() + "   6's - " + team.getPlayerList().get(striker2).getSixes() );

		System.out.println("************************************************************\n");
	}

	
	public static void finalScoreBoard()              //function to print the result of the match
    {
    	System.out.println("\n*****************************************************************");
    	System.out.println("FINAL RESULT");
    	System.out.println("*****************************************************************\n");
    	
    	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");
){
        	
        	int One = 1,Zero = 0;
        	
        	preparedStatement = con.prepareStatement("SELECT * FROM team WHERE toss_won=" + One);
        	resultSet1 = preparedStatement.executeQuery();
        	
        	preparedStatement = con.prepareStatement("SELECT * FROM team WHERE toss_won=" + Zero);
        	resultSet2 = preparedStatement.executeQuery();
        	
        	int Team1Score = 0, Team2Score = 0;
        	String Team1Name = null,Team2Name = null ;
        	int Team2Wickets = 0,Team1Wickets =0;
        	while(resultSet1.next())
        	{
        		Team1Score = resultSet1.getInt("total_runs");
        		Team1Name = resultSet1.getString("team_name") ;
        		Team1Wickets = resultSet1.getInt("total_wickets");

        	}
        	
        	while(resultSet2.next())
        	{
        		Team2Score = resultSet2.getInt("total_runs");
        		Team2Wickets = resultSet2.getInt("total_wickets");
        		Team2Name = resultSet2.getString("team_name") ;
        	}
        	
        	if( Team1Score > Team2Score)
        	            {
        	                int winningRun = Team1Score - Team2Score;
        	                System.out.println( Team1Name  + " won by " + winningRun + " runs\n");
							preparedStatement = con.prepareStatement("INSERT INTO result(team_name,winning_run,winning_wicket) VALUES('"+ Team1Name +"', "+ winningRun +", "+ 0 +")");
							preparedStatement.executeUpdate();
        	            }
        	            else if(Team1Score < Team2Score)
        	            {
        	                int winningWicket = 7 - Team2Wickets;
        	                System.out.println( Team2Name + " won by " + winningWicket + " wickets\n");
							preparedStatement = con.prepareStatement("INSERT INTO result(team_name,winning_run,winning_wicket) VALUES('"+ Team2Name +"', "+ 0 +", "+ winningWicket +")");
							preparedStatement.executeUpdate();
        	            }
        	            else
        	                System.out.println("Draw");
        	        	
        	        	displayTeamDetails(Team1Name, Team1Score, Team1Wickets);
        	            displayTeamDetails(Team2Name, Team2Score, Team2Wickets);

        }
        catch(SQLException se){
        	se.printStackTrace();
        }        	        	
        
    }
	
	
	public static void displayTeamDetails(String TeamName,int TotalRuns,int TotalWickets)
    {
    	int cntr = 1;
    	System.out.println("--------------------------------------------------");
    	try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");){
    		
        	   		
    	ResultSet resultSet1;
    	preparedStatement = con.prepareStatement("SELECT * FROM player WHERE team_name= '"+ TeamName + "'");
    	resultSet1 = preparedStatement.executeQuery();
    	
    	
    	while(resultSet1.next())
	    {
    		System.out.print(cntr+ "     ");
    		if(resultSet1.getInt("Out") == 1)
    			System.out.println(resultSet1.getString("player_name") + "			" +  resultSet1.getString("run_scored") + "(" + resultSet1.getString("balls_played") +")");
    		else
    			System.out.println(resultSet1.getString("player_name") + "*" +"			" +  resultSet1.getString("run_scored") + "(" + resultSet1.getString("balls_played") +")");
	    	cntr++;
	    } 
    	  		
    	System.out.println("\nTOTAL" + "				" + TotalRuns + "/" + TotalWickets + "\n");
    	System.out.println("--------------------------------------------------");
    	}
    	
    	catch(SQLException se){
        	se.printStackTrace();
        }
    }
	
	
    
    public static void printPlayerDetails(Vector<Player> team)                     //function to print the list of players in each team
    {
    for(int i = 0;i < 7; i++)
        {
           System.out.print (team.get(i).getName() + " ");
        }
    System.out.println();
    }
    
}
