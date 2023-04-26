package smithpack;

public class GameStat {

  String compStrategy;
  private int userSentence, compSentence;  //Tracks player and computer sentence/years
  private int rounds;  //Tracks number of rounds played

  //GameStat constructor
  public GameStat()
   {
     userSentence = 0; //set user, computer, and rounds to zero
     compSentence = 0;
     rounds = 0;
   }

  //Increments the stats and is called from pdgame
  public void update(int userSentence, int compSentence)
   {
     //update the userSentence, compSentence, and rounds played
     this.userSentence += userSentence;
     this.compSentence += compSentence;
     rounds++;
   }

  //Returns the winner of the game
  public String getWinner()
   {
     //Compare the years of player and computer
     if(userSentence < compSentence) //Player wins
      {
        return "player.";
      }

     else if (userSentence > compSentence) //Computer wins
      {
        return "computer.";
      }

     else  //It is a tie
      {
        return "Tie.";
      }
   }

  public int getuserSentence()
   {
     return userSentence;  //get the user prison sentence
   }

  public int getcompSentence()
   {
     return compSentence;  //get the computer prison sentence
   }

  public void setCompStrategy(String compStrategy)
   {
     this.compStrategy = compStrategy;  //set the computer startegies
   }

  //Returns the string strategy computer used in the game
  public String getCompStrategy()
   {
     return compStrategy;  //get the computer strategies
   }

  public int getRounds()
   {
     return rounds;	//get rounds
   }
} //End of class
