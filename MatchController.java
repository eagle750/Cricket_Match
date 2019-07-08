import java.util.*;
import java.util.concurrent.*;
import java.sql.*;

public class MatchController {
	
	
	Vector<Player> team1Players = new Vector<Player>();                  //array to store the list of names for team1
    Vector<Player> team2Players =  new Vector<Player>();                 //array to store the list of names for team2
    Set<Integer> playerSet = new HashSet<Integer>();        //set to check if a player chosen has already been assigned a team

    static Team prevTeam  = null;
    Team team1,team2;

    public void controller(String printOver)
    {   
    	
    team1Players = DbHelper.assignTeam("CSK",playerSet);                                   //function to assign randomly create 2 teams with 7 players each
    team1 = new Team("CSK", team1Players, 0, 0);

    team2Players = DbHelper.assignTeam("RCB",playerSet);
    team2 = new Team("RCB", team2Players, 0, 0);

    String str = "Team 1 members are:- \n";
    Display.printPlayerDetails(str, team1Players);
    System.out.println();

    str = "Team 2 members are:- \n";
    Display.printPlayerDetails(str, team2Players);
    System.out.println();
    
    Match match =  new Match();
    int tossResult = match.doToss();                          //invoking toss function

    int team1Score,team2Score;

    if(tossResult == 1)                                     //if tossResult is 1 team1 gets to play first else team2
    {

         str = "1st innings\n";
         team1Score = match.innings(str,10,team1, printOver, 1 );
         prevTeam = team1;

         str = "2nd innings\n";
         team2Score = match.innings(str,10,team2, printOver, 2);
         DbHelper.updateMatchStatus("completed");
    }
    
    else
    {
         str = "1st innings\n";
         team1Score = match.innings(str,15,team2, printOver, 1);
         prevTeam = team2;

        str = "2nd innings\n";
        team2Score = match.innings(str,15,team1, printOver, 2);
         DbHelper.updateMatchStatus("completed");

    }
    
    Display.finalScoreBoard();
    
    }
    

    
}