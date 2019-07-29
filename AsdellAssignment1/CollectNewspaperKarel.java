/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 1
 * 
 * This Karel subclass program is written to perform the extremely specific task involving
 * the single test world given for the assignment. The task is to have Karel retrieve a "newspaper"
 * beeper from just outside the "house" drawn in the given world "CollectNewspaperKarel.w" and 
 * return to the initial position. This program is designed to only function on this specific world
 * and is not generalized for any other newspaper retrieval task.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		getNewspaper();
		getBeeper();
		goBack();
	}

	/*
	 * Karel moves to newspaper position, one column to the right of the open doorway.
	 * Pre: Karel at starting position, facing East.
	 * Post: Karel at newspaper position, facing East.
	 */
	private void getNewspaper() {
		move();
		move();
		turnRight();
		move();
		turnLeft();
		move();
	}

	/*
	 * Karel picks up newspaper and turns toward the house.
	 * Pre: Karel on top of beeper, facing East.
	 * Post: Karel at same position, holding beeper, facing West.
	 */
	private void getBeeper() {
		pickBeeper();
		turnAround();
	}

	/*
	 * Karel returns to starting position.
	 * Pre: Karel at former newspaper position, holding beeper, facing West.
	 * Post: Karel at starting position, facing East.
	 */
	private void goBack() {
		move();
		move();
		move();
		turnRight();
		move();
		turnRight();
	}
}
