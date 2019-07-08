import java.util.concurrent.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

//class containing function for conducting match between teams
public class Match {

	private final static Logger LOGGER =
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	DecimalFormat df = new DecimalFormat("#.#");

	String outcomes[] = {"0", "1", "2", "3", "4", "5", "6", "W"};     //String array containing all the possible outcomes of a ball	
    
	
	public int doToss()                            //function to randomly perform the task of tossing the coin
	{
		LOGGER.setLevel(Level.WARNING);
        int tossResult = ThreadLocalRandom.current().nextInt(2);    //using ThreadLocalRandom class
        if(tossResult == 0 )
        {
        	LOGGER.log(Level.INFO,"CSK won the toss and chose to bat\n");
            DbHelper.updateTeamInfo("CSK", "RCB");
            return 1;
        }
        else
        {
			LOGGER.log(Level.INFO,"RCB won the toss and chose to bat\n");
            DbHelper.updateTeamInfo("RCB", "CSK");
            return 2;
        }
    }
	
	
	public int innings(String str,int overs, Team team, String printOver, int innings)             //function to simulate the playing of a team with given overs and specified team
	{
		LOGGER.setLevel(Level.WARNING);
		LOGGER.log(Level.INFO,str);

	    int striker1 = 0;int striker2 = 1,flag = 0,counter = 0;double curOver = 0;               //variables to store the index of players currently playing
	           for(int i = 0;i < overs;i++)
	           {
	        	   curOver = (double)i;
	                for(int j = 1;j <= 6;j++)
	                {
	                	counter++;
	                	curOver += (double)1/10;
	                    int run = Utility.ballingOutcome();
	                    
                    	DbHelper.updateBallInfo(counter,team,striker1,run,striker2);

	                    if(run < 7 )                     // here run represents the index of string array "outcomes" defined above in class
	                    {   
	                    	flag = updateRunInfo(team, striker1, striker2, run, printOver, curOver, innings);
	                    	if(flag == 1)
	                    		break;
	                    }
	                    
	                    else                                										//else run = 8 which represents the "W" in "outcomes" array ,means a wicket is down
	                    {
	                    	int val = updateWicketDownInfo(team, striker1, striker2, printOver, innings, curOver);
	                    	if(val != -1)
	                    		return val;

							int nextPlayer = Math.max(striker1, striker2) + 1;
							striker1  = nextPlayer;                      									//replace the player whose wicket is down with a new player
						}

	                    Utility.sleep();
	                }
	                
	                if(flag == 1)
	                	break;
				   LOGGER.log(Level.INFO,"Players on the ground " + team.getPlayerList().get(striker1).getName() + "  -  " + team.getPlayerList().get(striker1).getRunScored() +" and " + team.getPlayerList().get(striker2).getName() + "  -  " + team.getPlayerList().get(striker2).getRunScored() + "\n");

	                striker1 = Utility.swap(striker2, striker2 = striker1);                     //switch players at the end of each over	              
	                
	           }
	          
               DbHelper.updatePlayerInfo(team);
               
	           return team.getTotalScore();                                           //return totalRuns scored by a team
    }
	
	
	public int updateRunInfo(Team team,int striker1,int striker2, int run,String printOver,double over, int innings)
	{
		team.getPlayerList().get(striker1).addBallsPlayed();
        
    	team.getPlayerList().get(striker1).addRunScored(run);
    	 
    	 if(run == 4) {                            //to count the no of fours hit by batsman
			 team.getPlayerList().get(striker1).addFours();
		 }
    	 
    	 if(run == 6) {
			 team.getPlayerList().get(striker1).addSixes();
		 }
    	 
    	if(run%2 == 1)
    	{
    		striker1 = Utility.swap(striker2, striker2 = striker1);				//if runs are odd then swap the players
    	}
    	
        team.addTotalScore(run);  
                            
        if(innings == 2)
		{
        	if(MatchController.prevTeam.getTotalScore() < team.getTotalScore())
        	{
        		return 1;
        	}
        }
        if(df.format(over).equals(printOver))									//check if current over is equal to given over and call printScoreBoard
        	Display.ongoingScoreBoard(team, innings,over,striker1,striker2);
		LOGGER.log(Level.INFO, "Over = " + df.format(over) + " runHit = " + run + " 	Score =" +team.getTotalScore() + "/" + team.getTotalWickets());
		return 0;
	}
	
	
	public int updateWicketDownInfo(Team team,int striker1,int striker2,String printOver,int innings,double curOver)
	{
		team.addTotalWickets();
        team.getPlayerList().get(striker1).modifyOut();
		LOGGER.log(Level.INFO,"Player " + team.getPlayerList().get(striker1).getName() + "  -  " + team.getPlayerList().get(striker1).getRunScored() +" is out\n");

        if(df.format(curOver).equals(printOver))										//check if current over is equal to given over and call printScoreBoard
        	Display.ongoingScoreBoard(team, innings,curOver,striker1,striker2);

		int nextPlayer = Math.max(striker1, striker2) + 1;
		if(nextPlayer == 7)                          								//this represents the situation when 6th player is down
        {
        	LOGGER.log(Level.INFO,"All wicket down\n");

            DbHelper.updatePlayerInfo(team);

            return team.getTotalScore();
        }
        return -1;
	}
}