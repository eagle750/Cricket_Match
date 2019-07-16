package Cricket;

import java.math.BigDecimal;
import java.math.RoundingMode;

import repo.CricketRepoMysqlImpl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CricketUtils {

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
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }


    public static void teamReset(Team team)
    {
        playerListReset(team.getPlayerList(),team.getName());
        team.setTotalScore(0);
        team.setTotalWickets(0);
    }


    public static void playerListReset(List<Player> playerList,String teamName)
    {
        CricketRepoMysqlImpl cricketRepoMysql =  new CricketRepoMysqlImpl();
        cricketRepoMysql.resetPlayerInfo(teamName);

        for(int i = 0;i < playerList.size(); i++)
        {
            playerList.get(i).setBallsPlayed(0);
            playerList.get(i).setFours(0);
            playerList.get(i).setOut(0);
            playerList.get(i).setRunScored(0);
            playerList.get(i).setSixes(0);
        }
    }
}
