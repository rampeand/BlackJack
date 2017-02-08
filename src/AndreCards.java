/**
 * @author 		Andre Rampersad
 * Project: 	Black Jack Game
 * Course:		PROG24178 - Java 2
 * Instructor: 	Robert Lederer
 * File: 		AndreCards.java
 */
import javax.swing.ImageIcon;

public class AndreCards {
	//Stores card images.
	private ImageIcon[] cardImage = new ImageIcon[55];
	//Marks cards as available within the deck.
	private boolean[] cardAvailable = new boolean[53];
	//Stores point value of cards.
	private short[] cardValue = new short[53];
	//Stores card number.
	private short[] cardNumber = new short[53];
	
	//Constructor to create deck of cards.
	public AndreCards(){
		
		//Stores the file path of the card images.
		String cardImageFilePath = "image/";//Stored in a "cards" directory within my project.
		
		//Stores face-down image of card in cardImage[] element 0.
		cardImage[0] = new ImageIcon(cardImageFilePath+"b2fv.png");
		
		//Loop loads card images into array.
		for (int i = 1; i < 55; i++){
			cardImage[i] = new ImageIcon(cardImageFilePath + i + ".png");
			//Loop assigns card point values to all playing cards.  Ignores suit.
			if ( i > 0 && i < 53){
				cardNumber[i] = (short)i;
				if (i < 14){
					cardValue[i] = (short)i;
				}else if(i < 27){
					cardValue[i] = (short)(i - 13);
				}else if (i < 40){
					cardValue[i] = (short)(i - 26);
				}else{
					cardValue[i] = (short)(i - 39);
				}
				
				//Adjust the value of the face cards (Jack, Queen, King) to be 10 points.
				if (cardValue[i] > 10){
					cardValue[i] = 10;
				//All aces are reassigned their point values from 1 to 11 points.
				}else if (cardValue[i] == 1){
					cardValue[i] = 11;
				}
				
				//Marks all cards as being available in the deck.
				cardAvailable[i] = true;
			}
		}
	}//closes constructor
	
	//Accessor method return a card image and marks the card as unavailable in the deck.
	public ImageIcon getCardImage(int cardNumber){
		cardAvailable[cardNumber] = false;
		//Diagnostic output to console showing that card is unavailable once it is displayed.
		System.out.println("Card# " + cardNumber + " displayed and marked as unavailble in the deck.");
		//Returns card image.
		return cardImage[cardNumber];
	}
	
	//Accessor method checks if a card number is available within the deck.
	public boolean getCardAvailability(int cardNumberQuery){
		return cardAvailable[cardNumberQuery];
	}
	
	//Accessor method to retrieve a card point value based on a passed in card number.
	public short getCardValue(int cardNumberQuery){
		return cardValue[cardNumberQuery];
	}
	
	//Mutator method changes a card's point value when the card number and new point value are passed in.
	public void setCardValue(short cardNumber, short newCardValue){
		cardValue[cardNumber] = newCardValue;
	}
	
	//Method reinitializes card values and card availability within the deck.
	public void resetCards() {
		//Diagnostic output to console.
		System.out.println("\n**********\tNew Game\t**********");
		//Loop reinitializes the deck of cards.
		for (int i = 1; i < 53; i++){
			
			//Marks all cards in deck as available.
			cardAvailable[i] = true;
			
			//Restores all low-aces (having 1 point) to their original 11 point value.
			if (cardValue[i] == 1){
				cardValue[i] = 11;
				System.out.println("Card# " + i + " (an Ace) is restored to 11 points.");
			}
			
			//Diagnostic output to verify deck initializing process.
			//System.out.println("Card# " + cardNumber[i] + " is worth " + cardValue[i] + " points and is available to be played: " + cardAvailable[i]);
		}
	}

}
