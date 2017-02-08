/**
 * @author 		Andre Rampersad
 * Project: 	Black Jack Game
 * Course:		PROG24178 - Java 2
 * Instructor: 	Robert Lederer
 * File: 		AndreBlackJackGame.java
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class AndreBlackJackGame extends JFrame{
	boolean autoPlay = false;
        //boolean autoPlay = false;
	
	int playerWinCount = 0;
	int dealerWinCount = 0;
	int tieGame = 0;
	
	//Declares constants in seconds for time between plays for player and dealer.  
	final short DEALER_PLAY_INTERVAL_SECONDS = 5;
	final short PLAYER_PLAY_INTERVAL_SECONDS = 3;
	final short TIMER_INTERVAL_MILLISECONDS = 100;//1000;
	
	//Create deck of cards.
	AndreCards deck = new AndreCards();
	
	//Array lists to keep track of the individual card numbers assigned to player and dealer.
	ArrayList<Short>playerCardList = new ArrayList<Short>();
	ArrayList<Short>dealerCardList = new ArrayList<Short>();
	
	//Variables to store the scores for player and dealer.
	private short playerScore;
	private short dealerScore;
	
	//Variable to store the card number randomly generated from available cards in the deck.
	private short randomCardNumber;
	
	//Create panel jpDealer for the buttons and set FlowLayout
    private JPanel jpDealerLabels = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    private JLabel jlDealerScore = new JLabel(String.valueOf(dealerScore));
    
	//panel for dealer cards
    private JPanel jpDealerCards = new JPanel(new FlowLayout(FlowLayout.LEFT,1,0));
    private JLabel jlDealerPlaceholder = new JLabel(deck.getCardImage(0));
	
    //panel for player labels
    private JPanel jpPlayerLabels = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    private JLabel jlPlayerScore = new JLabel(String.valueOf(playerScore));
	
    //panel for player cards
    private JPanel jpPlayerCards = new JPanel(new FlowLayout(FlowLayout.LEFT,1,0));
	
    //panel for buttons and timer
    private JPanel jpPlayerControls = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    private JButton jbtHit = new JButton("Hit");
    private JButton jbtStay = new JButton("Stay");
  //Creates the play again button.
    JButton jbtPlayAgain = new JButton("New Game");
    private JLabel jlTimerCountdown = new JLabel();
    
    //Attempting to use timers unsuccessfully.  Will see if I can get it to work.
    Timer tmrDealerPlay = new Timer(TIMER_INTERVAL_MILLISECONDS, new DealerPlayDelayListener());
    
    //Assigns constant value for time between dealer drawing  a card to a variable.
    short timerCount = DEALER_PLAY_INTERVAL_SECONDS;
    
	public AndreBlackJackGame(){
		
	    //Adds dealer labels to panel
	    jpDealerLabels.add(new JLabel("Dealer: "));
	    jpDealerLabels.add(jlDealerScore);
	    
	    //Adds player labels to panel
	    jpPlayerLabels.add(new JLabel("Player: "));
	    jpPlayerLabels.add(jlPlayerScore);
	    
	    //Adds player controls and labels to panel
	    jpPlayerControls.add(jbtStay);
	    jpPlayerControls.add(jbtHit);
	    //jpPlayerControls.add(new JLabel("Next move in: "));
	    jpPlayerControls.add(jlTimerCountdown);
	    //jpPlayerControls.add(new JLabel(" seconds."));
	    jpPlayerControls.add(jbtPlayAgain);
	    
	    //Adds all panels to frame.
	    add(jpDealerLabels);
	    add(jpDealerCards);
	    add(jpPlayerLabels);
	    add(jpPlayerCards);
	    add(jpPlayerControls);
	    
	    //Initializes a new game by calling the new game method.
	    newGame();
	    
	    //Action listener for hit button.
	    jbtHit.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		short randomCardNumber = randomCard();
	    		timerCount = PLAYER_PLAY_INTERVAL_SECONDS;
	    		tmrDealerPlay.start();
	    		
	    		jpPlayerCards.add(new JLabel(deck.getCardImage(randomCardNumber)));
	    		jpPlayerCards.repaint();
	    		
	    		playerCardList.add(randomCardNumber);
	    	    updateScores(playerCardList);
	    		}
	    	}
	    );
	    
	  //Action listener for stay button.
	    jbtStay.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		//Starts timer for dealer play.
	    		tmrDealerPlay.start();
	    		
	    		//Sets the predefined time given between dealer plays.
	    		timerCount = DEALER_PLAY_INTERVAL_SECONDS;
	    		
	    		//Hides the hit button once dealer starts playing.
	    		jbtHit.setVisible(false);
	    		
	    		//Renames the hit button to "Deal Now"
	    		//This was done in order for the user to skip the 3 second timer delay if they wish.
	    		jbtStay.setText("Deal Now");
	    		
	    		//Removes the face-down dealer place-holder card.
	    		jpDealerCards.remove(jlDealerPlaceholder);
	    		
	    		//Generates a random available card from the deck
	    		randomCardNumber = randomCard();
	    		jpDealerCards.add(new JLabel(deck.getCardImage(randomCardNumber)));
	    		
	    		//Displays the new card in the panel.
	    		jpDealerCards.repaint();
	    		
	    		//Updates dealer card array list and score.
	    		dealerCardList.add(randomCardNumber);
	    	    updateScores(dealerCardList);
	    		}
	    	}
	    );
	    
	  //Action listener for play again button.
	    jbtPlayAgain.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		//Calls method to reinitialize the game.
	    		newGame();
	    		}
	    	}
	    );
		
	}
	
	//Action listener to allow dealer to play at a predefined time increment.
	private class DealerPlayDelayListener implements ActionListener {
		@Override 
	    public void actionPerformed(ActionEvent e) {
			//Displays time left in variable and also decrements the timing variable.
			jlTimerCountdown.setText("Dealer draws a card in :" + String.valueOf(timerCount--) + " seconds.");
			//Diagnostic output to console of the timer.
			System.out.println("\nDealer draws card in: " + timerCount + "second(s)");
			//Simulates a click of the Stay/Deal Now button when the timer reaches 0.
			if (timerCount <= 0){
				System.out.println("Dealer draws card.");
				jbtStay.doClick();
			}
	    }
	}
	
	//Method that evaluates all cards for either player or dealer.
	public void updateScores(ArrayList<Short> cardList){
		
		//Variable stores number of aces.
		int aceHighCount = 0;
			
			//Loop counts number of high-aces (11points).
			for (int i = 0; i < cardList.size(); i++){
				if (deck.getCardValue(cardList.get(i)) == 11){
					aceHighCount++;
					System.out.println("Found " + aceHighCount + " high ace(s) in hand!");
				}
			}
		
		//Conditional statement determines if parameter array list belongs to player or dealer. 
		if (cardList == playerCardList){
			//Resets player score.
			playerScore = 0;
			System.out.println("Evaluating player card nubers:");
			
			//Loop recalculates player score based on array list.
			for (int i=0; i < cardList.size(); i++){
			    System.out.print(cardList.get(i) + " ");
			    playerScore += deck.getCardValue(cardList.get(i));
			    jlPlayerScore.setText(String.valueOf(playerScore));   
			}
			//Determines whether to convert high-aces (11 points) to low-aces (1 point).
			if (playerScore > 21 && aceHighCount != 0){
				for (int i=0; i < cardList.size(); i++){
				    
					//Converts single high-ace to low-ace.
				    if(deck.getCardValue(cardList.get(i)) == 11){
				    	System.out.println("Card# " + cardList.get(i) + "- (High Ace) changed to a Low Ace");
				    	//deck.cardValue[cardList.get(i)] = 1;
				    	deck.setCardValue(cardList.get(i), (short)1);
				    	//Breaks loop after converting single high-ace to low-ace in order to reevaluate if it needs to convert anymore aces.
				    	break;
				    }   
				}
			
			
			//Reevaluates score after ace conversion.
			updateScores(playerCardList);
			jlPlayerScore.repaint();
			
			//If player score is over 21 and there are no high-aces.  Game lost.	
			}else if(playerScore > 21 & aceHighCount == 0){
				tmrDealerPlay.stop();
				
				if (autoPlay == false) {
					JOptionPane.showMessageDialog(null, "Your hand is worth " + playerScore + " points.  You have lost this round.", "Over 21 - Losing Message", 2);
				}
				
				jlTimerCountdown.setText("Click New Game button to play again.");
				//Game over.  Buttons marked as invisible.
				jbtHit.setVisible(false);
				jbtStay.setVisible(false);
			}
			//Diagnostic output to console.
			System.out.println("\nPlayer has:" + playerScore + " points.");
		}else {
			//Resets dealer score.
			dealerScore = 0;
			System.out.println("Evaluating dealer card numbers:");
			
			//Loop recalculates dealer score based on array list of cards.
			for (int i=0; i < cardList.size(); i++){
			    System.out.print(cardList.get(i) + " ");
			    dealerScore += deck.getCardValue(cardList.get(i));
			    jlDealerScore.setText(String.valueOf(dealerScore));
			}
			
			//Determines whether to convert high-aces (11 points) to low-aces (1 point).
			if (dealerScore > 21 & aceHighCount != 0){
				for (int i=0; i < cardList.size(); i++){
				    
				    //Converts single high-ace to low ace.
				    if(deck.getCardValue(cardList.get(i)) == 11){
				    	System.out.println("Card# " + cardList.get(i) + "- (High Ace) changed to a Low Ace");
				    	//deck.cardValue[cardList.get(i)] = 1;
				    	deck.setCardValue(cardList.get(i), (short)1);
				    	break;
				    }   
				}	
			
			//Reevaluates score after ace conversion.
			updateScores(dealerCardList);
			jlDealerScore.repaint();
			//If dealer score is over 21 and there are no high-aces.  Dealer loses and player wins.	
			}else if(dealerScore > 21 & aceHighCount == 0){
				//User notified of win.  Buttons mad invisible.
				tmrDealerPlay.stop();
				jbtStay.setVisible(false);
				jlTimerCountdown.setText("Click New Game button to play again.");
				
				if (autoPlay == false) {
					JOptionPane.showMessageDialog(null, "Your hand is worth " + playerScore + " points.  You have won this round.", "Dealer Over 21 - Winning Message", 1);
				}
				
				playerWinCount++;
				int numGames = playerWinCount + dealerWinCount + tieGame;
				double playerWinStat = ((int)(((double)(playerWinCount) / (double)(numGames)) * 10000.00)) / 100.00;
				double dealerWinStat = ((int)(((double)(dealerWinCount) / (double)(numGames)) * 10000.00)) / 100.00;
				double tieGameStat = ((int)(((double)(tieGame) / (double)(numGames)) * 10000.00)) / 100.00;
				System.out.println("\n#####Player wins this game.#####\nPlayer has:" + playerScore + "points - Dealer has: " + dealerScore + "points.");
				System.out.println("#####Game Statistics#####\nPlayer Wins: " + playerWinCount + "\tDealer Wins: "+ dealerWinCount + "\tTie Games: " + tieGame + "\tGame Number: " + numGames);
				System.out.println("Player wins: " + (double)playerWinStat +"%\tDealer wins: " + dealerWinStat + "%\nTie Games: " + (double)tieGameStat +"%");
				jbtPlayAgain.doClick();
				
				if (autoPlay == true) { 
					if(playerScore < 12){
						jbtHit.doClick();
					}
				}
				jbtStay.doClick();
			//If dealer score is less than or equal to 21 and greater than or equal to player; dealer wins.  Dealer wins and player loses.
			}else if(dealerScore <= 21 && dealerScore >= playerScore){
				jbtStay.setVisible(false);
				tmrDealerPlay.stop();
				jlTimerCountdown.setText("Click New Game button to play again.");
				
				if (autoPlay == false) {
					JOptionPane.showMessageDialog(null, "The dealer's hand is worth " + dealerScore + " points.  You have lost this round.", "Dealer Wins - Losing Message", 2);
				}
				
				if (playerScore < dealerScore){
					dealerWinCount++;
				}else{
					tieGame++;
				}
				
				int numGames = playerWinCount + dealerWinCount + tieGame;
				double playerWinStat = ((int)(((double)(playerWinCount) / (double)(numGames)) * 1000.00)) / 10.00;
				double dealerWinStat = ((int)(((double)(dealerWinCount) / (double)(numGames)) * 1000.00)) / 10.00;
				double tieGameStat = ((int)(((double)(tieGame) / (double)(numGames)) * 10000.00)) / 100.00;
				System.out.println("\n#####Dealer wins this game#####\nPlayer has:" + playerScore + "points - Dealer has: " + dealerScore + "points.");
				System.out.println("#####Game Statistics#####\nPlayer Wins: " + playerWinCount + "\tDealer Wins: "+ dealerWinCount + "\tTie Games: " + tieGame + "\tGame Number: " + numGames);
				System.out.println("Player wins: " + (double)playerWinStat +"%\tDealer wins: " + (double)dealerWinStat + "%\nTie Games: " + (double)tieGameStat +"%");
				jbtPlayAgain.doClick();
				
				if (autoPlay == true) {
					if(playerScore <= 11){
						jbtHit.doClick();
					}
				jbtStay.doClick();
				}
			}
		}
		//Diagnostic output to console.	
		System.out.println("\nDealer has: " + dealerScore + " points.");
	}
	
	public short randomCard(){
		short randomCard;
		short count = 0;
		
		//Do-while loop draws a random card and re-draws if needed until an available card is found.
		do { 
			randomCard = (short)(1 + Math.random() * (52 - 1 + 1));
			System.out.println("Card# " + randomCard + " generated in iteration #: " + count);
			count++;
			
		} while (deck.getCardAvailability(randomCard) == false);
		
		//Return a random card number.
		return randomCard;
	}
	
	//Method initializes all objects of the game: Cards, GUI, ArrayLists, Scoring variables...
	public void newGame(){
		
		//Ensures that the dealer play timer is stopped.
		tmrDealerPlay.stop();
		
		//Reinitializes the deck of cards.
		deck.resetCards();
		
		//Clears all dealer cards and scores
		dealerScore = 0;
		dealerCardList.clear();
		jpDealerCards.removeAll();
		
		//Generates random card for dealer and places a face-down card next to it.
		randomCardNumber = randomCard();
        jpDealerCards.add(new JLabel(deck.getCardImage(randomCardNumber)));
        jpDealerCards.add(jlDealerPlaceholder);
        
        //Records dealer card and updates dealer score.
        dealerCardList.add(randomCardNumber);
        dealerScore += deck.getCardValue(randomCardNumber);
        jlDealerScore.setText(String.valueOf(dealerScore));
    
        //Clears all player cards and scores
		playerScore = 0;
		playerCardList.clear();
		jpPlayerCards.removeAll();
		
		//Generates 2 random cards for the player, records card values and updates player score.
	    for (int i = 1; i <= 2; i++) {
	    	randomCardNumber = randomCard();
	        jpPlayerCards.add(new JLabel(deck.getCardImage(randomCardNumber)));
	        playerCardList.add(randomCardNumber);
	        playerScore += deck.getCardValue(randomCardNumber);
	        if (playerScore == 22){
	        	playerScore = 12;
	        }
	        jlPlayerScore.setText(String.valueOf(playerScore));
	      }
	    	
	    //Restores player control buttons.
		jbtHit.setVisible(true);
		jbtStay.setText("Stay");
		jbtStay.setVisible(true);
		
		//Displays game directions in label.
		jlTimerCountdown.setText("Click Hit or Stay to play current hand.");
		
		//Repaints the frame.
		repaint();
	}

}