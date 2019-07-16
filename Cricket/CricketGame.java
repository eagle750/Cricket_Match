package Cricket;

public class CricketGame {

    public static void main(String[] args) {

        MatchController matchController =  new MatchController();
        Series series = new Series(5,"CSK","RCB");
        matchController.controller("2.1",MatchType.T10,series);
    }

	}

