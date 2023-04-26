package smithpack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PDGameGui extends JFrame implements ActionListener, ListSelectionListener {
    
    //text fields
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> finishedGamesList;
    private final JTextField computerStrategyTF = new JTextField(10);
    private final JLabel computerStrategyL = new JLabel("Computer Strategy-Combo Box");
    private final JButton startB = new JButton("Start New Game");
    private final JComboBox<Object> computerStrategyCB;
    private int computerStrategy = 1;
    private final JLabel decisionL = new JLabel("Your decision this round?");
    private final JTextArea gameResultsTA = new JTextArea(15, 30);
    private PDGame currentPDGame = null;
    private String gameStartTime = null;
    private final HashMap<String, GameStat> stats = new HashMap<>();
    private final JLabel label1 = new JLabel("Rounds Played");
    private final JTextField roundsTF = new JTextField(10);
    private final JLabel label2 = new JLabel("Computer Strategy");
    private final JLabel label3 = new JLabel("Player Sentence");
    private final JTextField playerSentenceTF = new JTextField(10);
    private final JLabel label4 = new JLabel("Computer Sentence");
    private final JTextField computerSentenceTF = new JTextField(10);
    private final JLabel label5 = new JLabel("Winner");
    private final JTextField winnerTF = new JTextField(10);
    private final JButton silentB = new JButton("Remain Silent");
    private final JButton betrayB = new JButton("Testify");
    private final int GAME_LIMIT = 5;

    public static void main(String[] args) 
     {
        //call method to create and show GUI 
        createAndShowGUI();
     }

    public static void createAndShowGUI() 
     {
        //Create and set up the window
        PDGameGui pdg1 = new PDGameGui(); //call constructor below to set the window to user
        pdg1.addListeners();     //method will add listeners to buttons
        pdg1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pdg1.pack(); //pack all panels
        pdg1.setVisible(true); //display
     } 

    //Constructor to build the swing interface
    public PDGameGui() 
     {
        super("Prisoner's Dilemma"); //fills in the menu are of jframe with message
        currentPDGame = new PDGame("input.txt"); //get file for input strategy
       
        Color c1 = new Color(255, 175, 175);  //higher numbers means lighter colors
        Color c2 = new Color(0, 225, 225);
      
        super.setLayout(new BorderLayout());
        
        //Set up left panel
        JPanel panel1 = new JPanel(new BorderLayout());
        super.add(panel1, BorderLayout.WEST); //add to frame
        
        //Set up top left "list" and put it in a scroll pane for scrolling    
        finishedGamesList = new JList<>(listModel);
        finishedGamesList.setFont(new Font("SansSerif", Font.BOLD, 24));
        finishedGamesList.setVisibleRowCount(10);
        finishedGamesList.setFixedCellWidth(500);
        finishedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel1.add(new JScrollPane(finishedGamesList), BorderLayout.NORTH);
        panel1.setBackground(c2); //set background color
        
        //Set up Bottom Left panel
        JPanel panel2 = new JPanel();  
        
        //Set layout grid
        panel2.setLayout(new GridLayout(5, 2, 5, 5));
        
        //add required labels
        panel2.add(label1);
        panel2.add(roundsTF);
        roundsTF.setEditable(false);  //Set to false: prevent user from entering data
        
        //add required labels
        panel2.add(label2);
        panel2.add(computerStrategyTF);
        computerStrategyTF.setEditable(false);  //Set to false: prevent user from entering data
        
        //add required labels
        panel2.add(label3);
        panel2.add(playerSentenceTF);
        playerSentenceTF.setEditable(false);  //Set to false: prevent user from entering data
        
        //add required labels
        panel2.add(label4);
        panel2.add(computerSentenceTF);
        computerSentenceTF.setEditable(false);  //Set to false: prevent user from entering data
        
        //add required labels
        panel2.add(label5);
        panel2.add(winnerTF);  //add winner
        winnerTF.setEditable(false);  //Set to false: prevent user from entering data
        
        panel2.setBackground(c2);  //set the background
        
        //Add panel to the left
        panel1.add(panel2, BorderLayout.SOUTH);
        
        //Set up title border
        TitledBorder title;
        title = BorderFactory.createTitledBorder("List of Games");  //create title border
        panel1.setBorder(title);  //set title border
        
        //Set up panel on right side
        JPanel panel3 = new JPanel(new BorderLayout());
        super.add(panel3, BorderLayout.EAST); //add to frame
        
        // set up panel 4 to get strategy from the user and to start a game, and to select user decision
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(2, 1));
        
        //Set up panel5 goes on panel4
        JPanel panel5 = new JPanel(new FlowLayout());
        panel5.add(computerStrategyL);  //add computer strategy
        
        //Combo box with computer strategies 
        Object[] strategyArray = currentPDGame.getStrategies().toArray(); //convert AL to array
        computerStrategyCB = new JComboBox<>(strategyArray);  //place array in combo box
        computerStrategyCB.setEditable(false);
        computerStrategyCB.setSelectedIndex(0); //this sets starting value to first string in array
        
        //Add combo box and button
        panel5.add(computerStrategyCB);
        panel5.add(startB);    
        panel5.setBackground(c1); //Set background
        
        //Set up buttons
        JPanel panel6 = new JPanel(new FlowLayout());
        panel6.add(decisionL); //User decision
        panel6.setBackground(c2);  //set background
        panel6.add(silentB);
        panel6.add(betrayB);

        //Add panel5 and panel6 to panel4
        panel4.add(panel5);
        panel4.add(panel6);
        panel3.add(panel4, BorderLayout.NORTH);

        //Add text area to bottom right
        panel3.add(new JScrollPane(gameResultsTA), BorderLayout.SOUTH);  //Get game results
        gameResultsTA.setEditable(false);
        betrayB.setEnabled(false);
        silentB.setEnabled(false); 
        startB.setEnabled(true);  //start a new game
     } //end of constructor

    //hooks up listeners to buttons
    public void addListeners() 
     {
        startB.addActionListener(this);
        silentB.addActionListener(this);
        betrayB.addActionListener(this);
        
        computerStrategyCB.addActionListener(this);  //adds ActionListener() to combo box
        finishedGamesList.addListSelectionListener(this); //the JLIST event listener code is addListSelectionListener
     }

    public void actionPerformed(ActionEvent e) 
     {
    	if(e.getSource() == startB) 
    	 {
    	   startGame();  //start new game
    	 } 
		
    	else if(e.getSource() == silentB) 
    	 {
    	   cooperate();  //users decision is to cooperate 
    	 } 
		
    	else if(e.getSource() == betrayB) 
    	 {
    	   betray();  //users decision is to betray 
    	 } 
		
    	else if(e.getSource() == computerStrategyCB) //when user chooses an item in combo box
    	 { 
    	   computerStrategy = computerStrategyCB.getSelectedIndex() + 1; //get appropriate strategy
    	 }
     }

    public void valueChanged(ListSelectionEvent e) 
     {
    	String searchKey;
        
        //If the list is not empty, get value of key, and display associated statistics from GameStat
        if (!finishedGamesList.isSelectionEmpty()) 
         {
            searchKey = (String) finishedGamesList.getSelectedValue(); //get out time of game and look up in hash map
            GameStat gameStatsInfo;
            gameStatsInfo = stats.get(searchKey);
            
            //get number of rounds played
            roundsTF.setText(Integer.toString(gameStatsInfo.getRounds()));
            int playerSentenceYrs = gameStatsInfo.getuserSentence();
            
            //set player sentence
            playerSentenceTF.setText(String.format("%d %s", playerSentenceYrs,
                    ((playerSentenceYrs > 1) ? "years" : "year")));
            int compSentenceYrs = gameStatsInfo.getcompSentence(); 
            
            //set computer sentence
            computerSentenceTF.setText(String.format("%d %s", compSentenceYrs,
                    ((compSentenceYrs > 1) ? "years" : "year")));
            String compStrategy = gameStatsInfo.getCompStrategy(); 
            
            //format and get computer strategy 
            computerStrategyTF.setText(String.format("%s", compStrategy));
            //get and set winner
            String win = gameStatsInfo.getWinner();
            winnerTF.setText(String.format("%s", win)); 
         }
     }

    public void startGame() 
     {
        currentPDGame = new PDGame("input.txt"); //input strategy file
        currentPDGame.setStrategy(computerStrategy);
        gameStartTime = (new Date()).toString(); //this is used as a key in the hashmap
        
        gameResultsTA.append("***Prisoner's Dilemma***\n");
        
        silentB.setEnabled(true);
        betrayB.setEnabled(true); //user must not be able to press the New Game button
        startB.setEnabled(false);
        
        promptPlayer();   //Prompt player for their decision
     }

    public void cooperate() 
     {
        String roundResult = currentPDGame.playRound(1); //get round result
        gameResultsTA.append(roundResult + "\n");
        
        //if round is equal to 5, end game
        if(currentPDGame.getStats().getRounds() >= GAME_LIMIT) 
         { 
            endGame();
         } 

        //else prompt player for their decision
        else 
         {
            promptPlayer();             
         }
     }

    public void betray() 
     {
        String roundResult = currentPDGame.playRound(2); //get round result
        gameResultsTA.append(roundResult + "\n");

        //if round is equal to 5, end game
        if(currentPDGame.getStats().getRounds() >= GAME_LIMIT) 
         {  
            endGame();
         }
        
        //else prompt player for their decision
        else 
         {
            promptPlayer();                 
         }
     }

    private void promptPlayer() 
     { //prompt player for their decision
        String promptString = "\n1. Cooperate and remain silent." + "\n" + "2. Betray and testify against." + "\n\n" + "What is your decision this round?\n\n";
        gameResultsTA.append(promptString); //append the string to the TextArea
     }

    private void endGame() 
     {
        String endScores = currentPDGame.getScores();
        gameResultsTA.append(endScores + "\n");  //display scores on text area
        stats.put(gameStartTime, currentPDGame.getStats());  //update hash map
        listModel.addElement(gameStartTime);  //update list
        
        betrayB.setEnabled(false);
        silentB.setEnabled(false);  //user should only be able to press new game button at end of game
        startB.setEnabled(true);
     }
}  // end of class

