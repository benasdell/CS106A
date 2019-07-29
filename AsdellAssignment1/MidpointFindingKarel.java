/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 4
 * Sources: Karel the Robot Learns Java by Piech and Roberts
 * 
 * This Karel subclass program is designed to find the midpoint of the bottom row of any size
 * world. Karel first places beepers to mark the left and right boundaries, then repeatedly
 * picks up and places new beepers to reduce the distance between them. Once Karel has placed a 
 * beeper on the midpoint (odd length) or two beepers near the midpoint (even length), Karel
 * either stops or picks up the first encountered beeper and stops on the midpoint beeper.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
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
