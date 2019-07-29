/*
 * File: PrimeChecker.java
 * -----------------------------
 * Program written by Benjamin Asdell
 * Stanford University CS106A, Copyright 2019
 * Section: Wednesday 5pm
 * TA: Trey Connolly
 * Problem 1
 * 
 * This program utilizes the relatively finished starter code in addition to a personally designed method to test if any given 
 * integer  n >= 1 is prime. The function then checks the result of that method against a precomputed answer key. The specific 
 * algorithm for determining primality is a variant of checking for divisibility by all integers from 2 to n. However, taking into
 * account the Quora answer below, it skips unnecessary modulo operations by only testing integers of the form 6k + 1 or 6k - 1.
 * Source: https://www.quora.com/Why-do-prime-numbers-always-satisfy-the-6n+1-and-6n-1-conditions-Is-there-mathematical-logic-behind-it
 */

import acm.program.*;

public class PrimeChecker extends ConsoleProgram {
	
	int[] testCases = {2, 3, 8, 37,  42, 87, 361, 382, 729, 1019, 23081, 62053, 341, 561, 645};
	boolean[] answers = {true, true, false, true, false, false, false, false, false, true, true, true, false, false, false};
	
	/**
	 *This method tests the isPrime method for all possible primes in the array testCases. It returns the result of the 
	 *assertion for each test case, including the correct answer when isPrime returns a false positive.
	 */
	public void run() {	
		for (int i = 0; i < testCases.length; i++) {
			int testCase = testCases[i];
			boolean solution = answers[i];
			boolean returned = isPrime(testCase);
			
			if (solution == returned) { //conditional for printing assertion result to console
				println("Your solution worked for n = " + testCase + ".");
				
			} else {
				println("Your method returned " + returned + " for n = " + 
						testCase + ", but it should have returned " + solution + ".");
			}
		}
	}
	
	/**
	 * This function takes the input integer n as a parameter and outputs the boolean
	 * referring to the primality of n. The method first checks if the number is 2,3, or
	 * an integer multiple of either. If not, the method checks all numbers of the form
	 * 6k+1 or 6k-1, skipping values between them. 
	 */
	public boolean isPrime(int n) {
		int i = 5; //first prime after 3
		int j = 2; //distance between first and second primes after 3
		if (n == 2 || n == 3) {
			return true;
		}
		else if (n % 2 == 0 || n % 3 == 0) {
			return false;
		}
		while (Math.pow(i,2) <= n) {
			if (n % i == 0) {
				return false;
			}
			i += j; //these two lines allow primality test to incrementally step to the next possible prime location
			j = 6 - j; //alternates incremental step between 2  and 4
		}
		return true;
	}
}
