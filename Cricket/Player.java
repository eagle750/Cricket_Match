package Cricket;

public class Player {
	
	 private String name;
	 private int playerId;
	 private int runScored;
	 private int ballsplayed;
	 private String playerType;
	 private int sixes;
	 private int fours;
	 private int out;
	
	public Player(String name, String playerType, int playerId)
	{
		setName(name);
		setPlayerType(playerType);
		setPlayerId(playerId);
	}

	private void setPlayerType(String playerType) { this.playerType = playerType;}

	//Setter functions
	public void setName(String name)
	{
		this.name = name;
	}

	public void setRunScored (int runScored)
	{
		this.runScored = runScored;
	}


	public void setBallsPlayed (int ballsPlayed)
	{
		this.ballsplayed = ballsPlayed;
	}

	public void setPlayerId (int playerId)
	{
		this.playerId = playerId;
	}

	public void setSixes (int sixes)
	{
		this.sixes = sixes;
	}

	public void setFours (int fours)
	{
		this.fours = fours;
	}

	public void setOut (int out)
	{
		this.out = out;
	}

	//getter function
	public String getName()
	{
		return name;
	}
	
	public int getPlayerId()
	{
		return playerId;
	}
	
	public int getRunScored ()
	{
		return runScored;
	}
	
	public int getBallsPlayed ()
	{
		return ballsplayed;
	}
	
	public int getSixes ()
	{
		return sixes;
	}

	public String getPlayerType ()
	{
		return playerType;
	}

	public int getFours ()
	{
		return fours;	
	}
	
	public int getOut ()
	{
		return out;
	}
	
	//modify function
	public void addRunScored (int run)
	{
		this.runScored += run;
	}
	
	public void addBallsPlayed ()
	{
		this.ballsplayed += 1;
	}
	
	public void addSixes ()
	{
		this.sixes += 1;
	}
	
	public void addFours ()
	{
		this.fours += 1;	
	}
	
	public void modifyOut ()
	{
		this.out += 1;
	}
		
}
