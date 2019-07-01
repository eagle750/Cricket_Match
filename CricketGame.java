import java.util.*;
import java.sql.*;

public class CricketGame {

	public static void main(String[] args) {
		Vector<Player> temp = new Vector<Player>(); ///temporary list to store the list of players
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
        String[] collection = {"Sachin", "Sehwag", "Yuvraj", "Saurav", "Bumrah", "Pandya", "Dhoni", "Kohli", "Rohit", "Raina", "Chahal", "Dhawan", "Shankar", "Jadeja", "Sami", "Robin"};                       //array containing list of player names
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root","Tekion@123");
        	
        	preparedStatement = con.prepareStatement("DELETE FROM Player");
        	preparedStatement.executeUpdate();
        	
            for(int i = 0;i < collection.length;i++)        //initialising the player list
            {
            	String str = collection[i];
            	preparedStatement = con.prepareStatement("INSERT INTO Player(PlayerName) VALUES('"+str+"')");
            	preparedStatement.executeUpdate();
            }
            preparedStatement = con.prepareStatement("DELETE FROM Team");
        	preparedStatement.executeUpdate();
        	
        	preparedStatement = con.prepareStatement("INSERT INTO Team(TeamName) VALUES('CSK')");
        	preparedStatement.executeUpdate();
        	
        	preparedStatement = con.prepareStatement("INSERT INTO Team(TeamName) VALUES('RCB')");
        	preparedStatement.executeUpdate();
        	
        	preparedStatement = con.prepareStatement("DELETE FROM BallsData");
        	preparedStatement.executeUpdate();
        	

        }
        catch(SQLException se){
        	se.printStackTrace();
        }
        catch(ClassNotFoundException e) {
        	e.getMessage();
        }
        finally {
        	try {
        		if(con!=null)
        			con.close();
        	}
        	catch(SQLException se){
        		se.printStackTrace();
        	}
        }
        
        MatchController  matchController  =  new MatchController();
        matchController.controller("5.1");           //invoking the controller method with initial playerList and and over 
        													//after which scoreboard is to be printed
     }

	}
