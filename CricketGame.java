import java.util.*;

public class CricketGame {

	public static void main(String[] args) {

        String[] collection = {"Sachin", "Sehwag", "Yuvraj", "Saurav", "Bumrah", "Pandya", "Dhoni", "Kohli", "Rohit", "Raina", "Chahal", "Dhawan", "Shankar", "Jadeja", "Sami", "Robin"};                       //array containing list of player names
        
        DbHelper.insertInitialInfo(collection);
        
        MatchController  matchController  =  new MatchController();
        matchController.controller("2.1");           //invoking the controller method with initial playerList and and over
        													//after which scoreboard is to be printed
     }

	}
