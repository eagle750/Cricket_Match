package Cricket;

import java.util.*;

public class Team {

	
	private List<Player> playerList = new ArrayList<>();
	private String name;
	private int totalScore;
	private int totalWickets;
	private int tossWon;

	public Team(String name, List<Player> playerList,int totalScore, int totalWickets)
	{
		setName(name);
		setPlayerList(playerList);
		setTotalScore(totalScore);
		setTotalWickets(totalWickets);
	}
	
	//Setter Function
	public void setName(String name) { this.name = name; }

	public void setTossWon(int tossWon) {
		this.tossWon = tossWon;
	}

	public void setPlayerList(List<Player> playerList)
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
	
	public List<Player> getPlayerList()
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

	public int getTossWon() {
		return tossWon;
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
