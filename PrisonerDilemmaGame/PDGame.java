package smithpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PDGame {

    private ArrayList<Integer> userHistoryAL = new ArrayList<Integer>();   //Keeps user history
    private ArrayList<String> computerStrategiesAL = new ArrayList<String>();	 //string of each strategies
    private GameStat gsPtr = new GameStat();  //record of game stats
    private Scanner scan;    //Scanner
    private int strategy;    //strategy the computer is using

    //Constructor should initialize Scanner to read from text file
    public PDGame(String file)
     {
       //Computer strategies
       computerStrategiesAL.add("Computer Reads Strategy From Input File.");
       computerStrategiesAL.add("Tit-For-Tat.");
       computerStrategiesAL.add("Random Choice by Computer.");

       try  //try if file exists
        {
	      File f1 = new File(file);  //Open file
	      scan = new Scanner(f1);   //Point scanner at beginning of file
        }

       catch(FileNotFoundException e)  //otherwise catch exception
        {
	      System.out.println("\n EXCEPTION: FILE NOT FOUND");	//Display exception message
	      System.exit(0);
        }
     }

    //Generates computer's decision based on strategy selected
    public String playRound(int decision)
     {
       int user, computer;  //Stores prison sentence for user and computer
       int computerDecision = computerDecision();
       userHistoryAL.add(decision);  //Stores user decision in userHistoryAL

       //If both remain silent
       if(decision == 1 && computerDecision == 1)
        {
	  //both user and computer get 2 years in prison
          user = 2;
          computer = 2;
          gsPtr.update(user, computer);  //Update GameStat object
          return "\nYou and your partner remain silent." + "\nYou both get 2 years in prison.";
        }

       //Else if user testifies and computer remains silent
       else if(decision == 2 && computerDecision == 1)
        {
	  //User receives one year in prision and computer receives five
          user = 1;
          computer = 5;
          gsPtr.update(user,computer);  //Update GameStat object
          return "\nYou testify agaisnt your partner and they remain silent." + "\nYou get 1 year in prison and they get 5.";
        }

       //Else if user remains silent and computer testfies
       else if(decision == 1 && computerDecision == 2)
        {
          //User gets five years in prison and computer gets one
          user = 5;
          computer = 1;
          gsPtr.update(user,computer);  //Update GameStat object
          return "\nYou remain silent and they testify agaisnt you" + "\nYou get 5 years in prison and they get 1";
        }

       //Else if both testify
       else
        {
	  //Both user and computer get three years in prison
          user = 3;
          computer = 3;
          gsPtr.update(user,computer);  //Update GameStat object
          return "\nYou and your partner testify against each other" + "\nYou both get 3 years in prison";
        }
      }

    private int computerDecision()
     {
       if(strategy == 1)  //if user inputs a one read from a file
        {
          return scan.nextInt();
        }

       else if(strategy == 2)  //if user enters a two, first return a 1
        {
          return 1;
        }

       else if(strategy == 3)  //else generate a random number
        {
          return (int)(Math.random()*2+1);  //generates either a 1 or 2
        }

       else  //if user enters a two, first return a 1
        {
          int previousDecision = userHistoryAL.get(userHistoryAL.size() - 1);
          return previousDecision;
        }
    }

    //Returns an ArrayList of strings
    public ArrayList<String> getStrategies()
     {
       return computerStrategiesAL;
     }

    //Returns a string message indicating final scores
    public String getScores()
     {
      return "\n----Your prison sentence is: " + gsPtr.getuserSentence() + "\n----Your partner's/computer prison sentence is: " + gsPtr.getcompSentence();
     }

    //Returns the pointer to GameStat
    public GameStat getStats()
     {
       return gsPtr;
     }

    //Set method for the strategy data member
    public void setStrategy(int strategy)
     {
       //calls GameStat setter with the string obtained from ArrayList
       this.strategy = strategy;
       gsPtr.setCompStrategy(computerStrategiesAL.get(strategy - 1));
     }

} //End of class

