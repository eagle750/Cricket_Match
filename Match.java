import java.util.concurrent.*;
import java.text.DecimalFormat;
import java.util.*;

//class containing function for conducting match between teams
public class Match {
	String outcomes[] = {"0", "1", "2", "3", "4", "5", "6", "W"};     //String array containing all the possible outcomes of a ball
	
	DecimalFormat df = new DecimalFormat("#.#");
	
    int player1 = 0;int player2 = 1;double Over = 0;               //variables to store the index of players currently playing
	
    
	public int toss()                            //function to randomly perform the task of tossing the coin
	{
        int tossResult = ThreadLocalRandom.current().nextInt(2);    //using ThreadLocalRandom class
        if(tossResult == 0 )
        {   
            System.out.println("CSK won the toss and chose to bat\n");
            return 1;
        }
        else
        {
            System.out.println("RCB won the toss and chose to bat\n");
            return 2;
        }
    }
	
	
	public int innings(int overs, Team team, String printOver, int innings)             //function to simulate the playing of a team with given overs and specified team
	{
        player1 = 0; player2 = 1; Over = 0;               //variables to store the index of players currently playing
        boolean inningCompleted  = false;  
         
         
       for(int i = 1;i <= overs;i++)
       {
    	   Over = (double)i;
            for(int j = 1;j <= 6;j++)
            {
            	Over += (double)1/10;
                int run = ballingOutcome();
                
                if(run <= 7 )                     // here run represents the index of string array "outcomes" defined above in class
                {   
                	 team.playerList.get(player1).ballsplayed += 1;
                	 team.playerList.get(player1).runScored += run;
                	 
                	 if(run == 4)									//to count the no of fours hit by batsman
                		 team.playerList.get(player1).fours += 1;
                	 
                	 if(run == 6)
                		 team.playerList.get(player1).sixes += 1;
                	 
                	if(run%2 == 1)
                	{
                		player1 = swap(player2, player2 = player1);				//if runs are odd then swap the players
                	}
                	
                    team.totalScore += run;  
                                        
                    if(innings == 2)
                    {
                    	
                    	if(MatchController.prevTeam.totalScore < team.totalScore)
                    	{
                    		inningCompleted = true ;
                    		break;
                    	}
                    }
                    if(df.format(Over).equals(printOver))				//check if current over is equal to given over and call printScoreBoard
                    	printScoreBoard(team, printOver, innings);
                    
                    System.out.println("Over = " + df.format(Over) + " runHit = " + run + " 	Score =" +team.totalScore + "/" + team.totalWickets);
                }
                else                                //else run = 8 which represents the "W" in "outcomes" array ,means a wicket is down
                {
                    team.totalWickets++;
                    team.playerList.get(player1).out = 1;
                    System.out.println("\nPlayer " + team.playerList.get(player1).name + "  -  " + team.playerList.get(player1).runScored +" is out\n");       //print the player name whose wicket has been taken
                   
                    if(df.format(Over).equals(printOver))					//check if current over is equal to given over and call printScoreBoard
                    	printScoreBoard(team, printOver, innings);
                    
                    int nextPlayer = Math.max(player1, player2) + 1;

                    if(nextPlayer == 7)                          //this represents the situation when 6th player is down
                    {
                        System.out.println("All wicket down\n");
                        return team.totalScore;
                    }

                    player1  = nextPlayer;                      //replace the player whose wicket is down with a new player
                }
            }
            
            if(inningCompleted == true)
            	break;
            System.out.println("\nPlayers on the ground " + team.playerList.get(player1).name + "  -  " + team.playerList.get(player1).runScored +" and " + team.playerList.get(player2).name + "  -  " + team.playerList.get(player2).runScored + "\n");   //print the players currently playing
            
            player1 = swap(player2, player2 = player1);                     //switch players at the end of each over
       }
        return team.totalScore;                                   //return totalRuns scored by a team
    }
	
	
	public void printScoreBoard(Team team, String printOver, int innings)    //function to print the scoreboard for given team and given over
	{
		System.out.println("************************************************************");
		System.out.println("				SCOREBOARD				");
		System.out.println("************************************************************\n");
		System.out.println(innings + " innings" + " " + team.name +" is batting\n");
		if(innings == 2)
			System.out.println( MatchController.prevTeam.name +  "  scored -  " + MatchController.prevTeam.totalScore);
		System.out.println("Over - " + df.format(Over));
		System.out.println( team.playerList.get(player1).name + "*" + "      - " + team.playerList.get(player1).runScored + "   4's - " + team.playerList.get(player1).fours + "   6's - " + team.playerList.get(player1).sixes );
		System.out.println( team.playerList.get(player2).name + "      - " + team.playerList.get(player2).runScored + "   4's - " + team.playerList.get(player2).fours + "   6's - " + team.playerList.get(player2).sixes );

		System.out.println("************************************************************\n");
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
