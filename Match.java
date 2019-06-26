import java.util.concurrent.*;
import java.util.*;
//class containing function for conducting match between teams
public class Match {
	String outcomes[] = {"0", "1", "2", "3", "4", "5", "6", "W"};     //String array containing all the possible outcomes of a ball
	public int toss()                            //function to randomly perform the task of tossing the coin
	{
        int tossResult = ThreadLocalRandom.current().nextInt(2);    //uisng ThreadLocalRandom class
        if(tossResult == 0 )
        {   
            System.out.println("Team1 won the toss");
            System.out.println();
            return 1;
        }
        else
        {
            System.out.println("Team2 won the toss");
            System.out.println();
            return 2;
        }
    }
	public int innings(int overs, Vector<String> team)             //function to simulate the playing of a team with given overs and specified team
	{
        int totalRuns = 0;
        int totalWickets = 0;
        int player1 = 0;int player2 = 1;               //variables to store the index of players currently playing

       for(int i = 1;i <= overs;i++)
       {
            for(int j = 1;j <= 6;j++)
            {
                int run = ballingOutcome();
                if(run <= 7 )                     // here run represents the index of string array "outcomes" defined above in class
                {   
                    System.out.println("Over = " + i  +" ball = " + j + " run = " + run );
                    totalRuns += run;
                }
                else                                //else run = 8 which represents the "W" in "outcomes" array ,means a wicket is down
                {
                    System.out.println();
                    totalWickets++;
                    System.out.println("Player " + team.get(player1) + " is out");       //print the player name whose wicket has been taken
                    System.out.println();
                    
                    int nextPlayer = Math.max(player1, player2) + 1;

                    if(nextPlayer == 6)                          //this represents the situation when 6th player is down
                    {
                        System.out.println("All wicket down");
                        System.out.println();
                        return totalRuns;
                    }

                    player1  = nextPlayer;                      //replace the player whose wicket is down with a new player
                }
            }
            System.out.println();
            System.out.println("Players on the ground " + team.get(player1) + " and " + team.get(player2));   //print the players currently playing
            System.out.println();
            player1 = swap(player2, player2 = player1);                     //switch players at the end of each over
       }
        return totalRuns;                                   //return totalRuns scored by a team
    }
	public int ballingOutcome()   //function to randomly generate the outcome of balling from array "outcomes" defined above
    {
        int index;
        int tmp = ThreadLocalRandom.current().nextInt(9);
        index = tmp > 0 ?tmp : 0;
        return index;
    }

    public int swap(int value1, int value2)           //function to swap the players
    {
        return value1;
    }
    
	



}
