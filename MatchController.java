import java.util.*;
import java.util.concurrent.*;
import java.sql.*;

public class MatchController {
	
	
	Vector<Player> team1Players = new Vector<Player>();                  //array to store the list of names for team1
    Vector<Player> team2Players =  new Vector<Player>();                 //array to store the list of names for team2
    Set<Integer> playerSet = new HashSet<Integer>();        //set to check if a player chosen has already been assigned a team

    static Team prevTeam;
    Team team1;
    Team team2;
    
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null,resultSet1=null,resultSet2=null;
    
    
    public void controller(String printOver)
    {   
    team1Players = assignTeam("CSK");                                   //function to assign randomly create 2 teams with 7 players each
    team1 = new Team("CSK", team1Players, 0, 0);

    team2Players = assignTeam("RCB");
    team2 = new Team("RCB", team2Players, 0, 0);
    
    System.out.println("Team 1 members are:- \n");
    print(team1Players);
    System.out.println();
    System.out.println("Team 2 members are:- \n");
    print(team2Players);
    System.out.println();
    
    Match match =  new Match();
    int tossResult = match.toss();                          //invoking toss function

    int team1Score,team2Score;

    if(tossResult == 1)                                     //if tossResult is 1 team1 gets to play first else team2
    {

         System.out.println("First innings\n");
         team1Score = match.innings(10,team1, printOver, 1 );
         prevTeam = team1;
         
         System.out.println("2nd innings\n");
         team2Score = match.innings(10,team2, printOver, 2);
    }
    
    else
    {
         System.out.println("First innings\n");
         team1Score = match.innings(10,team2, printOver, 1);
         prevTeam = team2;
         
         System.out.println("2nd innings");
         team2Score = match.innings(10,team1, printOver, 2);
    }
    
    result();
    
    }
    
    
    public Vector<Player> assignTeam( String name)                        //function to create 2 teams from given list of players
    {
    	Vector<Player> tempList = new Vector<Player>();
    	Vector<Integer>playerIdList = new Vector<Integer>();
    	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");
){
        	preparedStatement = con.prepareStatement("SELECT * FROM Player");
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
        	            	preparedStatement = con.prepareStatement("UPDATE Player SET TeamName = '"+name+"' where PlayerId = "+ index);
        	            	preparedStatement.executeUpdate();
        	                i++;
        	            }
        	        }
        	    
        	    preparedStatement =  con.prepareStatement("SELECT * FROM Player WHERE TeamName ='"+ name + "' ");
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
    
    
    public void print(Vector<Player> team)                     //function to print the list of players in each team
    {
    for(int i = 0;i < 7; i++)
        {
           System.out.print (team.get(i) + " ");
        }
    System.out.println();
    }
    
    
    public void result()              //function to print the result of the match
    {
    	System.out.println("\n*****************************************************************");
    	System.out.println("FINAL RESULT");
    	System.out.println("*****************************************************************\n");
    	
    	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");
){
        	
        	int One = 1,Zero = 0;
        	
        	preparedStatement = con.prepareStatement("SELECT * FROM Team WHERE TossWon=" + One);
        	resultSet1 = preparedStatement.executeQuery();
        	
        	preparedStatement = con.prepareStatement("SELECT * FROM Team WHERE TossWon=" + Zero);
        	resultSet2 = preparedStatement.executeQuery();
        	
        	int Team1Score = 0, Team2Score = 0;
        	String Team1Name = null,Team2Name = null ;
        	int Team2Wickets = 0,Team1Wickets =0;
        	while(resultSet1.next())
        	{
        		Team1Score = resultSet1.getInt("TotalRuns");
        		Team1Name = resultSet1.getString("TeamName") ;
        		Team1Wickets = resultSet1.getInt("TotalWickets");

        	}
        	
        	while(resultSet2.next())
        	{
        		Team2Score = resultSet2.getInt("TotalRuns");
        		Team2Wickets = resultSet2.getInt("TotalWickets");
        		Team2Name = resultSet2.getString("TeamName") ;
        	}
        	
        	if( Team1Score > Team2Score)
        	            {
        	                int winningRun = Team1Score - Team2Score;
        	                System.out.println( Team1Name  + " won by " + winningRun + " runs\n");
        	            }
        	            else if(Team1Score < Team2Score)
        	            {
        	                int winningWicket = 7 - Team2Wickets;
        	                System.out.println( Team2Name + " won by " + winningWicket + " wickets\n");
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
    
    
    public void displayTeamDetails(String TeamName,int TotalRuns,int TotalWickets)
    {
    	int cntr = 1;
    	System.out.println("--------------------------------------------------");
    	try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");
){
    		
        	   		
    	ResultSet resultSet1;
    	preparedStatement = con.prepareStatement("SELECT * FROM Player WHERE TeamName= '"+ TeamName + "'");
    	resultSet1 = preparedStatement.executeQuery();
    	
    	
    	while(resultSet1.next())
	    {
    		System.out.print(cntr+ "     ");
    		if(resultSet1.getInt("Out") == 1)
    			System.out.println(resultSet1.getString("PlayerName") + "			" +  resultSet1.getString("runScored") + "(" + resultSet1.getString("ballsPlayed") +")");
    		else
    			System.out.println(resultSet1.getString("PlayerName") + "*" +"			" +  resultSet1.getString("runScored") + "(" + resultSet1.getString("ballsPlayed") +")");
	    	cntr++;
	    } 
    	  		
    	System.out.println("\nTOTAL" + "				" + TotalRuns + "/" + TotalWickets + "\n");
    	System.out.println("--------------------------------------------------");
    	}
    	
    	catch(SQLException se){
        	se.printStackTrace();
        }
    }
    

}