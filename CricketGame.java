import java.util.*;

public class CricketGame {

	public static void main(String[] args) {
		Vector<String> temp = new Vector<String>(); ///temporary list

        String[] collection = {"Sachin", "Sehwag", "Yuvraj", "Saurav", "Bumrah", "Pandya", "Dhoni", "Kohli", "Rohit", "Raina", "Chahal", "Dhawan", "Shankar", "Jadeja", "Sami", "Robin"};                       //array containing list of player names

        for(int i = 0;i < collection.length;i++)        //adding the array elements to temp array
        {   
            temp.add(collection[i]);
        }

        Player play = new Player();
        play.setPlayerList(temp);               //using the setter function of Player class to set the player list

        MatchController  matchController  =  new MatchController();
        matchController.controller();           //invoking the controller method of MatchController class
     }

	}
