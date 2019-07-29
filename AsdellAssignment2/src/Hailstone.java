/*
 * File: Hailstone.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 3
 * 
 * This program compures Hailstone sequences, a specific sequence made famous by Douglas Hofstadter,
 * which takes any number as an input. If the number is even, it is divided by 2; if odd, it is multiplied by
 * 3 and added to 1. The sequence continues, using each result of the previous calculation as the base for the
 * next until the value reaches 1. The intermediate values usually rise until returning back to 1, therefore
 * giving it the name "Hailstone". This specific program is significantly interactive, allowing the user to both 
 * input the first number in the sequence, but also run multiple sequences within the same instance. 
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		println("This program computes Hailstone sequences.");
		println();
		doHailstone(readInt("Enter a number: ")); //user input is directly passed to the method
		while (readBoolean("Run again? ", "y" , "n")) { //boolean allows for increased user interactivity
			println();
			doHailstone(readInt("Enter a number: "));
		}
		println("Thanks for using Hailstone.");
	}

	/*
	 * This method computes a single Hailstone sequence and prints the step count required to reach 1.
	 * The method passes a single integer as an input, then does the necessary math to reach each
	 * successive value in the sequence.
	 */
	private void doHailstone(int n) {
		int count = 0;
		while (n != 1) {
			int m; //variable represents new result of calculation in each pass through the loop
			if (n % 2 == 0) {
				m = n / 2;
				println(n + " is even, so I take half: " + m);
			}
			else {
				m = 3 * n + 1;
				println(n + " is odd, so I make 3n + 1: " + m);
			}
			n = m; //sets the result to the working value to be changed in the next loop
			count++;
		}
		println("It took " + count + " steps to reach 1.");
	}
}
