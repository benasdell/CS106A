/*
 * File: Quadratic Equation.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 1
 * 
 * This program computes the quadratic formula in order to find the roots of a given
 * polynomial of the form ax^2 + bx+ c. The input is three coefficients: a, b, and c.
 * The output is all the real roots (zeroes, solutions, etc.), if any. The program
 * calculates the discriminant first to determine how many roots, then directly calculates
 * the entire formula (for two real roots) or a simplified formula (for one root).
 */

import acm.program.*;

public class QuadraticEquation extends ConsoleProgram {
	public void run() {
		println("CS 106A Quadratic Equation Solver!");
		int a = readInt("Enter a: ");
		int b = readInt("Enter b: ");
		int c = readInt("Enter c: ");
		double quadDisc = b * b - 4 * a * c;	
		double sqrtDisc = Math.sqrt(quadDisc); //calculation for discrimination
		double quadDenom = 2 * a;
		if (quadDisc < 0) {
			println("No real roots");
		}
		else if (quadDisc > 0) {
			double quadNumer = -1 * b + sqrtDisc;
			double quadPos = quadNumer / quadDenom;
			quadNumer = -1 * b - sqrtDisc;
			double quadNeg = quadNumer / quadDenom;
			println("Two roots: " + quadPos + " and " + quadNeg);
		}
		else { //handles case (sqrtDisc = 0) without having to reconcile int and double data types
			double quadNumer = -1 * b;
			double quadSolve = quadNumer / quadDenom;
			println("One root: "+ quadSolve);
		}
	}
}
