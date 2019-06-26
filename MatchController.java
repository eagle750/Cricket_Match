import java.util.*;
import java.util.concurrent.*;

public class MatchController {
	Vector<String> team1 = new Vector<>();                  //array to store the list of names for team1
    Vector<String> team2 =  new Vector<>();                 //array to store the list of names for team2
    Set<Integer> playerSet = new HashSet<Integer>();        //set to check if a player choosen has already been assigned a team

    Player player =  new Player();
    Vector<String> totalPlayers =  player.getPlayerList();          //get the list of total players available for playing
    private int noOfPlayers = totalPlayers.size();

    Random rand = new Random();

    public void controller()
    {
    assignTeam();                                   //function to assign andomly create 2 teams with 7 players each
    
    System.out.println("Team 1 members are:- \n");
    print(team1);
    System.out.println();
    System.out.println("Team 2 members are:- \n");
    print(team2);
    System.out.println();
    
    Match match =  new Match();
    int tossResult = match.toss();                          //invoking toss function

    int team1Score,team2Score;

    if(tossResult == 1)                                     //if tossResult is 1 team1 gets to play first else team2
    {

         System.out.println("First innings\n");
         team1Score = match.innings(10,team1);
         
         System.out.println("2nd innings");
         team2Score = match.innings(10,team2);
    }
    else
    {
         team1Score = match.innings(10,team2);
         team2Score = match.innings(10,team1);
    }
    result(team1Score,team2Score);
    }
    
    public void assignTeam()                        //function to create 2 teams from given list of players
    {
    int  i = 0;
    while(i<7)
        {
            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);     //randomly assign an index of a player to put in a team
            if(!playerSet.contains(ind))                                    //if the set doesnot contain player list then add to the designated team
            {                                                               //else it has already been assigned a team
                playerSet.add(ind);
                team1.add(totalPlayers.get(ind));
                i++;
            }
        }
    i = 0;
    while(i<7)                                                              //same team assignment for team 2
        {
            int ind = ThreadLocalRandom.current().nextInt(noOfPlayers);

            if(!playerSet.contains(ind))
            {
                playerSet.add(ind);
                team2.add(totalPlayers.get(ind));
                i++;
            }
        }
    }
    
    public void print(Vector<String> team)                     //function to print the list of players in each team
    {
    for(int i = 0;i < 7; i++)
        {
           System.out.print (team.get(i) + " ");
        }
    System.out.println();
    }
    
    public void result(int team1score, int team2score)              //function to print the result of the match
    {
        if(team1score > team2score)
        {
            int winningRun = team1score - team2score;
            System.out.println("team1 won by " + winningRun);
        }
        else if(team1score < team2score)
        {
            int winningRun = team2score - team1score;
            System.out.println("team2 won by " + winningRun);
        }
        else
            System.out.println("Draw");
    }
    

}
