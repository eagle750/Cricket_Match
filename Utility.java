import java.util.concurrent.ThreadLocalRandom;

public class Utility {
	
	public static int ballingOutcome()   //function to randomly generate the outcome of balling from array "outcomes" defined above
    {
        int index;
        int tmp = ThreadLocalRandom.current().nextInt(9);
        index = tmp > 0 ?tmp : 0;
        return index;
    }

	
    public static int swap(int value1, int value2)           //function to swap the players
    {
        return value1;
    }

}
