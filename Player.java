import java.util.*;


public class Player {
	
	static  Vector<String> players =  new Vector<String>();  //Array containing all the players
	public void setPlayerList(Vector<String>  players)        //Setter function to initialize the playerList
	{   
        for(int i=0;i<players.size();i++)
        this.players.add(players.get(i));
    }
	public Vector<String> getPlayerList()             //Getter function to return playerList
    {   
        return players;
    }


}
