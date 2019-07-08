import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;

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


    public static void sleep() {                //sleep utility method

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
