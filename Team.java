import java.util.*;

public class Team {

	
	private Vector<Player> playerList = new Vector<Player>();
	private String name;
	private int totalScore;
	private int totalWickets;
	
	Team(String name, Vector<Player> playerList,int totalScore, int totalWickets)
	{
		setName(name);
		setPlayerList(playerList);
		setTotalScore(totalScore);
		setTotalWickets(totalWickets);
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
	
	//getter functions
	public String getName()
	{
		return name;
	}
	
	public Vector<Player> getPlayerList()
	{
		return playerList;
	}
	
	public int getTotalScore()
	{
		return totalScore;
	}
	
	public int getTotalWickets()
	{
		return totalWickets;
	}
	
	//Modify function
	public void addTotalScore(int runs)
	{
		totalScore += runs;
	}
	
	public void addTotalWickets()
	{
		totalWickets += 1 ;
	}

}
