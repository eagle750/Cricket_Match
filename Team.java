import java.util.*;

public class Team {

	
	Vector<Player> playerList = new Vector<Player>();
	String name;
	int totalScore;
	int totalWickets;
	int toBat;
	
	Team(String name, Vector<Player> playerList,int totalScore, int totalWickets,int toBat)
	{
		setName(name);
		setPlayerList(playerList);
		setTotalScore(totalScore);
		setTotalWickets(totalWickets);
		setToBat(toBat);
	}
	
	//Setter Function
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setPlayerList(Vector<Player> playerList)
	{
		for(int i = 0; i < playerList.size(); i++)
		{
			this.playerList.add(playerList.get(i));
		}
	}
	
	public void setTotalScore(int totalScore)
	{
		this.totalScore = totalScore;
	}
	
	public void setTotalWickets(int totalWickets)
	{
		this.totalWickets = totalWickets;
	}
	
	public void setToBat(int toBat)
	{
		this.toBat = 0;
	}
}
