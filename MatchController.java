import java.util.*;
import java.util.concurrent.*;

public class MatchController {
	Vector<Player> team1Players = new Vector<Player>();                  //array to store the list of names for team1
    Vector<Player> team2Players =  new Vector<Player>();                 //array to store the list of names for team2
    Set<Integer> playerSet = new HashSet<Integer>();        //set to check if a player choosen has already been assigned a team

    static Team prevTeam;
    Team team1;
    Team team2;
    
    
    public void controller(String printOver, Vector<Player> collection)
    {
    team1Players = assignTeam(collection);                                   //function to assign andomly create 2 teams with 7 players each
    team1 = new Team("CSK", team1Players, 0, 0, 0);

    team2Players = assignTeam(collection);
    team2 = new Team("RCB", team2Players, 0, 0, 0);

    
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
         team1Score = match.innings(10,team1, printOver, 1);
         prevTeam = team1;
         
         System.out.println("2nd innings\n");
         team2Score = match.innings(10,team2, printOver, 2);
    }
    
    else
    {
         System.out.println("First innings\n");
         team1Score = match.innings(10,team2, printOver, 1);
         prevTeam = team1;
         
         System.out.println("2nd innings");
         team2Score = match.innings(10,team1, printOver, 2);
    }
    
    result();
    
    }
    
    
    public Vector<Player> assignTeam(Vector<Player> collection)                        //function to create 2 teams from given list of players
    {
    int  i = 0;
    int noOfPlayers = collection.size();
    Vector<Player> tempList = new Vector<Player>();
    
    while(i<7)
        {
            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);     //randomly assign an index of a player to put in a team
            if(!playerSet.contains(ind))                                    //if the set doesnot contain player list then add to the designated team
            {                                                               //else it has already been assigned a team
                playerSet.add(ind);
                tempList.add(collection.get(ind));
                i++;
            }
        }
    	return tempList;
    }
    
    
    public void print(Vector<Player> team)                     //function to print the list of players in each team
    {
    for(int i = 0;i < 7; i++)
        {
           System.out.print (team.get(i).name + " ");
        }
    System.out.println();
    }
    
    
    public void result()              //function to print the result of the match
    {
    	System.out.println("\n*****************************************************************");
    	System.out.println("FINAL RESULT");
    	System.out.println("*****************************************************************\n");

        if(team1.totalScore > team2.totalScore)
        {
            int winningRun = team1.totalScore - team2.totalScore;
            System.out.println( team1.name + " won by " + winningRun + " runs\n");
        }
        else if(team1.totalScore < team2.totalScore)
        {
            int winningWicket = 7 - team2.totalWickets;
            System.out.println( team2.name + " won by " + winningWicket + " wickets\n");
        }
        else
            System.out.println("Draw");
        
        displayTeamDetails(team1);
        displayTeamDetails(team2);
    }
    
    
    public void displayTeamDetails(Team team)
    {
    	int cntr = 1;
    	System.out.println("--------------------------------------------------");
    	System.out.println("\n		" + team.name + "\n");
    	for(int i=0;i<team.playerList.size();i++)
    	{	
    		System.out.print(cntr++ + "   ");
    		if(team.playerList.get(i).out == 1)
    			System.out.println(team.playerList.get(i).name + "			" +  team.playerList.get(i).runScored + "(" + team.playerList.get(i).ballsplayed +")");
    		else
    			System.out.println(team.playerList.get(i).name + "*" +"			" +  team.playerList.get(i).runScored + "(" + team.playerList.get(i).ballsplayed +")");
    		
       	}
    	System.out.println("\nTOTAL" + "				" + team.totalScore + "/" + team.totalWickets + "\n");
    	System.out.println("--------------------------------------------------");
    }
    

}
