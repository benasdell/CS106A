/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 3
 * Sources: Karel the Robot Learns Java by Piech and Roberts
 * 
 * This Karel subclass program is designed to use Karel to generate a checkerboard pattern on worlds of varying size.
 * The program should work on even and odd heights and widths, including single row or single column worlds.
 * The checkerboard pattern begins with a beeper on (1,1) and is generated differently according to the world dimensions.
 * The 
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	public void run() {
		initialOrient();
		while (frontIsClear()) {
			placeColumn();
		}
	}
	
	/*
	 * This method sets Karel up to begin checkerboarding, placing a beeper at (1,1). 
	 * It also decides the direction of Karel's initial path, especially in single row or column worlds.
	 * Pre: Karel on (1,1) facing East.
	 * Post: Karel on (1,1) facing North if room to move. Karel faces East if not.
	 */
	private void initialOrient() {
		putBeeper();
		if (leftIsClear()) {
			turnLeft();
		}
	}
	
	/*
	 * This is the main checkerboarding method, in which Karel places a beeper
	 * once every two moves until she hits a wall. 
	 * Pre: Karel is at (1,1), facing target direction.
	 * Post: Karel is at top right square, facing East.
	 */
	private void placeColumn() {
		move();
		if (frontIsClear()) {
			move();
			putBeeper();
		}
		nextColumn();
	}
	
	/*
	 * This the submethod for the checkerboard placement that moves Karel in a snaking
	 * pattern across the world.
	 * Pre: Karel is at the top or bottom of finished column, facing North or South, respectively.
	 * Post: Karel is at the top or bottom of new column, facing South or North, respectively. If Karel
	 * placed a beeper at the top of the previous column, Karel is one space ahead, standing on a beeper.
	 */
	private void nextColumn() {
		if (facingNorth()) {
			turnNorth();
		}
		else if (facingSouth()) {
			turnSouth();
		}
	}
	
	/*
	 * This method is called when Karel hits a wall while facing North, causing her to turn right and 
	 * move to the next column. It also tells Karel where to place the first beeper of the new column.
	 * Pre: Karel is at the top of finished column, facing North.
	 * Post: Karel is near the top of a new column, facing South and standing on a beeper.
	 */
	private void turnNorth() {
		if (frontIsBlocked()) {
			if (noBeepersPresent()) {
				turnRight();
				if (frontIsClear()) {
					move();
					putBeeper();
					turnRight();
				}
			}
			else {
				turnRight();
				if (frontIsClear()) {
					move();
					turnRight();
					move();
					putBeeper();
				}
			}
		}
	}
	
	/*
	 * This method is called when Karel hits a wall while facing South, causing her to turn left
	 * and move to the next column. It also tells Karel where to put the first beeper of the new
	 * column.
	 * Pre: Karel is at the bottom of finished column, facing South.
	 * Post: Karel is near the bottom of new column, facing North and standing on a beeper.
	 */
	private void turnSouth() {
		if (frontIsBlocked()) {
			if (noBeepersPresent()) {
				turnLeft();
				if (frontIsClear()) {
					move();
					putBeeper();
					turnLeft();
				}
			}
			else {
				turnLeft();
				if (frontIsClear()) {
					move();
					turnLeft();
					move();
					putBeeper();
				}
			}
		}
	}
}