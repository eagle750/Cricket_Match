public class Player {
	
	 String name;
	 int runScored;
	 int ballsplayed;
	 int sixes;
	 int fours;
	 int out;
	
	Player(String name, int runScored, int ballsPlayed, int sixes, int fours, int out)
	{
		setName(name);
		setRunScored(runScored);
		setRunScored(ballsPlayed);
		setSixes(sixes);
		setFours(fours);
		setOut(out);
	}	
	
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
	
}
