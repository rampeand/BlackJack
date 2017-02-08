/**
 * @author 		Andre Rampersad
 * Project: 	Black Jack Game
 * Course:		PROG24178 - Java 2
 * Instructor: 	Robert Lederer
 * File: 		AndreBlackJackTest.java
 */

import java.awt.GridLayout;

import javax.swing.JFrame;

public class AndreBlackJackTest extends AndreBlackJackGame{

	public static void main(String[] args) {
		//Creates the game in a frame.
		AndreBlackJackGame frame = new AndreBlackJackGame();
		
		//Sets various properties of the game frame.
		frame.setLayout(new GridLayout(5,1,0,0));
		frame.setSize(500, 550);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	

	}

}
