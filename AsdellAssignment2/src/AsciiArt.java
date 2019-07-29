/*
 * File: AsciiArt.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 5
 * 
 * This program draws a simple lightning bolt pattern using a dotted line border. I chose a lightning bolt for
 * many personal reasons, from it being a common motif in my favorite pop culture media (i.e. Percy Jackson,
 * Harry Potter, Pokemon) and its representation of energy. I am an engineer, so electronics and electricity
 * are of utmost interest to me.
 */
import java.awt.Color;

import acm.program.*;

public class AsciiArt extends ConsoleProgram {
	private static final int SPACING = 25;
	private static final int SIZE = 15;
	public void run() {
		println("CS106A ASCII Art by Benjamin Asdell");
		for (int i = 0; i < SPACING; i++) { // beginning of top line
			print(" ");
		}
		for (int i = 0; i < (SIZE + 1) /2; i++) {
			print("_");
			print(" ");   
		}  //end of top line
		println();
		for (int j=1;j<10;j++) { //beginning of top third
			for (int i = 0; i < SPACING-j; i++) {
				print(" ");
			}
			print("/");
			for (int i = 0; i < SIZE; i++) {
				print(" ");
			}
			print("/");
			println();
		} //end of top third
		pause(250);
		for (int i = 0; i < SPACING - 10; i++) {
			print(" ");
		} 
		print("/");
		for (int i = 0; i < 4; i++) {  //horizontal top to middle, left side
			print("_");
			print(" ");
		}
		for (int i = 0; i < SIZE-8;i++) {
			print(" ");
		}
		print("/");
		for (int i = 0; i < 4; i++) { //horizontal top to middle, right side
			print("_");
			print(" ");
		}
		println();
		pause(250);
		for (int j=1;j<10;j++) { //beginning of middle thirrd
			for (int i = 1; i < SPACING-j; i++) {
				print(" ");
			}
			print("/");
			for (int i = 0; i < SIZE; i++) {
				print(" ");
			}
			print("/");
			println();
		} //end of middle third
		pause(250);
		for (int i = 0; i < SPACING - 11; i++) {
			print(" ");
		}
		print("/");
		for (int i = 0; i < 4; i++) { //horizontal middle to bottom, left side
			print("_");
			print(" ");
		}
		for (int i = 0; i < SIZE-8;i++) {
			print(" ");
		}
		print("/");
		for (int i = 0; i < 4; i++) { //horizontal middle to bottom, right side
			print("_");
			print(" ");
		}
		println();
		pause(250);
		for (int j=0;j<16;j++) {
			for (int i = 3; i < SPACING; i++) {
				print(" ");
			}
			print("|");
			for (int i = 0; i < SIZE-j; i++) {
				print(" ");
			}
			print("/");
			println();
		}
	}
}
