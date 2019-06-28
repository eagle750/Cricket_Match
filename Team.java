import java.util.*;

public class Team {

	
	Vector<Player> playerList = new Vector<Player>();
	String name;
	int totalScore;
	int totalWickets;
	
	Team(String name, Vector<Player> playerList,int totalScore, int totalWickets)
	{
		this.name = name;
		for(int i = 0; i < playerList.size(); i++)
		{
			this.playerList.add(playerList.get(i));
		}
		this.totalScore = totalScore;
		this.totalWickets = totalWickets;
	}
}
