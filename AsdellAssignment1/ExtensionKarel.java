/*
 * File: ExtensionKarel.java
 * --------------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 5 (optional)
 * 
 * This Karel subclass program is a simple extension of the previous midpoint finding algorithm.
 * This extension allows Karel to not just find the one dimensional midpoint of a row in a specific
 * world, but also find the two dimensional center of the world. If the dimension(s) of the world are even,
 * the final beeper will not mark the perfect center, but will be on an adjacent corner.
 */

import stanford.karel.*;

public class ExtensionKarel extends SuperKarel {
	
	public void run() {
		markMidpoint();
		verticalOrient();
		markMidpoint();
	}
	
	/*
	 * This method takes the horizontal midpoint, picks up the midpoint beeper, and
	 * turns Karel to face North regardless of what direction Karel previously faced.
	 * Pre: Karel is on the horizontal midpoint, standing on a beeper, facing East or West.
	 * Post: Karel is on the horizontal midpoint, facing North.
	 */
	private void verticalOrient() {
		pickBeeper();
		if (facingEast()) {
			turnLeft();
		}
		if (facingWest()) {
			turnRight();
		}
	}

	/*
	 * This is the entire main method from "MidpointFindingKarel.java" contained in a single submethod.
	 * Karel finds the one dimensional midpoint of the bottom row of the world, referred to above as the
	 * horizontal midpoint. 
	 * Pre: Karel is at (1,1) facing East.
	 * Post: Karel is at the horizontal midpoint, facing East or West.
	 */
	private void markMidpoint() {
		initialOrient();
		findMidpoint();
		finalOrient();
	}
	
	/*
	 * This method tells Karel to mark the leftmost boundary with a beeper
	 * and then move to the far wall, placing a beeper at the rightmost
	 * boundary.
	 * Pre: Karel is at (1,1), facing East.
	 * Post: Karel is at the farthest right position of bottom row, facing West.
	 */
	private void initialOrient() {
		putBeeper();
		while (frontIsClear()) {
			move();
		}
		if (frontIsBlocked()){
			putBeeper();
		}
		turnAround();
	}
	
	/*
	 * Karel moves to the leftmost boundary, picks up the beeper, and places it one space
	 * to the right. Karel does the same for the rightmost beeper, placing it incrementally
	 * to the left. Eventually, Karel places either both beepers next to each other or one 
	 * beeper on the center corner between the two original endpoints. Due to the method
	 * of determining the midpoint, Karel ends up at an exterior wall.
	 * Pre: Karel is at farthest right position of bottom row, facing West.
	 * Post: Karel is at left or right wall, facing the wall.
	 */
	private void findMidpoint() {
		while (frontIsClear()) {
			move();
			if (beepersPresent()) {
				pickBeeper();
				turnAround();
				move();
				putBeeper();
				move();
			}
		}
	}
	
	/*
	 * This method is designed to return Karel from the wall to the midpoint, picking 
	 * up the extraneous beeper along the way.
	 * Pre: Karel is at left or right wall, facing the wall.
	 * Post: Karel is at midpoint, standing on a beeper.
	 */
	private void finalOrient() {
		turnAround();
		while (noBeepersPresent()) {
			move();
		}
		pickBeeper();
		if (frontIsClear()) {
			move();
		}
		turnAround();
	}
}