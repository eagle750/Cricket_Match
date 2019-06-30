import java.util.*;

public class CricketGame {

	public static void main(String[] args) {
		Vector<Player> temp = new Vector<Player>(); ///temporary list to store the list of players
		
		
        String[] collection = {"Sachin", "Sehwag", "Yuvraj", "Saurav", "Bumrah", "Pandya", "Dhoni", "Kohli", "Rohit", "Raina", "Chahal", "Dhawan", "Shankar", "Jadeja", "Sami", "Robin"};                       //array containing list of player names

        for(int i = 0;i < collection.length;i++)        //initializing the player list
        {   Player player = new Player(collection[i], 0, 0, 0, 0, 0);
            temp.add(player);
        }

        MatchController  matchController  =  new MatchController();
        matchController.controller("5.1", temp);           //invoking the controller method with initial playerList and and over 
        													//after which scoreboard is to be printed
     }

	}
