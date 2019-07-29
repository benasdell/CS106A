/*
 * File: Rocket.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 4
 * 
 * This program is a drawing program that generates a simple rocket sketch using ASCII characters. The
 * exact model of the rocket is specifically designed to copy the rocket design given in the assignment
 * handout. While the program is not interactive, changing the class constant SIZE to different integer
 * values allows the user to scale the generated rocket in size. This program heavily uses nested for loops
 * to achieve its intended outcome.
 */
import acm.program.*;

public class Rocket extends ConsoleProgram {
	private static final int SIZE = 5; //scaling factor for the rocket dimensions
	
	public void run() {
		writeTitle();
		writePyramid();
		writeBody();
		writePyramid(); //utilizes symmetry of the nose cone and tail
	}
	
	/* 
	 * This method writes a simple title for the rocket. It does include the size of the specific rocket
	 * generated in the instance, which changes according to the value of the SIZE constant.
	 */
	private void writeTitle() { 
		println("CS 106A Rocket");
		println("(size "+ SIZE + ")");
	}
	
	/*
	 * This method draws the pyramid shape for both the nose cone and the tail of the rocket. The pyramid is
	 * oriented point upwards, begins 2 slashes wide, and ends 2 * SIZE slashes wide.
	 */
	private void writePyramid() {
		int i;
		int j;
		for (i = 0; i < SIZE; i++) {
			for (j = SIZE - i + 1; j > 1; j--) {
				print(" ");
			}
			for (j = 0; j <= i; j++) {
				print("/");
			}
			for (j=0; j <= i; j++) {
				print("\\");
			}
			println();
		}
	}
	
	/*
	 * This method draws the entire body tube of the rocket, consisting of a diamond pattern locked in a rectangle
	 * of periods. The diamond pattern is composed of "A-shaped" small triangles of one forward and one backslash.
	 * The diamond starts 1 triangle wide, grows to SIZE triangles wide, and ends 1 trianglew wide.
	 */
	private void writeBody() {
		writeBumper();
		for (int i = 0; i < SIZE; i++) {
			print("|");
			for (int j = SIZE - i; j > 1; j--) {
				print(".");
			}
			for (int j = 0; j <= i; j++) {
				print("/");
				print("\\");
			}
			for (int j = SIZE - i; j > 1; j--) {
				print(".");
			}
			print("|");
			println();
		}
		for (int i = 0; i < SIZE; i++) {
			print("|");
			for (int j = 0; j <= i - 1; j++) {
				print(".");
			}
			for (int j = SIZE; j > i; j--) {
				print("\\");
				print("/");
			}
			for (int j = 0; j <= i - 1 ; j++) {
				print(".");
			}
			print("|");
			println();
		}
		writeBumper();
	}
	
	/*
	 * This submethod draws the solid "bumper" lines composed of plus and equal signs on the body of the rocket.
	 * This method was separated from the rest of the body to improve decomposition and reduce the overall size
	 * of the writeBody method.
	 */
	private void writeBumper() {
		print("+");
		for (int i = 0; i < 2 * SIZE; i++) {
			print("=");
		}
		print("+");
		println();
	}
}
