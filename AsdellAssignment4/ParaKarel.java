/*
 * File: ParaKarel.java
 * ------------------
 * Program by Benjamin Asdell, Copyright 2019
 * Stanford University CS106A
 * Section: Wednesday 5pm
 * TA: Trey Connelly
 * Problem 2
 * 
 * This program is a simple "Hangman" style game that asks a player to guess a randomly chosen word
 * with a limited number of guesses. This specific instance includes a graphical display of Karel parachuting
 * with a limited number of parachute strings keeping Karel from falling to an untimely death. The game only 
 * allows input of a single character, which cannot be already revealed as a correct guess. If the player 
 * guesses incorrectly, the loss of "life" is accounted for and graphically reflected by the loss of a parachute string.
 * Repeated guesses of an incorrect guess do count as incorrect. The player can attempt to guess the entire word
 * without penalty. The words are chosen from a selected text file which can be edited to add or remove custom words.
 * The game is replayable, and each game uses a new randomly chosen word as the goal.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParaKarel extends ConsoleProgram {

	/***********************************************************
	 *              CONSTANTS                                  *
	 ***********************************************************/
	
	/* The number of guesses in one game of ParaKarel */
	private static final int N_GUESSES = 7;
	/* The width and the height to make the karel image */
	private static final int KAREL_SIZE = 150;
	/* The y-location to display karel */
	private static final int KAREL_Y = 230;
	/* The width and the height to make the parachute image */
	private static final int PARACHUTE_WIDTH = 300;
	private static final int PARACHUTE_HEIGHT = 130;
	/* The y-location to display the parachute */
	private static final int PARACHUTE_Y = 50;
	/* The y-location to display the partially guessed string */
	private static final int PARTIALLY_GUESSED_Y = 430;
	/* The y-location to display the incorrectly guessed letters */
	private static final int INCORRECT_GUESSES_Y = 460;
	/* The fonts of both labels */
	private static final String PARTIALLY_GUESSED_FONT = "Courier-36";
	private static final String INCORRECT_GUESSES_FONT = "Courier-26";
	/*The specific text file from which the program chooses its random words */
	private static final String FILENAME = "ParaKarelLexicon.txt";
	/***********************************************************
	 *              Instance Variables                         *
	 ***********************************************************/
	
	/* An object that can produce pseudo random numbers */
	private RandomGenerator rg = new RandomGenerator();
	//main canvas for graphics
	private GCanvas canvas = new GCanvas();
	//parachute string objects
	private GLine parachuteStrings[] = new GLine[N_GUESSES];
	//determines if player has won or lost the game
	private boolean winCondition = false;
	//karel instance object, used by multiple methods
	private GImage karel = new GImage("karel.png", KAREL_SIZE, KAREL_SIZE);
	//array list containing all possible words from selected file
	private ArrayList<String> wordDict;
	
	/***********************************************************
	 *                    Methods                              *
	 ***********************************************************/
	
	/**
	 * Method: Run
	 * -------------------------
	 * This method executes the ParaKarel program. The game always runs once before asking
	 * the player if they want to play again, choosing randomly a new word for the next game.
	 */
	public void run() {
		println("Welcome to ParaKarel");
		readLexicon();
		setupParaKarel();
		String word = getRandomWord();
		playParaKarel(word);
		while (readBoolean("Play again? ", "y", "n")) {
			winCondition = false;
			setupParaKarel();
			word = getRandomWord();
			playParaKarel(word);
		}
	}
	
	/**
	 * Method: Initialization
	 * -------------------------
	 * This is the initialization method that only adds the graphics display
	 * to the console program. This method executes before the above run method,
	 * allowing objects to be added to the canvas in the run execution.
	 */
	public void init() {
		add(canvas);
	}
	
	/**
	 * Method: Set Up ParaKarel
	 * -------------------------
	 * This method simply combines the four graphics methods that initialize the
	 * various canvas components and add them to the screen.
	 */
	private void setupParaKarel() {
		drawBackground();
		drawParachute();
		drawKarel();
		drawStrings();
	}
	
	/**
	 * Method: Play ParaKarel
	 * -------------------------
	 * This is the main method for the ParaKarel class. It contains all functionality
	 * of the "Hangman" aspect of the game as well as updating the canvas display
	 * accordingly. It also handles all possible win conditions.
	 */
	private void playParaKarel(String word) {
		println("Remember, you can guess the entire word.");
		int lifeCount = N_GUESSES;
		String hiddenWord = ""; //string representing overall partially guessed word
		for (int i = 0; i < word.length(); i++) { //builds string of dashes for new word
			hiddenWord += "-";
		}
		GLabel hiddenLabel = new GLabel(hiddenWord); //graphical display of partially guessed word
		hiddenLabel.setFont(PARTIALLY_GUESSED_FONT);
		hiddenLabel.setLocation(canvas.getWidth() / 2 - hiddenLabel.getWidth() / 2, PARTIALLY_GUESSED_Y);
		canvas.add(hiddenLabel);
		String incGuessWord = "";
		GLabel incGuessLabel = new GLabel(""); //graphical display of incorrectly guessed letters
		incGuessLabel.setFont(INCORRECT_GUESSES_FONT);
		incGuessLabel.setLocation(canvas.getWidth() / 2, INCORRECT_GUESSES_Y);
		canvas.add(incGuessLabel);
		int rightCounter = 1; //these two counters are used for the parachute string removal logic
		int leftCounter = 0;
		while (lifeCount > 0) {
			String revealedWord = ""; //string representing partially guessed word within each loop
			println("Your word now looks like this: " + hiddenWord);
			println("You have " + lifeCount + " guesses left.");
			String input = readLine("Your guess: ");
			if (input.toUpperCase().equals(word)) { //added extension allowing for word guesses without penalty
				winCondition = true;
				hiddenLabel.setLabel(word);
				break;
			}
			while ((input.length() != 1) || (hiddenWord.contains(input.toUpperCase()))) {
				println("That is not a valid guess."); //validates input is a single character or not already revealed
				input = readLine("Your guess: ");
			}		
			input = input.toUpperCase();
			char letterGuess = input.charAt(0); //converts input string to usable character
			int index = 0;
			for (index = 0; index < word.length(); index++) { //builds revealedWord string as dashes and possibly revealed letters
				if (word.charAt(index) == letterGuess) {
					revealedWord += letterGuess;
				}
				else if (hiddenWord.charAt(index) != '-'){
					revealedWord += word.charAt(index);
				}
				else {
					revealedWord += "-";
				}
			}
			if (hiddenWord.equals(revealedWord)) {
				println("There are no " + letterGuess + "'s in the word.");
				incGuessWord += input.toUpperCase(); //handles graphical updates of incorrrect guesses
				incGuessLabel.setLabel(incGuessWord);
				incGuessLabel.setLocation(canvas.getWidth() / 2 - incGuessLabel.getWidth() / 2, INCORRECT_GUESSES_Y);
				if (lifeCount % 2 == 1) { //logic for removing strings in specified order (right, left, right, ...)
					canvas.remove(parachuteStrings[parachuteStrings.length - rightCounter]);
					rightCounter ++;
				}
				else {
					canvas.remove(parachuteStrings[leftCounter]);
					leftCounter ++;
				}
				lifeCount --;
			}
			else {
				hiddenWord = revealedWord; //updates overall partial guess to the newly revealed word
				println("That guess is correct.");
				hiddenLabel.setLabel(hiddenWord);
			}
			if (hiddenWord.equals(word)) {
				winCondition = true;
				break;
			}	
		}
		if (winCondition == true) {
			println("You win.");
		}
		else {
			println("You're completely hung.");
			karel.setImage("karelFlipped.png");
			karel.setSize(KAREL_SIZE, KAREL_SIZE);
		}
		println("The word was: " + word);
	}
	
	/**
	 * Method: Draw Background
	 * -------------------------
	 * This method adds the main background overlaid over the canvas display.
	 * It is resized to the size of the entire canvas.
	 */
	private void drawBackground() {
		GImage bg = new GImage("background.jpg");
		bg.setSize(canvas.getWidth(), canvas.getHeight());
		canvas.add(bg,0,0);
	}
	
	/**
	 * Method: Draw Karel
	 * -------------------------
	 * This method resizes the global instance GImage karel to the constant
	 * parameter size, and moves it to a centered x-position and specified 
	 * y-position. The image is then added to the canvas display. This method also
	 * includes a reset statement needed for consecutive playthroughs.
	 */
	private void drawKarel() {
		karel.setImage("karel.png"); //reset statement
		karel.setSize(KAREL_SIZE, KAREL_SIZE);
		double karelX = canvas.getWidth() / 2 - karel.getWidth() / 2;
		canvas.add(karel, karelX, KAREL_Y);
	}

	/**
	 * Method: Draw Parachute
	 * -------------------------
	 * This method creates the parachuteBody image object, resizes it according to the
	 * specific constant parameters, and adds it to the canvas display.
	 */
	private void drawParachute() {
		double parachuteX = canvas.getWidth() / 2  - PARACHUTE_WIDTH / 2;
		GImage parachuteBody = new GImage ("parachute.png", parachuteX, PARACHUTE_Y);
		parachuteBody.setSize(PARACHUTE_WIDTH, PARACHUTE_HEIGHT);
		canvas.add(parachuteBody);
	}
	
	/**
	 * Method: Draw Strings
	 * -------------------------
	 * This method updates the instanced GLine array parachuteStrings to contain
	 * as many strings as the number of guesses allotted to the player. The strings are
	 * then updated to have the same starting point but different end points along the
	 * bottom of the parachute, and added to the canvas display.
	 */
	private void drawStrings() {
		double centerX = karel.getX() + karel.getWidth() / 2;
		double centerY = karel.getY();
		double endX = canvas.getWidth() / 2 - PARACHUTE_WIDTH / 2;
		double endY = PARACHUTE_Y + PARACHUTE_HEIGHT;
		for (int i = 0; i < N_GUESSES; i++) {
			parachuteStrings[i] = new GLine(centerX, centerY, endX + i * (PARACHUTE_WIDTH / (N_GUESSES - 1)), endY);
		}
		for (int j = 0; j < parachuteStrings.length; j++) {
			canvas.add(parachuteStrings[j]);
		}
	}

	/**
	 * Method: Get Random Word
	 * -------------------------
	 * This method returns a word to use in the ParaKarel game. It randomly 
	 * selects from among 10 choices.
	 */
	private String getRandomWord() {
		int index = rg.nextInt(wordDict.size() - 1);
		return wordDict.get(index);
	}
	
	/**
	 * Method: Read Lexicon
	 * -------------------------
	 * This method scans the selected text file line by line, and adds each entry into
	 * an array list. It also handles any input/output exceptions thrown by an empty or corrupted file.
	 */
	private void readLexicon() {
		try {
			Scanner input = new Scanner(new File(FILENAME));
			wordDict = new ArrayList<String>();
			while (input.hasNextLine()) {
				wordDict.add(input.nextLine());
			}
			input.close();
		} catch (IOException ex) {
			println("The error is: " + ex);
		}
	}
}
