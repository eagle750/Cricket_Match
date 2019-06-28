import java.util.*;
import java.util.concurrent.*;

public class MatchController {
	Vector<Player> team1Players = new Vector<Player>();                  //array to store the list of names for team1
    Vector<Player> team2Players =  new Vector<Player>();                 //array to store the list of names for team2
    Set<Integer> playerSet = new HashSet<Integer>();        //set to check if a player choosen has already been assigned a team

    static int prevTeamScore = 0;
    Team team1;
    Team team2;
    
    public void controller(String printOver, Vector<Player> collection)
    {
    assignTeam(collection);                                   //function to assign andomly create 2 teams with 7 players each
    
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
         team1Score = match.innings(10,team1, printOver, "1st innings");
         prevTeamScore = team1Score;
         
         System.out.println("2nd innings\n");
         team2Score = match.innings(10,team2, printOver, "2nd innings");
    }
    
    else
    {
         System.out.println("First innings\n");
         team1Score = match.innings(10,team2, printOver, "1st innings");
         prevTeamScore = team1Score;
         
         System.out.println("2nd innings");
         team2Score = match.innings(10,team1, printOver, "2nd innings");
    }
    
    result();
    
    }
    
    public void assignTeam(Vector<Player> collection)                        //function to create 2 teams from given list of players
    {
    int  i = 0;
    int noOfPlayers = collection.size();
    
    while(i<7)
        {
            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);     //randomly assign an index of a player to put in a team
            if(!playerSet.contains(ind))                                    //if the set doesnot contain player list then add to the designated team
            {                                                               //else it has already been assigned a team
                playerSet.add(ind);
                team1Players.add(collection.get(ind));
                i++;
            }
        }
    team1 = new Team("CSK", team1Players, 0, 0);
    
    i = 0;
    while(i<7)                                                              //same team assignment for team 2
        {
            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);

            if(!playerSet.contains(ind))
            {
                playerSet.add(ind);
                team2Players.add(collection.get(ind));
                i++;
            }
        }
    team2 = new Team("RCB", team2Players, 0, 0);
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
    	System.out.println("*****************************************************************");
    	System.out.println("FINAL RESULT");
    	System.out.println("*****************************************************************\n");

        if(team1.totalScore > team2.totalScore)
        {
            int winningRun = team1.totalScore - team2.totalScore;
            System.out.println( team1.name + " won by " + winningRun + " runs");
        }
        else if(team1.totalScore < team2.totalScore)
        {
            int winningRun = team2.totalScore - team1.totalScore;
            int winningWicket = 7 - team2.totalWickets;
            System.out.println( team2.name + " won by " + winningWicket + " wickets");
        }
        else
            System.out.println("Draw");
    }
    

}
