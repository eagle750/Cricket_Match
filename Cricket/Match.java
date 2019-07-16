package Cricket;

import repo.CricketRepoMysqlImpl;

import java.util.concurrent.*;
import java.text.DecimalFormat;
import java.util.logging.Level;

//class containing function for conducting match between teams
public class Match {

	private DecimalFormat df = new DecimalFormat("#.#");


	private CricketRepoMysqlImpl cricketRepoMysql = new CricketRepoMysqlImpl();

	public int doToss()                            //function to randomly perform the task of tossing the coin
	{
		int tossResult = ThreadLocalRandom.current().nextInt(2);    //using ThreadLocalRandom class
		return tossResult;
    }
	
	
	public void playInnings(int overs, Team team, String printOver, int innings,int Series_id,int matchNo) 		//function to simulate the playing of a team with given overs and specified team
	{
		int striker1 = 0; int striker2 = 1,flag = 0,counter = 0; double curOver;

		for(int i = 0;i < overs;i++)
	           {
	        	   curOver = (double)i;
	                for(int j = 1;j <= 6;j++)
	                {
	                	counter++;
	                	curOver += (double)1/10;
	                	int run  =  getRuns(team, striker1);

						cricketRepoMysql.updateBallInfo(counter,team,striker1,run,striker2,Series_id,matchNo);

	                    if(run < 7 )                     // here run represents the index of string array "outcomes" defined above in class
	                    {
	                    	flag = updateRunInfo(team, striker1, striker2, run, printOver, curOver, innings);
	                    	if(flag == 1)
	                    		break;
	                    }
	                    
	                    else                                										//else run = 8 which represents the "W" in "outcomes" array ,means a wicket is down
	                    {
	                    	updateWicketDownInfo(team, striker1, striker2, printOver, innings, curOver);

							int nextPlayer = Math.max(striker1, striker2) + 1;

							if(nextPlayer > 9)                          								//this represents the situation when 6th player is down
							{
								flag = 1;
								break;
							}
							striker1  = nextPlayer;                      									//replace the player whose wicket is down with a new player
						}
	                   CricketUtils.sleep();
	                }
	                if(flag == 1)
	                	break;

				   striker1 = CricketUtils.swap(striker2, striker2 = striker1);                     //switch players at the end of each over
	           }
		       cricketRepoMysql.updatePlayerInfo(team);
    }
	

    public int getRuns(Team team,int striker)
	{
		int run = CricketUtils.ballingOutcome();

		String playerType =  team.getPlayerList().get(striker).getPlayerType();

		if(run ==  7 && (playerType.equals("BATSMAN")|| playerType.equals("WICKETKEEPER") || playerType.equals("ALLROUNDER"))) {
			run = CricketUtils.ballingOutcome();

			if(run ==  7 && (playerType.equals("BATSMAN")|| playerType.equals("WICKETKEEPER") || playerType.equals("ALLROUNDER"))) {
				run = CricketUtils.ballingOutcome();
			}
		}
		return run;
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
    	 
    	if(run%2 == 1) {
    		striker1 = CricketUtils.swap(striker2, striker2 = striker1);				//if runs are odd then swap the players
    	}
        team.addTotalScore(run);  
                            
        if(innings == 2) {
        	if(MatchController.prevTeam.getTotalScore() < team.getTotalScore()) {
        		return 1;
        	}
        }
        if(df.format(over).equals(printOver))									//check if current over is equal to given over and call printScoreBoard
			PrintMatchDetails.ongoingScoreBoard(team, innings,over,striker1,striker2);
		return 0;
	}
	
	
	public void updateWicketDownInfo(Team team,int striker1,int striker2,String printOver,int innings,double curOver)
	{
		team.addTotalWickets();
        team.getPlayerList().get(striker1).modifyOut();

        if(df.format(curOver).equals(printOver))										//check if current over is equal to given over and call printScoreBoard
			PrintMatchDetails.ongoingScoreBoard(team, innings,curOver,striker1,striker2);
	}
}