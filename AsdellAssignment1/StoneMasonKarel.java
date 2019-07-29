/*
 * File: StoneMasonKarel.java
 * --------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 2
 * Sources: Karel the Robot Learns Java by Piech and Roberts
 * 
 * This Karel subclass program is designed to repair the columns of the damaged Quad
 * drawn in the various test worlds supplied for this assignment. The program works on 
 * the assumptions that each damaged column is four spaces apart, the columns are of 
 * any height, and there is a wall immediately following the last damaged column. Karel 
 * travels up each damaged column, placing beepers wherever necessary to repair the 
 * infrastructure, and returns to the ground to move to the next column. 
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			fixColumn();
			nextColumn();
		}
		fixColumn();
	}

	/*
	 * Karel moves up one column of arbitrary height, placing beepers at every open space,
	 * then moves down to the original position.
	 * Pre: Karel stands at the base of the column, facing East.
	 * Post: Karel stands at the base of the column facing East.
	 */
	private void fixColumn() {
		ascend();
		descend();
	}

	/*
	 * Orients Karel upward, moves Karel up the entire column regardless of height,
	 * placing beepers at every open space.
	 * Pre: Karel stands at base of column, facing East.
	 * Post: Karel stands at top of column, facing South.
	 */
	private void ascend() { //method for Karel to climb each column
		turnLeft();
		repair();
		while (frontIsClear()) {
			move();
			repair();
		}
		turnAround();
	}

	/*
	 * Returns Karel to base of column after finishing placing beepers.
	 * Pre: Karel stands at top of column, facing South.
	 * Post: Kareel stands at base of column, facing East.
	 */
	private void descend() { //method for returning Karel to floor
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
	/*
	 * Moves Karel four spaces ahead to the next column, as defined by the assignment handout.
	 * Pre: Karel stands at base of repaired column, facing East.
	 * Post: Karel stands at base of damaged column, facing East.
	 */
	private void nextColumn() {
		move();
		move();
		move();
		move();
	}

	/*
	 * Karel places beeper at current position only if there is not already a beeper there.
	 * Pre/Post: Karel at any height in damaged column, facing North.
	 */
	private void repair() { //Karel places beepers only if column needs one at her position
		if (noBeepersPresent()) {
			putBeeper();
		}
	}

}